package com.egtest.invoice_project.exception;

public class InvoiceNotFoundException extends RuntimeException {
    public InvoiceNotFoundException(Long id) {
        super("Invoice with ID " + id + " not found.");
    }
}

