package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.FoodOrder;
import me.pyradian.ojackpayment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    FoodOrder findByTransactionId(String foodOrderId);

    @Override
    List<FoodOrder> findAll();
}
