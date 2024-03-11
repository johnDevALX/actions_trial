package net.ekene.hello.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.util.Locale;
import java.util.Objects;

@Data
public class TokenData {
    @JsonProperty("stdToken")
    private StdToken stdToken;




//    try{
//
//    }catch (RuntimeException e){
//        log.info("exception caught {}", e);
//
//        htmlContent = readHtmlFileIntoString("failed-education-receipt.html");
//        searchedVariables = new String[]{"__BG__", "__COLOR__", "__AMOUNT__", "__STATUS__",
//                "__SERVICEPACKAGE__", "__ACCOUNTID__", "__TRANSACTIONTYPE__", "__TRANSACTIONDATE__",
//                "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"};
//
//
//        transactionVariables = new String[]{bg, color,
//                String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), transaction.getStatusMessage(),
//                billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                billsTransaction.getServiceCategory(), dateAndTime.getDate(), dateAndTime.getTime(),
//                billsTransaction.getDotPayReference()};
//    }

    public static void main(String[] args) {
        String input = "CABLE_TV   ";
        String result = input.replaceAll("[^a-zA-Z0-9]", " ");
        System.out.println(result.trim());
    }
}

// cable tv and education failed transactions that are fully empty.
// ASVbuBttXUuKMjXaPtjDwK8uGbDpwzsxmrWPeF8HDTmxCEb9U8MHmm4mmVwb2fLc



