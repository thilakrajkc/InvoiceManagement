package com.egtest.invoice_project.service;

import com.egtest.invoice_project.exception.InvoiceNotFoundException;
import com.egtest.invoice_project.model.Invoice;
import com.egtest.invoice_project.model.InvoiceStatus;
import com.egtest.invoice_project.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    //Create new Invoice
    @Override
    public Invoice createInvoice(double amount, LocalDate dueDate) {
        Invoice invoice = new Invoice(amount, dueDate);
        return repository.save(invoice);
    }

    //Get All created invoice
    @Override
    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }


    //Logic to pay the invoice
    @Override
    public void payInvoice(Long id, double amount) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(id));

        invoice.setPaidAmount(invoice.getPaidAmount() + amount);

        if (invoice.getPaidAmount() >= invoice.getAmount()) {
            invoice.setStatus(InvoiceStatus.PAID);
        }

        repository.save(invoice);
    }

    //Handle overdue

    @Override
    public void processOverdue(double lateFee, int overdueDays) {
        LocalDate today = LocalDate.now();
        LocalDate newDueDate = today.plusDays(overdueDays);

        List<Invoice> overdueInvoices = repository.findAll().stream()
                .filter(i -> i.isOverdue(today))
                .toList();


        for (Invoice invoice : overdueInvoices) {
            double remaining = invoice.getRemainingAmount();

            if (invoice.getPaidAmount() > 0) {
                // Partially paid
                invoice.setStatus(InvoiceStatus.PAID);
                repository.save(invoice);

                Invoice newInvoice = new Invoice(remaining + lateFee, newDueDate);
                repository.save(newInvoice);
            } else {
                // Not paid at all
                invoice.setStatus(InvoiceStatus.VOID);
                repository.save(invoice);

                Invoice newInvoice = new Invoice(invoice.getAmount() + lateFee, newDueDate);
                repository.save(newInvoice);
            }
        }
    }




}
