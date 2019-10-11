package me.pyradian.ojackpayment.controller;

import me.pyradian.ojackpayment.model.Wallet;
import me.pyradian.ojackpayment.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    // this controller is test purpose only

    @Autowired
    private WalletRepository walletRepository;

    @ResponseBody
    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "{\"msg\": \"Hello World. Successfully started\"}";
    }

//    @ResponseBody
//    @RequestMapping("/createwallet")
//    public Wallet createWallet() {
//        Wallet w = new Wallet("12sdfsdfdfsdf4", 100020, "CUSTOMER");
//
//        this.walletRepository.insert(w);
//        return "{\"msg\": \"Hello World. Successfully started\"}";
//        return w;
//    }


}
