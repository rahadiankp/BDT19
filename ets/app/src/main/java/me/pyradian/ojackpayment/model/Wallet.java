package me.pyradian.ojackpayment.model;

import javax.persistence.*;

@Entity
@Table(name = "wallets")
public class Wallet extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "wallet_number")
    private String walletNumber;
    @Column(name = "balance")
    private int balance;
    @Column(name = "type")
    private String type; // DRIVER, CUSTOMER, RESTAURANT

    public Wallet() {
    }

    public Wallet(String walletNumber, String type) {
        this.walletNumber = walletNumber;
        this.type = type;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

}
