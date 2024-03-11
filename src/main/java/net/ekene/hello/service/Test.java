package net.ekene.hello.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/gen")
@RestController
public class Test {

    private final CReeceipt cReceipt;

    @GetMapping( value = "/receipt/{transactionRef}")
    public ResponseEntity<byte[]> cReceipt(@PathVariable("transactionRef") String transactionRef)
            throws IOException {
        log.info("Transaction ref {}", transactionRef);
        TransactReceipt.ReturnReceipt result = cReceipt.receipt(transactionRef);
        return ResponseEntity.ok()
                .headers(result.getHeaders()).body(result.getOutputStream());
    }



//    public void schedule(String fileName, long delay, TimeUnit timeUnit) {
//        log.info("Scheduling file for deletion [{}] -> Time -> [{}] -> Unit [{}]", fileName, delay, timeUnit);
//        getDelayedQueue().offer(fileName, delay, timeUnit);
//    }
}

