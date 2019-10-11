package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.FoodOrder;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderServiceImpl implements FoodOrderService {

    private WalletRepository walletRepository;

    public FoodOrderServiceImpl(WalletRepository walletRepository) {
        this. walletRepository = walletRepository;
    }

    public int isValid(FoodOrder fo) {
        // incomplete fields
        if (fo.getFoodOrderBill() == 0)
            return -1;
        // invalid bill
        else if (fo.getFoodOrderBill() <= 0)
            return -2;
        // missing wallet number or share amount
        else if(!checkWallet(fo))
            return -3;
        // customer balance is insufficient
        else if(!checkCustomerBalance(fo))
            return -4;

        return 0;
    }

    public boolean isPending(FoodOrder fo) {
        return fo.getStatus().equals("pending");
    }

    private boolean checkWallet(FoodOrder fo) {
        if(fo.getCustomerWalletNumber() == null && fo.getCustomerAmount() == 0 &&
                fo.getDriverWalletNumber() == null && fo.getDriverAmount() == 0 &&
                fo.getRestaurantWalletNumber() == null && fo.getRestaurantAmount() == 0)
            return false;

        // check wallet existence per user
        Wallet customerWallet = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());
        Wallet driverWallet = this.walletRepository.findByWalletNumber(fo.getDriverWalletNumber());
        Wallet restaurantWallet = this.walletRepository.findByWalletNumber(fo.getRestaurantWalletNumber());

        return customerWallet != null && driverWallet != null && restaurantWallet != null;
    }

    private boolean checkCustomerBalance(FoodOrder fo) {
        Wallet w = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());

        return (w.getBalance() - fo.getFoodOrderBill()) >= 0;
    }

}
