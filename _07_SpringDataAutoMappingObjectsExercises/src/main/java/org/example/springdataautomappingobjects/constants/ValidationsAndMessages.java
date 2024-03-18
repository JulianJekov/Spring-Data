package org.example.springdataautomappingobjects.constants;

public enum ValidationsAndMessages {
    ;

    public static final String EMAIL_PATTERN = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
    public static final String TITLE_PATTERN = "^([A-Z])([a-z]+){3,100}";
    public static final String IMAGE_URL_PATTERN = "^(http|https):\\/\\/[^\\|]+";

    public static final String EMAIL_NOT_VALID_MESSAGE = "Incorrect email.";
    public static final String PASSWORD_EMAIL_NOT_VALID = "Incorrect username / password";
    public static final String PASSWORDS_NOT_THE_SAME = "Passwords not matching";

    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found";

    public static final String SUCCESSFULLY_LOGGED_IN = "Successfully logged in ";
    public static final String CAN_NOT_LOGOUT = "Cannot log out. No user was logged in.";
    public static final String USER_LOGGED_OUT = "User %s successfully logged out";

    public static final String TITLE_NOT_VALID_MESSAGE = "Title is not valid";
    public static final String PRICE_IS_NEGATIVE = "Price can not be negative";

    public static final String SIZE_IS_NEGATIVE = "Size can not be negative";
    public static final String TRAILER_NOT_VALID = "Trailer length should be 11 characters long";

    public static final String URL_NOT_VALID = "Url is not valid";
    public static final String DESCRIPTION_NOT_VALID = "Description should be at least 20 characters";
    public static final String GAME_ADDED = "Added ";
    public static final String NO_LOGGED_USER = "No user was logged in";
    public static final String USER_IS_NOT_ADMIN = "User is not Admin";

    public static final String GAME_NOT_EXIST = "Game with id %d does not exist";
    public static final String DELETE_GAME_MESSAGE = "Deleted %s";

    public static final String NO_SUCH_PARAMETER_TYPE = "No such game parameter type: %s";
    public static final String EDITED_GAME_MESSAGE = "Edited %s";

    public static final String ITEM_ADDED_TO_CART = "%s added to cart.";
    public static final String ITEM_REMOVED_FROM_CART = "%s removed from cart.";
    public static final String GAME_TITLE_NOT_EXIST = "No such game exist";
    public static final String ITEMS_BOUGHT = "Successfully bought games: %s";
}
