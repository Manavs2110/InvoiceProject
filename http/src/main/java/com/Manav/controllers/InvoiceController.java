package com.Manav.controllers;

import com.Manav.dto.Invoice;
import com.Manav.dto.InvoiceSummaryDto;
import com.Manav.dto.SqsInvoiceData;
import com.Manav.service.InvoiceService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final SqsClient sqsClient;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @PutMapping(path = "/upload")
    public List<Invoice> uploadFile(
            @RequestBody MultipartFile file,
            @RequestHeader("user-id") String userId) {
        return invoiceService.upload(file, userId);
    }
    @PutMapping(path = "/upload-filepath")
    public String uploadFileFromPath(
            @RequestParam("filePath") String filePath,
            @RequestHeader("user-id") String userId) {
        SqsInvoiceData sqsInvoiceData = SqsInvoiceData.builder().userId(userId).filePath(filePath).build();
        try{
            String message = objectMapper.writeValueAsString(sqsInvoiceData);
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder().queueUrl("https://sqs.ap-south-1.amazonaws.com/612304127751/2024-onboarding-2").messageBody(message).build();

            SendMessageResponse sendMessageResponse = sqsClient.sendMessage(sendMessageRequest);
            return "ok";
        }
        catch(JacksonException e){
            throw new RuntimeException("Cannot be converted to a String");
        }


    }

    @GetMapping(path = "/invoice/{invoiceNumber}")
    public Invoice getInvoice(
            @PathVariable String invoiceNumber,
            @RequestHeader("user-id") String userId) {
        log.info("path variable : {};", invoiceNumber);
        return invoiceService.getInvoice(invoiceNumber, userId);
    }

    @GetMapping(path = "/invoice")
    public Invoice getInvoiceQueryParam(
            @RequestParam String invoiceNumber,
            @RequestHeader("user-id") String userId) {
        log.info("query parameter : {};", invoiceNumber);
        return invoiceService.getInvoice(invoiceNumber, userId);
    }

    @GetMapping(path = "/summary")
    public InvoiceSummaryDto getSummary(@RequestHeader("user-id") String userId) {
        return invoiceService.getSummary(userId);
    }



    @PostMapping(path="/UpdateInvoice")
    public Invoice updateInvoice(@RequestBody Invoice invoice){
        invoice.setDescription("added Custom");
        return invoice;
    }
}
