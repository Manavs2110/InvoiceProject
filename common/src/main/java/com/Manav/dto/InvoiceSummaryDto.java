package com.Manav.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InvoiceSummaryDto {
    @JsonProperty(value = "userId")
    private String _id;
    private int count;
    private double totalTaxValue;
    private double totalValue;
}
