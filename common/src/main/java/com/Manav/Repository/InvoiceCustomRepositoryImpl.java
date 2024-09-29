package com.Manav.Repository;

import com.Manav.dto.InvoiceSummaryDto;
import com.Manav.model.InvoiceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class InvoiceCustomRepositoryImpl implements  InvoiceCustomRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void saveInvoices(List<InvoiceModel> invoices){
        mongoTemplate.insertAll(invoices);
    }

    @Override
    public InvoiceSummaryDto getSummary(String userId) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("userid").is(userId));
        GroupOperation groupOperation = Aggregation.group("userid").count().as("count")
                .sum("taxValue").as("totalTaxValue")
                .sum("totalValue").as("totalValue");

        Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation);

        return mongoTemplate.aggregate(aggregation, InvoiceModel.class, InvoiceSummaryDto.class)
                .getUniqueMappedResult();
    }
}
