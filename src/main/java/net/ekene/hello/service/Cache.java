package net.ekene.hello.service;

import java.util.Locale;
import java.util.Objects;

public class Cache {
//    else if (TransactionType.valueOf(transaction.getTransactionType()) == TransactionType.CABLETV) {
//        if (!transaction.isPending() || !transaction.isSuccessful()){
//            BillsMeta billsMeta = JsonUtil.fromJson(transaction.getTransferRequest(), BillsMeta.class);
//            if (Objects.isNull(billsMeta)){
//                throw new TransferResponseNotFoundException("Utility Bill transaction not found");
//            }
//            htmlContent = readHtmlFileIntoString("failed-cable-receipt.html");
//            searchedVariables = new String[]{"__BG__", "__COLOR__",
//                    "__AMOUNT__", "__STATUS__", "__SERVICEID__", "__SERVICEPKG__", "__DECODER__", "__TRANSACTIONTYPE__",
//                    "__TRANSACTIONDATE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//            };
//            DateAndTime dateAndTime = formatTime(transaction.getCreatedAt());
//            transactionVariables = new String[]{bg, color,
//                    String.format(Locale.US, "%,.2f", transaction.getAmount()), transaction.getStatusMessage(),
//                    billsMeta.getServiceId(), billsMeta.getServicePackageId(), billsMeta.getAccountNumber(),
//                    extractTransactionType(transaction.getTransactionType()), dateAndTime.getDate(), dateAndTime.getTime(),
//                    transaction.getTransactionReference()
//            };
//        } else {
//            BillsTransactionResponse billsTransaction = utilityServiceCalls.getTransaction(transaction.getTransactionReference());
//            if (billsTransaction.getProvider().equalsIgnoreCase("BAXI")){
//                htmlContent = readHtmlFileIntoString("blank-cable-receipt.html");
//                searchedVariables = new String[]{"__BG__", "__COLOR__",
//                        "__AMOUNT__", "__STATUS__", "__SERVICEID__", "__SERVICEPKG__", "__DECODER__", "__TRANSACTIONTYPE__",
//                        "__TRANSACTIONDATE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//                };
//                DateAndTime dateAndTime = formatTime(transaction.getCreatedAt());
//                transactionVariables = new String[]{bg, color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), transaction.getStatusMessage(),
//                        billsTransaction.getServiceType(), billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                        extractTransactionType(billsTransaction.getServiceCategory()), dateAndTime.getDate(),
//                        dateAndTime.getTime(), billsTransaction.getDotPayReference()
//                };
//            }else {
//                CustomerDetails customerDetails = utilityServiceCalls.getCustomerDetails(transaction.getTransactionReference());
//                htmlContent = readHtmlFileIntoString("cable-receipt.html");
//                searchedVariables = new String[]{"__BG__", "__COLOR__",
//                        "__AMOUNT__", "__STATUS__", "__SERVICEID__", "__SERVICEPKG__", "__DECODER__", "__CUSTOMERNAME__",
//                        "__TRANSACTIONTYPE__", "__TRANSACTIONDATE__", "__TRANSACTIONTIME__", "__TRANSACTIONREFERENCE__"
//                };
//                DateAndTime dateAndTime = formatTime(transaction.getCreatedAt());
//                transactionVariables = new String[]{bg, color,
//                        String.format(Locale.US, "%,.2f", billsTransaction.getAmount()), transaction.getStatusMessage(),
//                        billsTransaction.getServiceType(), billsTransaction.getServicePackage(), billsTransaction.getAccount(),
//                        customerDetails.getCustomerName(), extractTransactionType(billsTransaction.getServiceCategory()),
//                        dateAndTime.getDate(), dateAndTime.getTime(), billsTransaction.getDotPayReference()
//                };
//            }
//        }
//    }
}
