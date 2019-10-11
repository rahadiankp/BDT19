package me.pyradian.ojackpayment.model;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "topups")
public class Topup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "topup_wallet_number")
    private String walletNumber;
    @Column(name = "topup_balance")
    private int topupBalance;
    @Column(name = "transaction_id")
    protected String transactionId;
    @Column(name = "transaction_type")
    protected String transactionType;
    @Column(name = "transaction_cashflow")
    protected String cashflow; // CREDIT, DEBIT
    @Column(name = "transaction_status")
    protected String status; // confirmed, canceled, pending

    public Topup() {
    }

    public Topup(String walletNumber, int topupBalance) {
        // generate topup transaction id
        String hash = DigestUtils.sha1Hex(new Date() + walletNumber + topupBalance);

        this.transactionId = "OJAP-TOP-" + hash;
        this.transactionType = "TOPUP";
        this.cashflow = "credit";
        this.walletNumber = walletNumber;
        this.topupBalance = topupBalance;
        this.status = "pending";
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public int getTopupBalance() {
        return topupBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getCashflow() {
        return cashflow;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setCashflow(String cashflow) {
        this.cashflow = cashflow;
    }
}
