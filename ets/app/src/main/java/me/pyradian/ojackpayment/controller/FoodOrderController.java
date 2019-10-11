package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.exception.BadRequestException;
import me.pyradian.ojackpayment.exception.NotFoundException;
import me.pyradian.ojackpayment.model.FoodOrder;
import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.FoodOrderRepository;
import me.pyradian.ojackpayment.repository.WalletRepository;
import me.pyradian.ojackpayment.service.FoodOrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FoodOrderController.BASE_URL)
public class FoodOrderController {
    public static final String BASE_URL = "/api/v1/transaction/foodorder";

    private FoodOrderRepository foodOrderRepository;
    private WalletRepository walletRepository;
    private FoodOrderService foodOrderService;

    public FoodOrderController(FoodOrderRepository foodOrderRepository, WalletRepository walletRepository,
                               FoodOrderService foodOrderService) {
        this.foodOrderRepository = foodOrderRepository;
        this.walletRepository = walletRepository;
        this.foodOrderService = foodOrderService;
    }

    @GetMapping
    public List<FoodOrder> getAllFoodOrder() {
        return this.foodOrderRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<FoodOrder> requestFoodOrder(@RequestBody FoodOrder fo) {
        int res = this.foodOrderService.isValid(fo);

        if (res == -1)
            throw new BadRequestException("Some or all fields are empty");
        else if (res == -2)
            throw new BadRequestException("Invalid bill received. Must be > 0");
        else if (res == -3)
            throw new BadRequestException("Some or all wallets credential are missing");
        else if (res == -4)
            throw new BadRequestException("Customer wallet balance is insufficient to complete the order");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Location", FoodOrderController.BASE_URL + "/" + fo.getTransactionId());
        Wallet customerWallet = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());
        customerWallet.setBalance(customerWallet.getBalance() - fo.getFoodOrderBill());
        this.foodOrderRepository.save(fo);
        this.walletRepository.save(customerWallet);

        return new ResponseEntity<>(fo, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{foodOrderId}")
    public FoodOrder getFoodOrder(@PathVariable("foodOrderId") String foodOrderId) {
        FoodOrder fo = (FoodOrder) this.foodOrderRepository.findByTransactionId(foodOrderId);
        if (fo == null)
            throw new NotFoundException("Food Order with ID " + foodOrderId + " is non-existent");

        return fo;
    }

    @PatchMapping("/confirm/{foodOrderId}")
    public FoodOrder confirmFoodOrder(@PathVariable("foodOrderId") String foodOrderId) {
        FoodOrder fo = (FoodOrder) this.foodOrderRepository.findByTransactionId(foodOrderId);

        if (fo == null)
            throw new NotFoundException("Food Order with ID " + foodOrderId + " is non-existent");

        if (!this.foodOrderService.isPending(fo))
            throw new BadRequestException("Food Order with ID " + foodOrderId + " is already confirmed/canceled");

        // fetch driver and restaurant wallet, then add fund
        Wallet driverWallet = this.walletRepository.findByWalletNumber(fo.getDriverWalletNumber());
        Wallet restaurantWallet = this.walletRepository.findByWalletNumber(fo.getRestaurantWalletNumber());
        driverWallet.setBalance(driverWallet.getBalance() + fo.getDriverAmount());
        restaurantWallet.setBalance(restaurantWallet.getBalance() + fo.getRestaurantAmount());
        this.walletRepository.save(driverWallet);
        this.walletRepository.save(restaurantWallet);

        fo.setStatus("confirmed");
        this.foodOrderRepository.save(fo);

        return fo;
    }

    @PatchMapping("/cancel/{foodOrderId}")
    public FoodOrder cancelFoodOrder(@PathVariable("foodOrderId") String foodOrderId) {
        FoodOrder fo = (FoodOrder) this.foodOrderRepository.findByTransactionId(foodOrderId);

        if (fo == null)
            throw new NotFoundException("Food Order with ID " + foodOrderId + " is non-existent");

        if (!this.foodOrderService.isPending(fo))
            throw new BadRequestException("Food Order with ID " + foodOrderId + " is already confirmed/cancel");

        // fetch customer wallet and then refund
        Wallet customerWallet = this.walletRepository.findByWalletNumber(fo.getCustomerWalletNumber());
        customerWallet.setBalance(customerWallet.getBalance() + fo.getFoodOrderBill());
        this.walletRepository.save(customerWallet);

        fo.setStatus("canceled");
        this.foodOrderRepository.save(fo);

        return fo;
    }
}
