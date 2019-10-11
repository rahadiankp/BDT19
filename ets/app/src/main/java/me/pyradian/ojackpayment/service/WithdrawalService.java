package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.Withdrawal;

public interface WithdrawalService {
    int isValid(Withdrawal wd);
    boolean isPending(Withdrawal wd);
}
