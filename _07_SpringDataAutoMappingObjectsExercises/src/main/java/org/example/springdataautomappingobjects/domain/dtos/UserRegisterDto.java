package org.example.springdataautomappingobjects.domain.dtos;

import java.util.regex.Pattern;

import static org.example.springdataautomappingobjects.constants.ValidationsAndMessages.*;

public class UserRegisterDto {
    public static final String WAS_REGISTERED = " was registered";
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String email, String password, String confirmPassword, String fullName) {
        this.setEmail(email);
        this.setPassword(password);
        this.setConfirmPassword(confirmPassword);
        this.setFullName(fullName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        boolean isEmailValid = Pattern.matches(EMAIL_PATTERN, email);

        if (!isEmailValid) {
            throw new IllegalArgumentException(EMAIL_NOT_VALID_MESSAGE);
        }

        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        boolean isPasswordValid = Pattern.matches(PASSWORD_PATTERN, password);

        if (!isPasswordValid) {
            throw new IllegalArgumentException(PASSWORD_EMAIL_NOT_VALID);
        }

        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (!this.password.equals(confirmPassword)) {
            throw new IllegalArgumentException(PASSWORDS_NOT_THE_SAME);
        }

        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String successfulRegisterUser () {
        return this.fullName + WAS_REGISTERED;
    }

}
