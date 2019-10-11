package me.pyradian.ojackpayment.model;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "food_orders")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "food_order_bill")
    private int foodOrderBill;
    @Column(name = "transaction_id")
    protected String transactionId;

    @Column(name = "customer_amount")
    private int customerAmount;
    @Column(name = "customer_wallet_number")
    private String customerWalletNumber;

    @Column(name = "driver_amount")
    private int driverAmount;
    @Column(name = "driver_wallet_number")
    private String driverWalletNumber;

    @Column(name = "restaurant_amount")
    private int restaurantAmount;
    @Column(name = "restaurant_wallet_number")
    private String restaurantWalletNumber;

    @Column(name = "transaction_type")
    protected String transactionType;
    @Column(name = "transaction_cashflow")
    protected String cashflow; // CREDIT, DEBIT
    @Column(name = "transaction_status")
    protected String status; // confirmed, canceled, pending

    public FoodOrder() {
    }

    public FoodOrder(int foodOrderBill) {
        // generate food order transaction id
        String hash = DigestUtils.sha1Hex(new Date() + "secretkey");

        this.transactionId = "OJAP-FOD-" + hash;
        this.transactionType = "FOODORDER";
        this.foodOrderBill = foodOrderBill;
        this.status = "pending";
        this.cashflow = "debit";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFoodOrderBill() {
        return foodOrderBill;
    }

    public void setFoodOrderBill(int foodOrderBill) {
        this.foodOrderBill = foodOrderBill;
    }

    public int getCustomerAmount() {
        return customerAmount;
    }

    public void setCustomerAmount(int customerAmount) {
        this.customerAmount = customerAmount;
    }

    public String getCustomerWalletNumber() {
        return customerWalletNumber;
    }

    public void setCustomerWalletNumber(String customerWalletNumber) {
        this.customerWalletNumber = customerWalletNumber;
    }

    public int getDriverAmount() {
        return driverAmount;
    }

    public void setDriverAmount(int driverAmount) {
        this.driverAmount = driverAmount;
    }

    public String getDriverWalletNumber() {
        return driverWalletNumber;
    }

    public void setDriverWalletNumber(String driverWalletNumber) {
        this.driverWalletNumber = driverWalletNumber;
    }

    public int getRestaurantAmount() {
        return restaurantAmount;
    }

    public void setRestaurantAmount(int restaurantAmount) {
        this.restaurantAmount = restaurantAmount;
    }

    public String getRestaurantWalletNumber() {
        return restaurantWalletNumber;
    }

    public void setRestaurantWalletNumber(String restaurantWalletNumber) {
        this.restaurantWalletNumber = restaurantWalletNumber;
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

    public String getStatus() {
        return status;
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