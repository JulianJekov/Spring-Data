package org.example.springdataautomappingobjects.services;


import org.example.springdataautomappingobjects.domain.dtos.*;
import org.example.springdataautomappingobjects.domain.entities.Game;
import org.example.springdataautomappingobjects.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.*;

@Service
public class GameServiceImpl implements GameService {


    private final GameRepository gameRepository;
    private final UserService userService;
    private final ModelMapper mapper = new ModelMapper();
    private UserDto userDto;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {

        if (this.userService.isUserLogged()) {
            return NO_LOGGED_USER;
        }

        if (!this.userService.isUserAdmin()) {
            return USER_IS_NOT_ADMIN;
        }

        String title = args[1];
        BigDecimal price = new BigDecimal(args[2]);
        float size = Float.parseFloat(args[3]);
        String trailer = args[4];
        String url = args[5];
        String description = args[6];
        LocalDate releaseDate = LocalDate.parse(args[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        GameAddDto gameAddDto;

        try {
            gameAddDto = new GameAddDto(title, price, size, trailer, url, description, releaseDate);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        System.out.println();
        Game game = this.mapper.map(gameAddDto, Game.class);

        this.gameRepository.save(game);

        return GAME_ADDED + game.getTitle();
    }

    @Override
    public String deleteGame(String[] args) {

        if (this.userService.isUserLogged()) {
            return NO_LOGGED_USER;
        }

        if (!this.userService.isUserAdmin()) {
            return USER_IS_NOT_ADMIN;
        }

        long gameId = Long.parseLong(args[1]);

        Optional<Game> gameToDelete = this.gameRepository.findById(gameId);

        if (gameToDelete.isEmpty()) {
            return String.format(GAME_NOT_EXIST, gameId);
        }

        String gameToDeleteTitle = gameToDelete.get().getTitle();
        this.gameRepository.delete(gameToDelete.get());

        return String.format(DELETE_GAME_MESSAGE, gameToDeleteTitle);
    }

    @Override
    public String editGame(String[] args) {

        if (this.userService.isUserLogged()) {
            return NO_LOGGED_USER;
        }

        if (!this.userService.isUserAdmin()) {
            return USER_IS_NOT_ADMIN;
        }

        long gameId = Long.parseLong(args[1]);

        Optional<Game> gameToEdit = this.gameRepository.findById(gameId);

        if (gameToEdit.isEmpty()) {
            return String.format(GAME_NOT_EXIST, gameId);
        }

        GameEditDto gameEditDto = mapper.map(gameToEdit.get(), GameEditDto.class);

        try {
            for (int i = 2; i < args.length; i++) {
                String[] tokens = args[i].split("=");
                switch (tokens[0]) {
                    case "title":
                        gameEditDto.setTitle(tokens[1]);
                        break;
                    case "price":
                        gameEditDto.setPrice(new BigDecimal(tokens[1]));
                        break;
                    case "size":
                        gameEditDto.setSize(Float.parseFloat(tokens[1]));
                        break;
                    case "trailer":
                        gameEditDto.setTrailer(tokens[1]);
                        break;
                    case "thumbnailURL":
                        gameEditDto.setImageUrl(tokens[1]);
                        break;
                    case "description":
                        gameEditDto.setDescription(tokens[1]);
                        break;
                    case "releaseDate":
                        gameEditDto.setReleaseDate(LocalDate.parse(args[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                        break;
                    default:
                        throw new IllegalArgumentException(String.format(NO_SUCH_PARAMETER_TYPE, args[1]));
                }
            }
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        mapper.map(gameEditDto, gameToEdit.get());

        this.gameRepository.save(gameToEdit.get());


        return String.format(EDITED_GAME_MESSAGE, gameToEdit.get().getTitle());
    }

    @Override
    public String allGamesTitleAndPrice() {
        StringBuilder sb = new StringBuilder();

        this.gameRepository.findAllByTitleAndPrice()
                .forEach(gameView -> sb.append(String.format("%s %.2f%n", gameView.getTitle(), gameView.getPrice())));

        return sb.toString().trim();
    }

    @Override
    public String detailGame(String[] args) {

        String gameTitle = args[1];
        Game game = this.gameRepository.findByTitle(gameTitle);
        GameDetailDto gameDetailDto = mapper.map(game, GameDetailDto.class);

        return gameDetailDto.toString();
    }
}
