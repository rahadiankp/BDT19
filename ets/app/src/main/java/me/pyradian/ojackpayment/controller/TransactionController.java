package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.model.Transaction;
import me.pyradian.ojackpayment.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {
    public static final String BASE_URL = "/api/v1/transaction";

    private TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping
    public List<Transaction> getAllTransaction(@RequestParam("status") String status) {
        //System.out.println(msg);
        return this.transactionRepository.findAll();
    }
}
