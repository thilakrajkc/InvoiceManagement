package com.egtest.invoice_project.service;

import com.egtest.invoice_project.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(double amount, LocalDate dueDate);
    List<Invoice> getAllInvoices();
    void payInvoice(Long id, double amount);
    void processOverdue(double lateFee, int overdueDays);
}

