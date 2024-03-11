package net.ekene.hello.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricPayload {
    @JsonProperty("id")
    private int id;

    @JsonProperty("amountGenerated")
    private BigDecimal amountGenerated;

    @JsonProperty("tariff")
    private BigDecimal tariff;

    @JsonProperty("debtAmount")
    private BigDecimal debtAmount;

    @JsonProperty("debtRemaining")
    private BigDecimal debtRemaining;

    @JsonProperty("disco")
    private String disco;

    @JsonProperty("freeUnits")
    private String freeUnits;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("receiptNo")
    private String receiptNo;

    @JsonProperty("tax")
    private BigDecimal tax;

    @JsonProperty("vendTime")
    private String vendTime;

    @JsonProperty("token")
    private String token;

    @JsonProperty("tokenCode")
    private String tokenCode;

    @JsonProperty("totalAmountPaid")
    private BigDecimal totalAmountPaid;

    @JsonProperty("units")
    private BigDecimal units;

    @JsonProperty("vendAmount")
    private BigDecimal vendAmount;

    @JsonProperty("vendRef")
    private String vendRef;

    @JsonProperty("responseCode")
    private int responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;
}
