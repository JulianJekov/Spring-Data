package org.example.springdataautomappingobjects.domain.dtos;

public class UserDto {
    private String email;
    private String password;
    private String fullName;
    private Boolean isAdmin;

    public UserDto() {
    }

    public UserDto(String email, String password, String fullName, Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
