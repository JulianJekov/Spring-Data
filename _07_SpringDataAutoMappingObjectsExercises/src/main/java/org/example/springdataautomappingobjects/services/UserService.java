package org.example.springdataautomappingobjects.services;

public interface UserService {
    String registerUser(String[] args);

    String loginUser(String[] args);

    String logout();

    boolean isUserLogged();

    boolean isUserAdmin();
}
