package net.ekene.hello.service;

import jakarta.annotation.PostConstruct;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@RestController
@RequestMapping("/transact")
public class TransactReceipt {
    @Autowired
    private TransactionRepo transactionService; // Assuming you have a TransactionService to retrieve transactions

    @PostConstruct
    public void addUser(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy, HH:mm");

        Transaction transaction = new Transaction(UUID.randomUUID(), "120000", "10101010", "Opay", "ben", "00202020", "joy", "failed", "Valentine gift", "Inter Bank", "456789", "456****789", "126789", LocalDateTime.now().format(formatter), RandomStringUtils.randomAlphanumeric(12));
        Transaction transaction1 = new Transaction(UUID.randomUUID(), "120000", "10101010", "Moniepoint", "Loy", "00202020", "joy", "successful", "Valentine gift", "Inter Bank", "456789", "456****789", "126789", Date.from(Instant.now()).toString(), RandomStringUtils.randomAlphanumeric(12));
        Transaction transaction2 = new Transaction(UUID.randomUUID(), "12000", "10101010", "Access", "Jane", "00202020", "joy", "pending", "Valentine gift", "Inter Bank", "456789", "456****789", "126789", LocalDate.now().toString(), RandomStringUtils.randomAlphanumeric(12));
        transactionService.saveAll(List.of(transaction1,transaction,transaction2));
        System.out.println(transaction2.getRef());
        System.out.println(transaction1.getRef());
        System.out.println(transaction.getRef());
        System.out.println("Data persisted");
        System.out.println(transactionService.findAll());

    }


    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping(value = "/generate-pdf/{transactionRef}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String transactionRef) {
//        Transaction transaction = transactionService.findById(id).get();
//        System.out.println(transaction);
//
//        Context context = new Context();
//        context.setVariable("transaction", transaction);
//
//        String htmlContent = templateEngine.process("receipt", context);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(htmlContent);
//        renderer.layout();
//        renderer.createPDF(outputStream, true);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
////        headers.setContentType(MediaType.IMAGE_JPEG);
//        System.out.println("transaction-" + transaction.getRef() + ".pdf");
//
//        headers.setContentDispositionFormData("attachment", "transaction-" + transaction.getRef() + ".pdf");
        ReturnReceipt returnReceipt = generateReceipt(transactionRef);

        return ResponseEntity
                .ok()
                .headers(returnReceipt.getHeaders())
                .body(returnReceipt.getOutputStream());
    }

    public ReturnReceipt generateReceipt ( String transactionRef) {

        ReturnReceipt returnReceipt = new ReturnReceipt();
        try {
            Transaction transaction = transactionService.findTransactionByRefIgnoreCase(transactionRef).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

            Context context = new Context();
            context.setVariable("transaction", transaction);

            String htmlContent = templateEngine.process("receipt", context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream, true);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "transaction-" + transaction.getRef() + ".pdf");

            returnReceipt.setHeaders(headers);
            returnReceipt.setOutputStream(outputStream.toByteArray());

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return returnReceipt;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ReturnReceipt{
        private HttpHeaders headers;
        private byte[] outputStream;
    }

    public static void main(String[] args) throws ParseException {
        String format = String.format(Locale.US, "%,.2f", 1222323.00);
        System.out.println(format);

        ZoneId zoneId = ZoneId.of("Africa/Lagos");
        System.out.println(LocalDateTime.now());
        System.out.println(Instant.now() + "dfghj");

        System.out.println(maskAccountNumber(Instant.parse("2024-02-13T07:34:44.560434Z")));

    }

    public static String maskAccountNumber(Instant time) {
//        Instant instantTime = (time != null) ? time : Instant.parse(time.toString());
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("Africa/Lagos"));
        int monthValue = zonedDateTime.getMonthValue();
        String monthName = Instant.now().atZone(ZoneId.of("UTC"))
                .withMonth(monthValue)
                .getMonth()
                .getDisplayName(TextStyle.FULL, Locale.US);

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-yyyy, hh:mm a", Locale.US);
        return monthName + "-" + outputFormat.format(Date.from(zonedDateTime.toInstant()));
    }
}
