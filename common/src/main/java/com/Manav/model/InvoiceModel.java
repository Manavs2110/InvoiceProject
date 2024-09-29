package com.Manav.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class InvoiceModel {
    private String invoiceNumber;
    private String description;
    private int quantity;
    private String userid;
    private double itemValue;
    private double rate;
    private double taxValue;
    private double totalValue;
}
