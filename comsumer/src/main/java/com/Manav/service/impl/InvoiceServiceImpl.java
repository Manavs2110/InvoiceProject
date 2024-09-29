package com.Manav.service.impl;

import com.Manav.Repository.InvoiceRepository;
import com.Manav.dto.Invoice;
import com.Manav.dto.InvoiceSummaryDto;
import com.Manav.helper.CSVHelper;
import com.Manav.model.InvoiceModel;
import com.Manav.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final CSVHelper csvHelper;

    private final InvoiceRepository invoiceRepository;

    private final Map<String, List<Invoice>> data = new HashMap<>();

    @Override
    @Async
    public List<Invoice> upload(MultipartFile file, String userId) {
        var invoices = csvHelper.readAndPrintInvoice(file);
        List<InvoiceModel> invoiceModels = new ArrayList<>();
        for (Invoice invoice: invoices){
            InvoiceModel invoiceModel = InvoiceModel.builder().invoiceNumber(invoice.getInvoiceNumber()).
                    description(invoice.getDescription()).
                    quantity(invoice.getQuantity()).
                    itemValue(invoice.getItemValue()).
                    rate(invoice.getRate()).
                    taxValue(invoice.getTaxValue()).
                    totalValue(invoice.getTotalValue()).
                    userid(userId).
                    build();

            invoiceModels.add(invoiceModel);
            invoiceRepository.save(invoiceModel);

        }

//        data.put(userId, invoices);
//        invoiceRepository.saveInvoices(invoiceModels);

        return invoices;
    }

    @Override
    public Invoice getInvoice(String invoiceNumber, String userId) {
//        var invoices = data.get(userId);
//        for (var invoice : invoices) {
//            if (invoice.getInvoiceNumber().equals(invoiceNumber)) {
//                return invoice;
//            }
//        }
        InvoiceModel invoiceModel = invoiceRepository.findByInvoiceNumberAndUserid(invoiceNumber, userId);
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceModel.getInvoiceNumber());
        invoice.setDescription(invoiceModel.getDescription());
        invoice.setQuantity(invoiceModel.getQuantity());
        invoice.setItemValue(invoiceModel.getItemValue());
        invoice.setRate(invoiceModel.getRate());
        invoice.setTotalValue(invoiceModel.getTotalValue());
        invoice.setTaxValue(invoiceModel.getTaxValue());
        return invoice;
    }

    @Override
    public InvoiceSummaryDto getSummary(String userId) {
        return invoiceRepository.getSummary(userId);
    }

}
