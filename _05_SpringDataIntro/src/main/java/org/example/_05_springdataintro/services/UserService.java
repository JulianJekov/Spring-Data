package org.example._05_springdataintro.services;

import org.example._05_springdataintro.models.User;

public interface UserService {
    void register(String username, int age);

    User findByUsername(String username);
}
