package com.egtest.invoice_project.controller;

import com.egtest.invoice_project.dto.InvoiceRequestDTO;
import com.egtest.invoice_project.dto.OverdueRequestDTO;
import com.egtest.invoice_project.dto.PaymentRequestDTO;
import com.egtest.invoice_project.model.Invoice;
import com.egtest.invoice_project.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@Slf4j
public class InvoiceController {
    private final InvoiceService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> createInvoice(@RequestBody InvoiceRequestDTO dto) {
        double amount = dto.getAmount();
        LocalDate dueDate = LocalDate.parse(dto.getDue_date());
        Invoice invoice = service.createInvoice(amount, dueDate);
        log.info("Created invoice with ID {}", invoice.getId());
        return ResponseEntity.status(201).body(Map.of("id", String.valueOf(invoice.getId())));
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {
        log.info("Fetching all invoices");
        return service.getAllInvoices();
    }

    @PostMapping("/payments/{id}")
    public ResponseEntity<Map<String, String>> payInvoice(@PathVariable Long id, @RequestBody PaymentRequestDTO dto) {
        double amount = dto.getAmount();
        log.info("Paying invoice ID {} with amount {}", id, amount);
        service.payInvoice(id, amount);
        return ResponseEntity.ok(Map.of("message", "Invoice paid successfully"));
    }

    @PostMapping("/process-overdue")
    public ResponseEntity<Map<String, String>> processOverdue(@RequestBody OverdueRequestDTO dto) {
        double lateFee = dto.getLate_fee();
        int overdueDays = dto.getOverdue_days();
        log.info("Processing overdue invoices with late fee {} and overdue days {}", lateFee, overdueDays);
        service.processOverdue(lateFee, overdueDays);
        return ResponseEntity.ok(Map.of("message", "Overdue invoices processed successfully"));
    }
}


