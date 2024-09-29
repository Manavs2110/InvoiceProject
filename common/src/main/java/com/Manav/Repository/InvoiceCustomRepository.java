package com.Manav.Repository;

import com.Manav.dto.InvoiceSummaryDto;
import com.Manav.model.InvoiceModel;

import java.util.List;

public interface InvoiceCustomRepository {
    void saveInvoices(List<InvoiceModel> invoices);

    InvoiceSummaryDto getSummary(String userId);
}
