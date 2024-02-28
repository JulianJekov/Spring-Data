package org.example._08_jsonprocessingexercises.domain.dtos.user;

import java.util.ArrayList;
import java.util.List;

public class UserWithSoldProductsCountDto {
    private Integer usersCount;
    private List<UserWithSoldProductsInfoDto> users;

    public UserWithSoldProductsCountDto() {
        this.users = new ArrayList<>();
    }

    public Integer getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(Integer usersCount) {
        this.usersCount = usersCount;
    }

    public List<UserWithSoldProductsInfoDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserWithSoldProductsInfoDto> users) {
        this.users = users;
    }
}
