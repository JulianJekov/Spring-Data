package org.example.springdataautomappingobjects.services;

import org.example.springdataautomappingobjects.domain.dtos.UserDto;
import org.example.springdataautomappingobjects.domain.dtos.UserLoginDto;
import org.example.springdataautomappingobjects.domain.dtos.UserRegisterDto;
import org.example.springdataautomappingobjects.domain.entities.User;
import org.example.springdataautomappingobjects.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.*;

@Service
public class UserServiceImpl implements UserService {

    private UserDto userDto;
    private final UserRepository userRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        if (isUserLogged()) {
            return CAN_NOT_LOGOUT;
        }
        String loggedOutUser = this.userDto.getFullName();
        this.userDto = null;
        return String.format(USER_LOGGED_OUT, loggedOutUser);
    }

    @Override
    public boolean isUserLogged() {
        return this.userDto == null;
    }

    @Override
    public boolean isUserAdmin() {
        return this.userDto.getAdmin();
    }
}
