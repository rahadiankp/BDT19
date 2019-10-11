package me.pyradian.ojackpayment.service;

import me.pyradian.ojackpayment.model.FoodOrder;

public interface FoodOrderService {
    int isValid(FoodOrder fo);
    boolean isPending(FoodOrder fo);
}
