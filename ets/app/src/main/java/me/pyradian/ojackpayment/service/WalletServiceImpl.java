package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private WalletRepository walletRepository;

    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public int isValidWallet(Wallet w) {

        // incomplete field
        if (w.getWalletNumber() == null || w.getType() == null || !validType(w.getType()))
            return -1;

        // wallet number already exists
        if (this.walletRepository.findByWalletNumber(w.getWalletNumber()) != null)
            return -2;

        // invalid type
        if (!validType(w.getType()))
            return -3;

        return 0;
    }

    private boolean validType(String type) {
        return type.equals("driver") || type.equals("customer") || type.equals("restaurant");
    }
}
