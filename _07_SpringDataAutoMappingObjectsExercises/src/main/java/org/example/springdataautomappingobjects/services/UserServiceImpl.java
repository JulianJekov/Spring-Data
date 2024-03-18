package org.example.springdataautomappingobjects.services;

import org.example.springdataautomappingobjects.constants.ValidationsAndMessages;
import org.example.springdataautomappingobjects.domain.dtos.UserDto;
import org.example.springdataautomappingobjects.domain.dtos.UserLoginDto;
import org.example.springdataautomappingobjects.domain.dtos.UserRegisterDto;
import org.example.springdataautomappingobjects.domain.entities.Game;
import org.example.springdataautomappingobjects.domain.entities.Order;
import org.example.springdataautomappingobjects.domain.entities.User;
import org.example.springdataautomappingobjects.repositories.GameRepository;
import org.example.springdataautomappingobjects.repositories.OrderRepository;
import org.example.springdataautomappingobjects.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDto userDto;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String registerUser(String[] args) {
        String email = args[1];
        String password = args[2];
        String confirmPassword = args[3];
        String fullName = args[4];

        UserRegisterDto userRegisterDto;

        try {
            userRegisterDto = new UserRegisterDto(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        //TODO catch if email is not already used

        final User user = this.mapper.map(userRegisterDto, User.class);

        user.setAdmin(this.userRepository.count() == 0);

        this.userRepository.save(user);

        return userRegisterDto.successfulRegisterUser();
    }

    @Override
    public String loginUser(String[] args) {

        String email = args[1];
        String password = args[2];

        UserLoginDto userLoginDto = new UserLoginDto(email, password);

        User user = this.userRepository.findByEmail(userLoginDto.getEmail());

        if (user == null) {
            return PASSWORD_EMAIL_NOT_VALID;
        }

        this.userDto = this.mapper.map(user, UserDto.class);

        return SUCCESSFULLY_LOGGED_IN + user.getFullName();
    }

    @Override
    public String logout() {

        if (!isUserLogged()) {
            return CAN_NOT_LOGOUT;
        }
        String loggedOutUser = this.userDto.getFullName();
        this.userDto = null;
        return String.format(USER_LOGGED_OUT, loggedOutUser);
    }

    @Override
    public String addItem(String[] args) {
        String itemTitle = args[1];

        if (isUserLogged()) {
            User user = getLogedUser();
            Optional<Game> optionalGame = this.gameRepository.findByTitle(itemTitle);
            if (optionalGame.isPresent()) {
                Set<Game> games = user.getOrder().getGames();
                Game game = optionalGame.get();

                if(games.contains(game)) {
                    return "Game already owned";
                }

                if(user.getGames().contains(game)) {
                    return "Game already in cart";
                }

                this.getLogedUser().getOrder().getGames().add(game);
                return String.format(ValidationsAndMessages.ITEM_ADDED_TO_CART, itemTitle);
            }
            return ValidationsAndMessages.GAME_TITLE_NOT_EXIST;
        }
        return NO_LOGGED_USER;
    }

    @Override
    public String removeItem(String[] args) {
        String itemTitle = args[1];
        Set<Game> games = this.getLogedUser().getOrder().getGames();
        if (isUserLogged()) {
            Game game = games
                    .stream()
                    .filter(g -> g.getTitle().equals(itemTitle)).findFirst()
                    .orElse(null);

            if (game != null) {
                games.remove(game);
                return String.format(ValidationsAndMessages.ITEM_REMOVED_FROM_CART, itemTitle);
            }
            return ValidationsAndMessages.GAME_TITLE_NOT_EXIST;
        }
        return ValidationsAndMessages.NO_LOGGED_USER;
    }

    @Override
    public String buyItem() {
        if (isUserLogged()) {
            Order order = this.getLogedUser().getOrder();
            Set<Game> games = order.getGames();
            User user = getLogedUser();
            user.getGames().addAll(games);
            this.orderRepository.saveAndFlush(order);
            this.userRepository.saveAndFlush(user);

            String output = String.format(ITEMS_BOUGHT, games
                    .stream().map(Game::getTitle).collect(Collectors.joining("\n -")));
            games.clear();
            return output;
        }
        return ValidationsAndMessages.NO_LOGGED_USER;
    }

    @Override
    public boolean isUserLogged() {
        return this.userDto != null;
    }

    public User getLogedUser () {
        return this.mapper.map(this.userDto, User.class);
    }

    @Override
    public boolean isUserAdmin() {
        return this.userDto.getAdmin();
    }
}
