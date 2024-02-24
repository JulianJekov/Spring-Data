package org.example.springdataautomappingobjects.services;

public interface GameService {
    String addGame (String[] args);

    String deleteGame(String[] args);

    String editGame(String[] args);

    String allGamesTitleAndPrice();

    String detailGame(String[] args);
}
