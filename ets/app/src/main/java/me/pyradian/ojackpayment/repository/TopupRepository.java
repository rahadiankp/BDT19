package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Topup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopupRepository extends JpaRepository<Topup, Long> {
    Topup findByTransactionId(String topupId);

    @Override
    List<Topup> findAll();

    List<Topup> findByWalletNumber(String walletNumber);
    List<Topup> findByStatus(String status);
}
