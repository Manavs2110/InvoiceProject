package com.Manav;

import com.Manav.dto.SqsInvoiceData;
import com.Manav.service.InvoiceService;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class SqsConsumerService {
    private final InvoiceService invoiceService;
    private static final ObjectMapper objectMapper=new ObjectMapper();
    @SqsListener("https://sqs.ap-south-1.amazonaws.com/612304127751/2024-onboarding-2")
    public void receiveMessage(String message){
        log.info("get Message : "+message);
        processMessage(message);
    }
    public void processMessage(String message){
        SqsInvoiceData sqsTicketData;
        try {
            sqsTicketData = objectMapper.readValue(message, new TypeReference<>() {});
        } catch(JacksonException ex) {
            log.error("Failed to convert sqs message to SqsTicketData", message);
            throw new RuntimeException("Failed to cast message from queue to SqsTicketData DTO. Error: " +  ex.getMessage());
        }
        File file1 = new File(sqsTicketData.getFilePath());
        try {
            FileInputStream input = new FileInputStream(file1);
            MultipartFile multipartFile = new MockMultipartFile("file",
                    "", "text/plain", input);
            invoiceService.upload(multipartFile, sqsTicketData.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while uploading file");
        }
    }
}
