package me.pyradian.ojackpayment.repository;

import me.pyradian.ojackpayment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByWalletNumber(String walletNumber);
}
