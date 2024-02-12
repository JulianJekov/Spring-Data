package org.example._05_springdataintro.services;

import org.example._05_springdataintro.models.Account;
import org.example._05_springdataintro.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal amount, Long id) {
        Optional<Account> account = this.accountRepository.findById(id);
        
        if (account.isEmpty()) {
            throw new RuntimeException("Account does not exist");
        }

        BigDecimal currentBalance = account.get().getBalance();

        if(amount.compareTo(currentBalance) > 0) {
            throw new RuntimeException("Cannot withdraw!");
        }

        account.get().setBalance(currentBalance.subtract(amount));
        accountRepository.save(account.get());
    }

    @Override
    public void depositMoney(BigDecimal amount, Long id) {
        Account account = this.accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        BigDecimal balance = account.getBalance();
        BigDecimal add = balance.add(amount);
        account.setBalance(add);
        this.accountRepository.save(account);
    }
}
