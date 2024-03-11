package net.ekene.hello.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponsePayload implements Serializable {
    @JsonProperty("packageName")
    private String packageName;

    @JsonProperty("paymentReference")
    private String paymentReference;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("vendStatus")
    private String vendStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private Date date;

    @JsonProperty("units")
    private String units;

//    @JsonProperty("tokenData")
//    private TokenData[] tokenData;

    @JsonProperty("customerMessage")
    private String customerMessage;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("tokenCode")
    private String tokenCode;


        public static void main(String[] args) {
            String dateString = "2024-01-04 10:02:13";


            System.out.println(formatTime(dateString));
        }

    public static String formatTime(String inputString) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM dd yyyy, hh:mm a", Locale.US);

        return outputFormatter.format(inputFormatter.parse(inputString));
    }

}
