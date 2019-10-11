package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.exception.BadRequestException;
import me.pyradian.ojackpayment.exception.ConflictException;
import me.pyradian.ojackpayment.exception.NotFoundException;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import me.pyradian.ojackpayment.service.WalletService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(WalletController.BASE_URL)
public class WalletController {
    public static final String BASE_URL = "/api/v1/wallet";

    private WalletRepository walletRepository;
    private WalletService walletService;

    public WalletController(WalletRepository walletRepository, WalletService walletService) {
        this.walletRepository = walletRepository;
        this.walletService = walletService;
    }

    @GetMapping
    public List<Wallet> getAllWallet(@Nullable @RequestParam("type") String type,
                                     @Nullable @RequestParam("balance_range") String balanceRange) {

        return this.walletRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet w) {
        int res = this.walletService.isValidWallet(w);

        if (res == -1) // incomplete field
            throw new BadRequestException("Some or all fields are empty");
        else if (res == -2) // wallet number already exists
            throw new ConflictException("Wallet number is already registered");
        else if (res == -3) // invalid wallet type
            throw new BadRequestException("Invalid wallet type");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", WalletController.BASE_URL + "/" + w.getWalletNumber());
        this.walletRepository.save(w);
        return new ResponseEntity<>(w, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{walletNumber}")
    public Wallet getWallet(@PathVariable("walletNumber") String walletNumber) {
        Wallet w = this.walletRepository.findByWalletNumber(walletNumber);

        if (w == null)
            throw new NotFoundException("Wallet number " + walletNumber + " is non-existent");

        return w;
    }

}
