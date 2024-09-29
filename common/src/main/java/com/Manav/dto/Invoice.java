package com.Manav.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    private String invoiceNumber;
    private String description;
    private int quantity;
    private double itemValue;
    private double rate;
    private double taxValue;
    private double totalValue;
}
