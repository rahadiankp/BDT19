package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.exception.BadRequestException;
import me.pyradian.ojackpayment.exception.ConflictException;
import me.pyradian.ojackpayment.exception.NotFoundException;
import me.pyradian.ojackpayment.model.Topup;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.TopupRepository;
import me.pyradian.ojackpayment.repository.WalletRepository;
import me.pyradian.ojackpayment.service.TopupService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(TopupController.BASE_URL)
public class TopupController {
    public static final String BASE_URL = "/api/v1/transaction/topup";

    private TopupRepository topupRepository;
    private WalletRepository walletRepository;
    private TopupService topupService;

    public TopupController(TopupRepository topupRepository, WalletRepository walletRepository,
                           TopupService topupService)
    {
        this.topupRepository = topupRepository;
        this.walletRepository = walletRepository;
        this.topupService = topupService;
    }

    @GetMapping
    public List<Topup> getAllTopup() {
        return this.topupRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Topup> requestTopup(@RequestBody Topup t) {
        int res = this.topupService.isValid(t);

        if (res == -1)
            throw new BadRequestException("Either Wallet Number is null or topup balance is 0");
        else if (res==-2)
            throw new NotFoundException("Wallet Number for this topup request is non-existent");
        else if (res == -3)
            throw new BadRequestException("Minimum topup balance is 25000");
        else if (res == -4)
            throw new BadRequestException("Wallet Topup is exclusive for customer only");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", TopupController.BASE_URL + "/" + t.getTransactionId());
        this.topupRepository.save(t);

        return new ResponseEntity<>(t, headers, HttpStatus.CREATED);
    }

    @PatchMapping("/confirm/{topupId}")
    public Topup confirmTopup(@PathVariable("topupId") String topupId) {
        Topup t = this.topupRepository.findByTransactionId(topupId);
        if (t == null)
            throw new NotFoundException("Topup with ID " + topupId + " is non-existent");
        if (!this.topupService.isPending(t))
            throw new ConflictException("Topup with ID " + topupId + " is already confirmed/canceled");

        Wallet w = this.walletRepository.findByWalletNumber(t.getWalletNumber());
        w.setBalance(w.getBalance() + t.getTopupBalance());
        t.setStatus("confirmed");
        this.walletRepository.save(w);
        this.topupRepository.save(t);

        return t;
    }

    @PatchMapping("/cancel/{topupId}")
    public Topup cancelTopup(@PathVariable("topupId") String topupId) {
        Topup t = this.topupRepository.findByTransactionId(topupId);

        if (!this.topupService.isPending(t))
            throw new ConflictException("Topup with ID " + topupId + " is already confirmed/canceled");

        t.setStatus("canceled");
        this.topupRepository.save(t);

        return t;
    }


}
