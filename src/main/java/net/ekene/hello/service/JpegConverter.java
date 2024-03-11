package net.ekene.hello.service;

import com.lowagie.text.DocumentException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.swing.Java2DRenderer;
import org.xhtmlrenderer.util.FSImageWriter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/")
@Slf4j
public class JpegConverter {
    private final TransactReceipt transactReceipt;






    @GetMapping("/convert/pdf-to-image/{transactionRef}")
    public ResponseEntity<byte[]> convertPdfToImage(@PathVariable("transactionRef") String transactionRef) {
           TransactReceipt.ReturnReceipt returnReceipt1 = convertPDFToImage(transactionRef);
            return ResponseEntity.ok()
                    .headers(returnReceipt1.getHeaders())
                    .body(returnReceipt1.getOutputStream());
    }

    public TransactReceipt.ReturnReceipt convertPDFToImage(String transactionRef) {
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(transactReceipt.generateReceipt(transactionRef).getOutputStream()))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "JPEG", outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDispositionFormData("attachment", "transaction-" + transactionRef + ".jpeg");

            return new TransactReceipt.ReturnReceipt(headers, outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


//    private final TemplateEngine templateEngine;
//
//    @GetMapping("image-gen/{transactionRef}")
//    public ResponseEntity<byte[]> convertHtmlToJpeg(@PathVariable("transactionRef") String transactionRef) {
//        try {
//            // Load HTML template from the "templates" folder
////            Resource htmlResource = new ClassPathResource("templates/" + readHtmlFileIntoString("receipt.html"));
////            String htmlFilePath = htmlResource.getURL().toString();
//
//            Transaction transaction = transactionService.findTransactionByRefIgnoreCase(transactionRef).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
//
//            Context context = new Context();
//            context.setVariable("transaction", transaction);
//
//            String htmlContent = templateEngine.process("receipt", context);
//
//
//            byte[] image = convertionHtmlToJpeg("<span>hello</span>");
//            log.info("Buffered image {}", image);
//
////            byte[] jpegBytes = saveImageAsJpeg(image);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            headers.setContentDispositionFormData("attachment", "output.jpg");
//
//            return new ResponseEntity<>(image, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    public byte[] convertHtmlToJpeg(String htmlContent) {
//        try {
//            BufferedImage image = renderToImage(htmlContent);
//
//            return saveImageAsJpeg(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//    private static BufferedImage renderToImage(String htmlContent) {
//        HtmlRendererBuilder rendererBuilder = new HtmlRendererBuilder();
//        rendererBuilder.withHtmlContent(htmlContent, "");
//
//        return rendererBuilder.toImage();
//    }
//
//    private static byte[] saveImageAsJpeg(BufferedImage image) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", outputStream);
//
//        return outputStream.toByteArray();
//    }
//
//    public static void main(String[] args) {
//        String htmlContent = "<html><body><h1>Hello, World!</h1></body></html>";
//
//        byte[] jpegBytes = convertHtmlToJpeg(htmlContent);
//
//        // Now you can use jpegBytes as needed (e.g., save to a file or return in a Spring ResponseEntity)
//    }
//
////    private byte[] convertionHtmlToJpeg(String htmlFilePath) throws IOException, DocumentException {
////        ITextRenderer renderer = new ITextRenderer();
////
////        // Load HTML content from the file or URL
////        try (InputStream inputStream = new FileInputStream(htmlFilePath)) {
////            renderer.setDocument((Document) inputStream, htmlFilePath);
////        } catch (IOException e) {
////            e.printStackTrace();
////            throw e;
////        }
////
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        renderer.createPDF(outputStream);
////
////        // Convert the PDF bytes to JPEG (you may need to adjust this based on your requirements)
////        // For simplicity, we are using the PDF bytes directly in this example
////        return outputStream.toByteArray();
////    }
////    private BufferedImage renderToImage(String htmlFilePath) {
////        Java2DRenderer renderer = new Java2DRenderer(htmlFilePath, 1024, 768);
////        log.info("Render  {}", renderer.getImage());
////
////        return renderer.getImage();
////    }
////
////    private byte[] saveImageAsJpeg(BufferedImage image) throws IOException {
////        log.info("Buffered image {}", image);
////
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        FSImageWriter imageWriter = new FSImageWriter();
////        imageWriter.write(image, outputStream);
////        return outputStream.toByteArray();
////    }
//
////    private String readHtmlFileIntoString(String filename){
////        InputStream inputStream;
////        try {
////            inputStream = resourceLoader.getResource("classpath:/templates/" + filename).getInputStream();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
////    }
}
