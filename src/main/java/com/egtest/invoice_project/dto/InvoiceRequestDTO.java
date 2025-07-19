package com.egtest.invoice_project.dto;

import lombok.Data;

@Data
public class InvoiceRequestDTO {
    private double amount;
    private String due_date;
}
