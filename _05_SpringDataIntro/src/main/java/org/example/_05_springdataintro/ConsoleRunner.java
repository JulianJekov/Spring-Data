package org.example._05_springdataintro;

import org.example._05_springdataintro.models.User;
import org.example._05_springdataintro.services.AccountService;
import org.example._05_springdataintro.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    public ConsoleRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = this.userService.findByUsername("first");
        this.accountService.depositMoney(BigDecimal.TEN, user.getId());

        this.accountService.withdrawMoney(BigDecimal.TWO, user.getId());
    }
}
