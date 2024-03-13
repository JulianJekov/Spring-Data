package org.example.usersystem.anotations.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.usersystem.anotations.AnnotationsUtil;
import org.example.usersystem.constants.Messages;

import java.util.regex.Pattern;


public class PasswordValidator implements ConstraintValidator<Password, CharSequence> {

    private static final Pattern PATTERN_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGIT = Pattern.compile("[0-9]");
    private static final Pattern PATTERN_SYMBOL = Pattern.compile("[!@#$%^&*()_+<>?]");

    private int minLength;
    private int maxLength;
    private boolean hasUpper;
    private boolean hasLower;
    private boolean hasDigit;
    private boolean hasSpecialSymbol;


    @Override
    public void initialize(Password password) {
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.hasUpper = password.containsUpperCase();
        this. hasLower = password.containsLowerCase();
        this.hasDigit = password.containsDigit();
        this.hasSpecialSymbol = password.containsSpecialSymbol();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if(value == null) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_CANNOT_BE_NULL);
            return false;
        }

        if(value.length() < minLength) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_TOO_SHORT);
            return false;
        }

        if(value.length() > maxLength) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_TOO_LONG);
            return false;
        }

        String password = value.toString();


        if (!PATTERN_LOWER.matcher(password).find() && this.hasLower) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_SHOULD_CONTAIN_LOWERCASE_LETTER);
            return false;
        }

        if (!PATTERN_UPPER.matcher(password).find() && this.hasUpper) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_SHOULD_CONTAIN_UPPERCASE_LETTER);
            return false;
        }

        if (!PATTERN_DIGIT.matcher(password).find() && this.hasDigit) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_SHOULD_CONTAIN_DIGIT);
            return false;
        }

        if (!PATTERN_SYMBOL.matcher(password).find() && this.hasSpecialSymbol) {
            AnnotationsUtil.setErrorMessage(context, Messages.PASSWORD_SHOULD_CONTAIN_SPECIAL_SYMBOL);
            return false;
        }

        return true;
    }
}
