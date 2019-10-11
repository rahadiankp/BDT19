package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.model.Withdrawal;
import me.pyradian.ojackpayment.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{
    private WalletRepository walletRepository;

    public WithdrawalServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public int isValid(Withdrawal wd) {
        // incomplete fields
        if (wd.getAmount() <= 0 || wd.getWalletNumber() == null ||
                wd.getBankName() == null || wd.getBankAccount() == null)
            return -1;

        Wallet w = this.walletRepository.findByWalletNumber(wd.getWalletNumber());
        // wallet not found
        if (w == null)
            return -2;
        // wallet insufficient funds
        else if (w.getBalance() - wd.getAmount() < 0)
            return -3;
        // withdrawal only for driver and restaurant
        else if (w.getType().equals("customer"))
            return -4;

        return 0;
    }

    public boolean isPending(Withdrawal wd) {
        return wd.getStatus().equals("pending");
    }
}
