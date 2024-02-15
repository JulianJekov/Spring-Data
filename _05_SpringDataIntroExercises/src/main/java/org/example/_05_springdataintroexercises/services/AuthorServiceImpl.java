package org.example._05_springdataintroexercises.services;

import org.example._05_springdataintroexercises.entities.Author;
import org.example._05_springdataintroexercises.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        Random random = new Random();
        long size = this.authorRepository.count();
        int randomId = random.nextInt((int) size) + 1;

        return this.authorRepository.findById(randomId).get();
    }
}
