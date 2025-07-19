package com.egtest.invoice_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private double paidAmount;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;


    public Invoice(double amount, LocalDate dueDate) {
        this.amount = amount;
        this.paidAmount = 0;
        this.dueDate = dueDate;
        this.status = InvoiceStatus.PENDING;
    }

    public boolean isOverdue(LocalDate today) {
        return dueDate.isBefore(today) && status == InvoiceStatus.PENDING;
    }

    public double getRemainingAmount() {
        return amount - paidAmount;
    }
}
