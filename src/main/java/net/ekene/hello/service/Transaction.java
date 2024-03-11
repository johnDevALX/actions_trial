package net.ekene.hello.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {
    @Id
    private UUID id;
    private String amount;
    private String bAcctNo;
    private String bAcctType;
    private String sender;
    private String sAcctNo;
    private String beneficiary;
    private String status;
    private String narration;
    private String type;
    private  String rrn;
    private  String maskedPan;
    private  String stan;
    private String date;
    private String ref;


}
