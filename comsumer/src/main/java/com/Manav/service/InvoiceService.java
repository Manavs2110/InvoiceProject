package com.Manav.service;

import com.Manav.dto.Invoice;
import com.Manav.dto.InvoiceSummaryDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InvoiceService {
    List<Invoice> upload(MultipartFile file, String userId);

    Invoice getInvoice(String invoiceNumber, String userId);

    InvoiceSummaryDto getSummary(String userId);
}
