package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.Topup;

public interface TopupService {
     int isValid(Topup t);
     boolean isPending(Topup t);
}
