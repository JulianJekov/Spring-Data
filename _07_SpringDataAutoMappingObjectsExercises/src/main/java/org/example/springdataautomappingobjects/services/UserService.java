package org.example.springdataautomappingobjects.services;

import org.example.springdataautomappingobjects.domain.entities.User;

public interface UserService {
    String registerUser(String[] args);

    String loginUser(String[] args);

    String logout();

    boolean isUserLogged();

    boolean isUserAdmin();

    User getLogedUser ();

    String addItem(String[] args);

    String removeItem(String[] args);

    String buyItem();
}
