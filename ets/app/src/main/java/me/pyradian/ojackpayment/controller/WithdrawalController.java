package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.exception.BadRequestException;
import me.pyradian.ojackpayment.exception.NotFoundException;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.model.Withdrawal;
import me.pyradian.ojackpayment.repository.WalletRepository;
import me.pyradian.ojackpayment.repository.WithdrawalRepository;
import me.pyradian.ojackpayment.service.WithdrawalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WithdrawalController.BASE_URL)
public class WithdrawalController {
    public static final String BASE_URL = "/api/v1/transaction/withdrawal";

    private WalletRepository walletRepository;
    private WithdrawalRepository withdrawalRepository;
    private WithdrawalService withdrawalService;

    public WithdrawalController(WalletRepository walletRepository,WithdrawalRepository withdrawalRepository,
                                WithdrawalService withdrawalService) {
        this.walletRepository = walletRepository;
        this.withdrawalRepository = withdrawalRepository;
        this.withdrawalService = withdrawalService;
    }

    @GetMapping
    public List<Withdrawal> getAllWithdrawal() {
        return this.withdrawalRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Withdrawal> requestWithdrawal(@RequestBody Withdrawal wd) {
        int res = this.withdrawalService.isValid(wd);

        if (res == -1)
            throw new BadRequestException("Some or all fields are empty");
        else if (res == -2)
            throw new NotFoundException("Wallet Number for this withdrawal is non-existent");
        else if (res == -3)
            throw new BadRequestException("Wallet Balance is insufficient to complete withdrawal");
        else if (res == -4)
            throw new BadRequestException("Balance withdrawal is exclusive for driver and restaurant wallet only");

        Wallet w = this.walletRepository.findByWalletNumber(wd.getWalletNumber());
        w.setBalance(w.getBalance()-wd.getAmount());

        this.withdrawalRepository.save(wd);
        this.walletRepository.save(w);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", WithdrawalController.BASE_URL + "/" + wd.getTransactionId());
        return new ResponseEntity<>(wd, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{withdrawalId}")
    public Withdrawal getWithdrawal(@PathVariable("withdrawalId") String withdrawalId) {
        Withdrawal wd = this.withdrawalRepository.findByTransactionId(withdrawalId);

        if (wd == null)
            throw new NotFoundException("Withdrawal with ID " + withdrawalId + " is non-existent");

        return wd;
    }

    @PatchMapping("/confirm/{withdrawalId}")
    public Withdrawal confirmWithdrawal(@PathVariable("withdrawalId") String withdrawalId) {
        Withdrawal wd = this.withdrawalRepository.findByTransactionId(withdrawalId);

        if (wd == null)
            throw new NotFoundException("Withdrawal with ID " + withdrawalId + " is non-existent");

        if (!this.withdrawalService.isPending(wd))
            throw new BadRequestException("Withdrawal with ID " + withdrawalId + " is already confirmed/canceled");

        wd.setStatus("confirmed");
        this.withdrawalRepository.save(wd);

        return wd;
    }

    @PatchMapping("/cancel/{withdrawalId}")
    public Withdrawal canceledWithdrawal(@PathVariable("withdrawalId") String withdrawalId) {
        Withdrawal wd = this.withdrawalRepository.findByTransactionId(withdrawalId);

        if (wd == null)
            throw new NotFoundException("Withdrawal with ID " + withdrawalId + " is non-existent");

        if (!this.withdrawalService.isPending(wd))
            throw new BadRequestException("Withdrawal with ID " + withdrawalId + " is already confirmed/canceled");

        Wallet w = this.walletRepository.findByWalletNumber(wd.getWalletNumber());
        w.setBalance(w.getBalance() + wd.getAmount()); // refund wallet balance
        wd.setStatus("canceled");

        this.withdrawalRepository.save(wd);
        this.walletRepository.save(w);

        return wd;
    }
}
