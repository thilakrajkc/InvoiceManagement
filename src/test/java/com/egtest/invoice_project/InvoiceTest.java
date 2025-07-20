package com.egtest.invoice_project;

import com.egtest.invoice_project.model.Invoice;
import com.egtest.invoice_project.model.InvoiceStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    @Test
    void testGetRemainingAmount() {
        Invoice invoice = new Invoice(100.0, LocalDate.now());
        invoice.setPaidAmount(30.0);

        double remaining = invoice.getRemainingAmount();
        assertEquals(70.0, remaining, 0.001);
    }

    @Test
    void testIsOverdueTrue() {
        Invoice invoice = new Invoice(100.0, LocalDate.now().minusDays(5));
        invoice.setStatus(InvoiceStatus.PENDING);

        assertTrue(invoice.isOverdue(LocalDate.now()));
    }

    @Test
    void testIsOverdueFalseWhenFutureDate() {
        Invoice invoice = new Invoice(100.0, LocalDate.now().plusDays(2));
        invoice.setStatus(InvoiceStatus.PENDING);

        assertFalse(invoice.isOverdue(LocalDate.now()));
    }


    @Test
    void testIsOverdueFalseWhenNotPending() {
        Invoice invoice = new Invoice(100.0, LocalDate.now().minusDays(5));
        invoice.setStatus(InvoiceStatus.PAID);

        assertFalse(invoice.isOverdue(LocalDate.now()));
    }
}
