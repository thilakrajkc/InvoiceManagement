package com.egtest.invoice_project.dto;

import lombok.Data;

@Data
public class OverdueRequestDTO {
    private double late_fee;
    private int overdue_days;
}

