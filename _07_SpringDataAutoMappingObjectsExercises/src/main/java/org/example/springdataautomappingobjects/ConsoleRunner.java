package org.example.springdataautomappingobjects;

import org.example.springdataautomappingobjects.services.GameService;
import org.example.springdataautomappingobjects.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static org.example.springdataautomappingobjects.constants.Commands.*;
import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.COMMAND_NOT_FOUND_MESSAGE;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enter command:");
        String input = scanner.nextLine();
        while (!input.equals("End")) {
            final String[] inputData = input.split("\\|");
            final String command = inputData[0];
            String result;
            switch (command) {
                case REGISTER_USER:
                    result = this.userService.registerUser(inputData);
                    break;
                case LOGIN_USER:
                    result = this.userService.loginUser(inputData);
                    break;
                case LOGOUT:
                    result = this.userService.logout();
                    break;
                case ADD_GAME:
                    result = this.gameService.addGame(inputData);
                    break;
                case EDIT_GAME:
                    result = this.gameService.editGame(inputData);
                    break;
                case DELETE_GAME:
                    result = this.gameService.deleteGame(inputData);
                    break;
                case VIEW_ALL_GAMES:
                    result = this.gameService.allGamesTitleAndPrice();
                    break;
                case DETAIL_GAME:
                    result = this.gameService.detailGame(inputData);
                    break;
                default:
                    result = COMMAND_NOT_FOUND_MESSAGE;
                    break;
            }
            System.out.println(result);
            System.out.println("Enter new command:");
            input = scanner.nextLine();
        }
    }
}
