//package org.dotpay.transaction.service;
//
//import com.microsoft.playwright.Browser;
//import com.microsoft.playwright.Page;
//import com.microsoft.playwright.Playwright;
//import com.microsoft.playwright.options.ScreenshotType;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;
//import org.dotpay.transaction.exception.BadGatewayException;
//import org.dotpay.transaction.exception.TransactionDoesNotExistException;
//import org.dotpay.transaction.exception.TransferResponseNotFoundException;
//import org.dotpay.transaction.interaction.impl.UtilityServiceCalls;
//import org.dotpay.transaction.model.entity.Transaction;
//import org.dotpay.transaction.model.enums.TransactionType;
//import org.dotpay.transaction.payload.TransactionReceiptResponse;
//import org.dotpay.transaction.payload.receipt.*;
//import org.dotpay.transaction.payload.receipt.BillsTransactionResponse.SummaryResponsePayload;
//import org.dotpay.transaction.payload.receipt.BillsTransactionResponse.EpinResponsePayload;
//import org.dotpay.transaction.payload.receipt.CustomerResponse.Customer;
//import org.dotpay.transaction.payload.utility.UtilityPaymentResponse;
//import org.dotpay.transaction.repository.TransactionRepository;
//import org.dotpay.transaction.util.JsonUtil;
//import org.dotpay.transaction.validation.model.BillsMeta;
//import org.dotpay.transaction.validation.model.EpinMeta;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.http.ContentDisposition;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.xhtmlrenderer.pdf.ITextRenderer;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.TextStyle;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//@Slf4j
//public class ReceiptService {
//
//    private final TransactionRepository transactionRepository;
//    private final ResourceLoader resourceLoader;
//    private final UtilityServiceCalls utilityServiceCalls;
//    private final Playwright playwright = Playwright.create();
//    private final Browser browser = playwright.chromium().launch();
//
//
//
//    public TransactionReceiptResponse generateTransactionPDFReceipt (String transactionReference) {
//        ITextRenderer renderer = new ITextRenderer();
//        try {
//            List<Transaction> transactions = transactionRepository.searchForTransactionsByReference(transactionReference);
//            if (transactions.isEmpty()) {
//                throw new TransactionDoesNotExistException("Transaction not found");
//            }
//            Transaction transaction = transactions.get(0);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            renderer.setDocumentFromString(fillTransactionDetails(transaction));
//            renderer.layout();
//            renderer.createPDF(outputStream, true);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "transaction-" + transaction.getTransactionReference() + ".pdf");
//            return new TransactionReceiptResponse(headers, outputStream.toByteArray());
//        } catch (Exception e){
//            throw new BadGatewayException(e.getMessage());
//        }
//
//    }
//
//    public TransactionReceiptResponse convertPDFToJpeg(String transactionRef) {
//        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(generateTransactionPDFReceipt(transactionRef).getOutputStream()))) {
//            PDFRenderer pdfRenderer = new PDFRenderer(document);
//            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ImageIO.write(image, "JPEG", outputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            headers.setContentDispositionFormData("attachment", "transaction-" + transactionRef + ".jpeg");
//
//            return new TransactionReceiptResponse(headers, outputStream.toByteArray());
//        } catch (IOException e) {
//            throw new BadGatewayException(e.getMessage());
//        }
//    }
//
//    public TransactionReceiptResponse pngTransactionReceipt(String transactionRef) {
//
//        List<Transaction> transactions = transactionRepository.searchForTransactionsByReference(transactionRef);
//        if (transactions.isEmpty()) {
//            throw new TransactionDoesNotExistException("Transaction not found");
//        }
//        Transaction transaction = transactions.get(0);
//        log.info("Transaction ref {}", transactionRef);
//        byte[] optional = getBrowserPageScreenShot(transaction);
////        optional.ifPresent(fileName -> schedule(fileName, 3, TimeUnit.MINUTES));
//
//        if (Objects.isNull(optional)) {
//            throw new RuntimeException("Not found");
//        }
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("transaction-" + transactionRef + ".png").build());
//
////        String base64Image = Base64.getEncoder().encodeToString(optional);
//
////        log.info("Image string  {}", base64Image);
//        return new TransactionReceiptResponse(headers, optional);
//    }
//
//    //TODO, add customer name in utility, card type in withdrawal.
//    //TODO: get transaction details from bills for -> data, education, cabletv, utility, epin
//
//    private String readHtmlFileIntoString(String filename){
//        InputStream inputStream;
//        try {
//            inputStream = resourceLoader.getResource("classpath:/templates/" + filename).getInputStream();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
//    }
//
//    private String fillTransactionDetails(Transaction transaction){
//        UtilityPaymentResponse summary = utilityServiceCalls.getTransactionSummary("FMY03249910920240109153108");
//        BillsTransactionResponse billsTransaction = utilityServiceCalls.getTransaction("FMY03249910920240109153108");
//
////        List<EpinResponsePayload> epinResponsePayloads = JsonUtil.fromJson(billsTransaction.getResponsePayload(), List.class);
////        EpinResponsePayload epinResponsePayload = JsonUtil.fromJson(billsTransaction.getResponsePayload(), EpinResponsePayload.class);
//
//        log.info("UtilityPaymentResponse obj {}", summary);
////        log.info("BillsTransactionResponse epinResponsePayloads obj {}", epinResponsePayloads);
////        log.info("BillsTransactionResponse epinResponsePayloads size {}", epinResponsePayloads.size());
//        log.info("BillsTransactionResponse epinResponsePayload obj {}", billsTransaction.getResponsePayload());
//
//        log.info("BillsTransactionResponse obj {}", billsTransaction);
//
//
//
//
//        String[] searchedVariables = null;
//        String[] transactionVariables = null;
//        String htmlContent = null;
//
//        String color = "#008000";
//        if (transaction.isPending()){
//            color = "#c34804";
//        } else if (!transaction.isPending() && !transaction.isSuccessful()) {
//            color = "#f50303";
//        }
//
//        //Date for airtime and data
//        if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.AIRTIME || TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.DATA){
////            SummaryResponsePayload responsePayload = JsonUtil.fromJson(billsTransaction.getResponsePayload(), SummaryResponsePayload.class);
////            log.info("BillsTransactionResponse ResponsePayload obj {}", responsePayload);
////            if (Objects.isNull(responsePayload)){
////                throw new TransferResponseNotFoundException(transaction.getTransactionType() + " Bill transaction not found");
////            }
//            htmlContent = readHtmlFileIntoString("airtime-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__", "__AMOUNT__", "__STATUS__", "__NUMBER__",
//                    "__NUMBERTYPE__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                    billsTransaction.getPhone(), billsTransaction.getServiceType(), billsTransaction.getServiceCategory(),
//                    formatTime((transaction.getCreatedAt())), billsTransaction.getDotPayReference()
//            };
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.EPIN){
//            //Fix failed epin bc the List will be null
//            List<EpinResponsePayload> epinResponsePayloads = JsonUtil.fromJson(billsTransaction.getResponsePayload(), List.class);
//            if (Objects.isNull(epinResponsePayloads)){
//                EpinMeta epinMeta = JsonUtil.fromJson(transaction.getTransferRequest(), EpinMeta.class);
//                htmlContent = readHtmlFileIntoString("epin-receipt.html");
//                searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__", "__BILLEDAMOUNT__", "__STATUS__", "__EPIN__",
//                        "__BILLEDAMOUNT__", "__QTY__", "__SERVICE__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__",
//                        "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//                };
//                transactionVariables = new String[]{ "₦", color,
//                        String.format(Locale.US, "%,.2f", transaction.getBilledAmount()), billsTransaction.getRequestStatus(),
//                        String.valueOf(billsTransaction.getAmount()), String.valueOf(transaction.getBilledAmount()),
//                        String.valueOf(transaction.getBilledAmount().divide(transaction.getAmount())), billsTransaction.getServicePackage(), billsTransaction.getServiceCategory(),
//                        formatTime(transaction.getCreatedAt()), billsTransaction.getServiceCategory(), billsTransaction.getDotPayReference()
//                };
//            } else {
//                htmlContent = readHtmlFileIntoString("epin-receipt.html");
//                searchedVariables = new String[]{"__SYMBOL__", "__COLOR__", "__BILLEDAMOUNT__", "__STATUS__", "__EPIN__",
//                        "__BILLEDAMOUNT__", "__QTY__", "__SERVICE__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__",
//                        "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//                };
//
//                int epinQty = epinResponsePayloads.size();
//                transactionVariables = new String[]{"₦", color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount().multiply(BigDecimal.valueOf(epinQty))), billsTransaction.getRequestStatus(),
//                        String.valueOf(billsTransaction.getAmount()), String.valueOf(billsTransaction.getAmount().multiply(BigDecimal.valueOf(epinQty))),
//                        String.valueOf(epinQty), billsTransaction.getServicePackage(), billsTransaction.getServiceCategory(),
//                        formatTime(transaction.getCreatedAt()), billsTransaction.getServiceCategory(), billsTransaction.getDotPayReference()
//                };
//            }
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.BETTING) {
//            BillsMeta billsMeta = JsonUtil.fromJson(transaction.getTransferRequest(), BillsMeta.class);
//            if (Objects.isNull(billsMeta)){
//                throw new TransferResponseNotFoundException("Betting Bill transaction not found");
//            }
//            htmlContent = readHtmlFileIntoString("betting-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__ACCOUNTID__", "__SERVICEPACKAGE__", "__TRANSACTIONTYPE__",
//                    "__TRANSACTIONTIME__", "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    billsMeta.getAccountNumber(), billsMeta.getServicePackageId(), transaction.getTransactionType(),
//                    formatTime(transaction.getCreatedAt()), transaction.getDescription(), transaction.getTransactionReference()
//            };
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.CABLETV) {
//            //some cabletv returns failed and the customer details are available. failed cable transaction, returns a date but successful one doesn't
//            if (billsTransaction.getRequestStatus().equalsIgnoreCase("FAILED")){
//                htmlContent = readHtmlFileIntoString("failed-cable-receipt.html");
//                searchedVariables = new String[]{"__SYMBOL__", "__COLOR__",
//                        "__AMOUNT__", "__STATUS__", "__SERVICEID__", "__SERVICEPKG__", "__DECODER__", "__TRANSACTIONTYPE__",
//                        "__TRANSACTIONTIME__", "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//                };
//
//                transactionVariables = new String[]{"₦", color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                        billsTransaction.getServiceType(), billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                        billsTransaction.getServiceCategory(), formatTime(transaction.getCreatedAt()),
//                        billsTransaction.getServiceCategory(), billsTransaction.getDotPayReference()
//                };
//            } else {
//                CustomerDetails customerDetails = utilityServiceCalls.getCustomerDetails("DOT03247842020230807171204209");
//                htmlContent = readHtmlFileIntoString("cable-receipt.html");
//                searchedVariables = new String[]{"__SYMBOL__", "__COLOR__",
//                        "__AMOUNT__", "__STATUS__", "__SERVICEID__", "__SERVICEPKG__", "__DECODER__", "__CUSTOMERNAME__", "__TRANSACTIONTYPE__",
//                        "__TRANSACTIONTIME__", "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//                };
//
//                transactionVariables = new String[]{"₦", color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                        billsTransaction.getServiceType(), billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                        customerDetails.getCustomerName(), billsTransaction.getServiceCategory(), formatTime(transaction.getCreatedAt()),
//                        billsTransaction.getServiceCategory(), billsTransaction.getDotPayReference()
//                };
//            }
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.EDUCATION) {
//            //some successful transaction under education, their response payload differ.
//
//            log.info("Returned Transaction object {}", transaction);
//
//            if (billsTransaction.getRequestStatus().equalsIgnoreCase("SUCCESSFUL") || billsTransaction.getRequestStatus().equalsIgnoreCase("SUCCESS")){
//                EducationResponsePayload educationResponsePayload = JsonUtil.fromJson(billsTransaction.getResponsePayload(), EducationResponsePayload.class);
//                log.info("BillsTransactionResponse educationResponsePayload obj {}", educationResponsePayload);
//                if (!Objects.isNull(educationResponsePayload)) {
//                    htmlContent = readHtmlFileIntoString("education-receipt.html");
//                    searchedVariables = new String[]{"__SYMBOL__", "__COLOR__",
//                            "__AMOUNT__", "__STATUS__", "__SERVICEPACKAGE__", "__ACCOUNTID__", "__TOKENCODE__", "__TRANSACTIONTYPE__",
//                            "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"};
//
//                    transactionVariables = new String[]{"₦", color,
//                            String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                            billsTransaction.getServicePackage(), billsTransaction.getAccount(), educationResponsePayload.getTokenCode(),
//                            billsTransaction.getServiceCategory(), formatTime(transaction.getCreatedAt()),
//                            billsTransaction.getDotPayReference()};
//                }
//
//            } else {
//                //work on the status
//                htmlContent = readHtmlFileIntoString("failed-education-receipt.html");
//                searchedVariables = new String[]{"__SYMBOL__", "__COLOR__", "__AMOUNT__", "__STATUS__",
//                        "__SERVICEPACKAGE__", "__ACCOUNTID__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__",
//                        "__TRANSACTIONREFERENCE__"};
//
//                transactionVariables = new String[]{"₦", color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), "FAILED",
//                        billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                        billsTransaction.getServiceCategory(), formatTime(transaction.getCreatedAt()),
//                        billsTransaction.getDotPayReference()};
//            }
//
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.UTILITY) {
//            //some cabletv returns failed and the customer details are available.
//            CustomerDetails customerDetails = utilityServiceCalls.getCustomerDetails("FMY03249910920240109153108");
//            String customerDataJson = JsonUtil.toJson(customerDetails.getCustomerData());
//            CustomerResponse customerResponse = JsonUtil.fromJson(customerDataJson, CustomerResponse.class);
//            log.info("Customer Response Obj {}", customerResponse );
//            if (billsTransaction.getRequestStatus().equalsIgnoreCase("SUCCESSFUL") || billsTransaction.getRequestStatus().equalsIgnoreCase("SUCCESS")){
//                UtilityResponsePayload utilityResponsePayload = JsonUtil.fromJson(billsTransaction.getResponsePayload(), UtilityResponsePayload.class);
//                if (!Objects.isNull(utilityResponsePayload) && !Objects.isNull(customerResponse)){
//                    htmlContent = readHtmlFileIntoString("electricity-receipt.html");
//                    searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                            "__AMOUNT__", "__STATUS__", "__METERNO__", "__DISCO__", "__CUSTOMERNAME__", "__TOKENCODE__",
//                            "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//                    };
//
//                    transactionVariables = new String[]{ "₦", color,
//                            String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                            customerResponse.getCustomer().getMeterNo(), billsTransaction.getServiceType(),
//                            customerResponse.getCustomer().getName(), utilityResponsePayload.getTokenCode(),
//                            transaction.getTransactionType(), formatTime(utilityResponsePayload.getVendTime()),
//                            billsTransaction.getDotPayReference()
//                    };
//                }
//            } else {
//                if (Objects.isNull(customerResponse)){
//                    throw new TransferResponseNotFoundException("Utility Bill transaction not found");
//                }
//                htmlContent = readHtmlFileIntoString("failed-electricity-receipt.html");
//                searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                        "__AMOUNT__", "__STATUS__", "__METERNO__", "__DISCO__", "__CUSTOMERNAME__",
//                        "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//                };
//
//                transactionVariables = new String[]{ "₦", color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), billsTransaction.getRequestStatus(),
//                        customerResponse.getCustomer().getMeterNo(), billsTransaction.getServiceType(),
//                        customerResponse.getCustomer().getName(), transaction.getTransactionType(),
//                        formatTime(transaction.getCreatedAt()), billsTransaction.getDotPayReference()
//                };
//            }
//
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.POS_TRANSFER) {
//            htmlContent = readHtmlFileIntoString("pos-transfer-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__SENDERACCOUNTNAME__", "__SENDERACCOUNTNUMBER__",
//                    "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    transaction.getSenderAccountName(), maskAccountNumber(transaction.getSenderAccountNumber()),
//                    transaction.getTransactionType(), formatTime(transaction.getCreatedAt()),
//                    transaction.getDescription(), transaction.getTransactionReference()
//            };
//        }else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.WITHDRAWAL || TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.BANK_WITHDRAWAL) {
//            htmlContent = readHtmlFileIntoString("withdrawal-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__DESCRIPTION__",
//                    "__TRANSACTIONREFERENCE__", "__MASKEDPAN__", "__RRN__", "__STAN__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    transaction.getTransactionType(), formatTime(transaction.getCreatedAt()),
//                    transaction.getDescription(), transaction.getTransactionReference(), transaction.getMaskedPan(),
//                    transaction.getRrn(), transaction.getStan()
//            };
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.LOANS) {
//            htmlContent = readHtmlFileIntoString("loan-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__DESCRIPTION__",
//                    "__TRANSACTIONREFERENCE__", "__AID__", "__MODE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    transaction.getTransactionType(), formatTime(transaction.getCreatedAt()),
//                    transaction.getDescription(), transaction.getTransactionReference(), transaction.getFromAccountId(),
//                    transaction.getTransactionMode()
//            };
//        } else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.LOTTERY) {
//            htmlContent = readHtmlFileIntoString("lottery-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    transaction.getTransactionType(), formatTime(transaction.getCreatedAt()), transaction.getTransactionReference()
//            };
//        } else {
//            htmlContent = readHtmlFileIntoString("transfer-receipt.html");
//            searchedVariables = new String[]{ "__SYMBOL__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__TOACCOUNTNAME__", "__TOBANKNAME__", "__TOACCOUNTNUMBER__",
//                    "__TRANSACTIONTYPE__", "__TRANSACTIONTIME__",
//                    "__DESCRIPTION__", "__TRANSACTIONREFERENCE__"
//            };
//
//            transactionVariables = new String[]{ "₦", color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    transaction.getToBankAccountName(), transaction.getToBankName(), transaction.getToBankAccountNumber(),
//                    transaction.getTransactionType(), formatTime(transaction.getCreatedAt()),
//                    transaction.getDescription(), transaction.getTransactionReference()
//            };
//        }
//
//        return StringUtils.replaceEach(htmlContent, searchedVariables, transactionVariables);
//    }
//
//    private String maskAccountNumber(String accountNumber) {
//        return accountNumber.substring(0, 3) + "*".repeat(accountNumber.length() - 6) + accountNumber.substring(accountNumber.length() - 3);
//
//    }
//    private String formatTime(Instant time) {
//        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("Africa/Lagos"));
//        int monthValue = zonedDateTime.getMonthValue();
//        String monthName = Instant.now().atZone(ZoneId.of("UTC"))
//                .withMonth(monthValue)
//                .getMonth()
//                .getDisplayName(TextStyle.FULL, Locale.US);
//
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-yyyy, hh:mm a", Locale.US);
//        return monthName + "-" + outputFormat.format(Date.from(zonedDateTime.toInstant()));
//    }
//
//    private byte[] getBrowserPageScreenShot(Transaction transaction) {
//        log.info("Transaction object  {}", transaction);
//
//        String fileName = String.format("%s.png", transaction.getTransactionReference());
////
////        log.info("FIle obj {}", FilesUtils.load("templates/receipt.html"));
////        String transactionDate = transaction.getDate();
////        String html = StringUtils.replaceEach(
////                FilesUtils.load("templates/receipt.html"),
////                new String[]{"__AMOUNT__", "__TRANSACTION_ID__", "__RRN__", "__PAN__",
////                        "__STAN__", "__TRANSACTION_DATE__", "__TRANSACTION_STATUS__"},
////                new String[]{transaction.getAmount(), transaction.getRef(),
////                        transaction.getRrn(), transaction.getMaskedPan(), transaction.getStan(),
////                        transactionDate, "APPROVED"}
////        );
//
////        log.info("Html file ---  obj {}", htmlContent);
//
//        Page page = browser.newPage();
//
//        log.info("Page object  {}", page);
//
//
//        page.setContent(fillTransactionDetails(transaction));
//        page.setViewportSize(650, 770);
//
//        byte[] imageBytes = page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));
//
//        log.info("Page object  {}", page);
//        log.info("filename object  {}", fileName);
//        return imageBytes;
//
//    }
//
//    private String formatTime(String inputString) {
//        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM dd yyyy, hh:mm a", Locale.US);
//
//        return outputFormatter.format(inputFormatter.parse(inputString));
//    }
//
//
//}


//public class TokenData {
//    @JsonProperty("stdToken")
//    private StdToken stdToken;
//
//    public static class StdToken {
//        @JsonProperty("value")
//        private String value;
//    }
//}