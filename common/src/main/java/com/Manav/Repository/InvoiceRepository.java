package com.Manav.Repository;

import com.Manav.model.InvoiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvoiceRepository extends MongoRepository<InvoiceModel, String>, InvoiceCustomRepository  {
    InvoiceModel findByInvoiceNumberAndUserid(String invoiceNumber, String userid);
}
