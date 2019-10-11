package me.pyradian.ojackpayment.model;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "withdrawals")
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "withdrawal_wallet_number")
    private String walletNumber;
    @Column(name = "withdrawal_amount")
    private int amount;
    @Column(name = "withdrawal_bank_name")
    private String bankName;
    @Column(name = "withdrawal_bank_account")
    private String bankAccount;
    @Column(name = "transaction_id")
    protected String transactionId;
    @Column(name = "transaction_type")
    protected String transactionType;
    @Column(name = "transaction_cashflow")
    protected String cashflow; // CREDIT, DEBIT
    @Column(name = "transaction_status")
    protected String status; // confirmed, canceled, pending

    public Withdrawal(String walletNumber, int amount, String bankName, String bankAccount) {
        // generate withdrawal transaction id
        String hash = DigestUtils.sha1Hex(new Date() + walletNumber + amount + bankAccount + bankName);

        this.transactionId = "OJAP-WDR-" + hash;
        this.walletNumber = walletNumber;
        this.amount = amount;
        this.status = "pending";
        this.bankName = bankName;
        this.bankAccount = bankAccount;

        this.cashflow = "debit";
        this.transactionType = "WITHDRAWAL";
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public int getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getTransactionId() {
        return transactionId;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
