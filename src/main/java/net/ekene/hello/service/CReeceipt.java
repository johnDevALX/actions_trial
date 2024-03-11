package net.ekene.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.ScreenshotType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class CReeceipt {

    private final Playwright playwright = Playwright.create();
    private final Browser browser = playwright.chromium().launch();
    private final TransactionRepo transactionRepo;
    private final TemplateEngine templateEngine;

    public TransactReceipt.ReturnReceipt receipt(String transactionRef) {
        TransactReceipt.ReturnReceipt returnReceipt = new TransactReceipt.ReturnReceipt();

        log.info("Transaction ref {}", transactionRef);
        byte[] optional = createReceipt(transactionRef);
//        optional.ifPresent(fileName -> schedule(fileName, 3, TimeUnit.MINUTES));

        if (Objects.isNull(optional)) {
            throw new RuntimeException("Not found");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("transaction-" + transactionRef + ".png").build());

//        String base64Image = Base64.getEncoder().encodeToString(optional);

        returnReceipt.setHeaders(headers);
        returnReceipt.setOutputStream(optional);

//        log.info("Image string  {}", base64Image);

        log.info("dfghjk, dfghj {}", TimeUnit.DAYS);
        return returnReceipt;
    }

    public byte[] createReceipt(String reference) {

        Transaction transaction = transactionRepo.findTransactionByRefIgnoreCase(reference).get();
        log.info("Transaction object  {}", transaction);

        Context context = new Context();
        context.setVariable("transaction", transaction);

        String htmlContent = templateEngine.process("receipt", context);

        String fileName = String.format("%s.png", transaction.getRef());
//
//        log.info("FIle obj {}", FilesUtils.load("templates/receipt.html"));
//        String transactionDate = transaction.getDate();
//        String html = StringUtils.replaceEach(
//                FilesUtils.load("templates/receipt.html"),
//                new String[]{"__AMOUNT__", "__TRANSACTION_ID__", "__RRN__", "__PAN__",
//                        "__STAN__", "__TRANSACTION_DATE__", "__TRANSACTION_STATUS__"},
//                new String[]{transaction.getAmount(), transaction.getRef(),
//                        transaction.getRrn(), transaction.getMaskedPan(), transaction.getStan(),
//                        transactionDate, "APPROVED"}
//        );

//        log.info("Html file ---  obj {}", htmlContent);

        Page page = browser.newPage();

        log.info("Page object  {}", page);


        page.setContent(htmlContent);
        page.setViewportSize(650, 770);

        byte[] imageBytes = page.screenshot(new Page.ScreenshotOptions().setType(ScreenshotType.PNG));

        log.info("Page object  {}", page);
        log.info("filename object  {}", fileName);
        return imageBytes;

    }



    public static void main(String[] args) throws Exception {
        String payload = "{\"tokenData\":{\"stdToken\":{\"amount\":\"5700.00\",\"value\":\"375441827668421\"}}}";
        System.out.println(payload);

        ObjectMapper objectMapper = new ObjectMapper();
        CoralTokenDataWrapper tokenData = objectMapper.readValue(payload, CoralTokenDataWrapper.class);

        // Accessing the values
        TokenData tokenData1 = tokenData.getTokenData();
        System.out.println("Amount: " + tokenData1.getStdToken().getValue());
//        System.out.println("Value: " + stdToken.getValue());
    }
}
