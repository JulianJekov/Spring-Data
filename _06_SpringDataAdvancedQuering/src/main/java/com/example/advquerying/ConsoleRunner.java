package com.example.advquerying;

import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private ShampooRepository shampooRepository;

    @Autowired
    public ConsoleRunner(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        shampooRepository.findAllByBrand("")
    }
}
