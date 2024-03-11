package net.ekene.hello.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponse implements Serializable {
    @JsonProperty("dotPayReference")
    private String dotPayReference;

    @JsonProperty("requestStatus")
    private String requestStatus;

    @JsonProperty("externalReference")
    private String externalReference;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responsePayload")
    private String responsePayload;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("account")
    private String account;

    @JsonProperty("failed")
    private boolean failed;

    @JsonProperty("successful")
    private boolean successful;

    @JsonProperty("pending")
    private boolean pending;

    public static void main(String[] args) throws IOException {
        String jsonString = "{\"dotPayReference\":\"DOT03272846120240213074953092\",\"requestStatus\":\"SUCCESS\",\"externalReference\":\"a0232b298ee34befa9255d811d478033\",\"responseCode\":\"200\",\"responsePayload\":\"{\\\"packageName\\\":\\\"JAMB\\\",\\\"paymentReference\\\":\\\"DOT03272846120240213074953092\\\",\\\"transactionId\\\":\\\"a0232b298ee34befa9255d811d478033\\\",\\\"vendStatus\\\":\\\"CONFIRMED\\\",\\\"date\\\":\\\"2024-02-13\\\",\\\"units\\\":null,\\\"tokenData\\\":[{\\\"amount\\\":6200,\\\"tokenValue\\\":\\\"2767432403869668\\\",\\\"id\\\":null}],\\\"customerMessage\\\":\\\"Successful | JAMB pin Payment for Profile code: 8685838712 of N 6200 from your Wallet.\\\",\\\"customer\\\":\\\"8685838712\\\",\\\"tokenCode\\\":\\\"2767432403869668\\\"}\"}"
                + ",\"provider\":\"CAPIFLEX\",\"account\":\"8685838712\",\"failed\":false,\"successful\":true,\"pending\":false}";

        String jsonString1 = "{\"dotPayReference\":\"DOT03328863720240219032654257\",\"requestStatus\":\"SUCCESS\",\"externalReference\":\"1708309358266\",\"responseCode\":\"200\",\"responsePayload\":\"{\\\"id\\\":64331775,\\\"amountGenerated\\\":\\\"1813.95\\\",\\\"tariff\\\":\\\"72.67\\\",\\\"debtAmount\\\":\\\"0\\\",\\\"debtRemaining\\\":\\\"0\\\",\\\"disco\\\":\\\"PH\\\",\\\"freeUnits\\\":null,\\\"orderId\\\":\\\"019d90989906481d9572556c9365f31b\\\",\\\"receiptNo\\\":\\\"1902202430941695\\\",\\\"tax\\\":\\\"132.56\\\",\\\"vendTime\\\":\\\"2024-02-19 03:22:52\\\",\\\"token\\\":\\\"1713-0807-1659-6982-6858\\\",\\\"tokenCode\\\":\\\"1713-0807-1659-6982-6858\\\",\\\"totalAmountPaid\\\":\\\"2000\\\",\\\"units\\\":\\\"25.0\\\",\\\"vendAmount\\\":\\\"0\\\",\\\"vendRef\\\":\\\"1708309358266\\\",\\\"responseCode\\\":100,\\\"responseMessage\\\":\\\"Purchase Successful\\\"}\",\"provider\":\"BUYPOWER\",\"account\":\"0124001656832\",\"failed\":false,\"successful\":true,\"pending\":false}";

        ObjectMapper objectMapper = new ObjectMapper();
        PaymentResponse paymentResponse = objectMapper.readValue(jsonString1, PaymentResponse.class);
        ElectricPayload responsePayload1 = objectMapper.readValue(paymentResponse.getResponsePayload(), ElectricPayload.class);

        System.out.println(responsePayload1);
        System.out.println(paymentResponse);
//        System.out.println(responsePayload1.getTax());
//        System.out.println(responsePayload1.getResponseMessage());
//        System.out.println(responsePayload1.getAmountGenerated());
//        System.out.println(responsePayload1.getDisco());
//        System.out.println(responsePayload1.getTotalAmountPaid());
//        System.out.println(responsePayload1.getUnits());
//        System.out.println(responsePayload1.getTokenCode());
//
//        System.out.println(jsonString);
//        System.out.println(paymentResponse.getDotPayReference());
//        System.out.println(paymentResponse.getRequestStatus());
//        // ... Access other fields as needed
//        System.out.println(paymentResponse.getProvider());
//        System.out.println("dfghjkl");
//        System.out.println(paymentResponse.getExternalReference());
//        System.out.println(paymentResponse.getAccount());
//        System.out.println(paymentResponse.getResponsePayload());
//        System.out.println(paymentResponse.isSuccessful());
    }
}



//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//class TokenData implements Serializable {
//    @JsonProperty("amount")
//    private int amount;
//
//    @JsonProperty("tokenValue")
//    private String tokenValue;
//
//    @JsonProperty("id")
//    private String id;
//}

