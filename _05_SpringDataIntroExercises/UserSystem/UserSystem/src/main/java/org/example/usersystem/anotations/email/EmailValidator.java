package org.example.usersystem.anotations.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.usersystem.anotations.AnnotationsUtil;
import org.example.usersystem.constants.Messages;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, CharSequence> {
    private int minUsernameLength;
    private int maxUsernameLength;
    private int maxHostNameLength;
    private Pattern pattern;

    @Override
    public void initialize(Email email) {
        this.minUsernameLength = email.minUserNameLength();
        this.maxUsernameLength = email.maxUsernameLength();
        this.maxHostNameLength = email.maxHostNameLength();
        this.pattern = Pattern.compile(email.regex());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        String email = value.toString();

        int userNameLength = email.indexOf("@");
        int hostNameLength = email.length() - email.lastIndexOf("@") - 1;

        if (userNameLength < this.minUsernameLength) {
            AnnotationsUtil.setErrorMessage(context, Messages.EMAIL_NAME_LENGTH_TOO_SHORT);
            return false;
        }

        if (userNameLength > this.maxUsernameLength) {
            AnnotationsUtil.setErrorMessage(context, Messages.EMAIL_NAME_LENGTH_TOO_LONG);
            return false;
        }

        if (hostNameLength > this.maxHostNameLength) {
            AnnotationsUtil.setErrorMessage(context, Messages.EMAIL_HOST_LENGTH_TOO_LONG);
            return false;
        }

        return this.pattern.matcher(email).matches();
    }
}
