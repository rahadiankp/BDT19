package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    Withdrawal findByTransactionId(String withdrawalId);

    @Override
    List<Withdrawal> findAll();
}
