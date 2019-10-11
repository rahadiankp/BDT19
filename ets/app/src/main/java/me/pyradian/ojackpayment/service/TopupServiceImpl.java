package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.Topup;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class TopupServiceImpl implements TopupService{

    private WalletRepository walletRepository;

    public TopupServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public int isValid(Topup t) {
        if ( t.getTopupBalance() <= 0 || t.getWalletNumber() == null)
            return -1;
        else if (this.walletRepository.findByWalletNumber(t.getWalletNumber()) == null)
            return -2;
        else if (t.getTopupBalance() <= 25000)
            return -3;
        // only valid for customer
        else if (!checkWalletType(t))
            return -4;

        return 0;
    }

    public boolean isPending(Topup t) {
        return t.getStatus().equals("pending");
    }

    private boolean checkWalletType(Topup t) {
        Wallet w = this.walletRepository.findByWalletNumber(t.getWalletNumber());

        return w.getType().equals("customer");
    }
}
