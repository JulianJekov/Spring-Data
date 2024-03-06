package org.example._09_xmlprocessingexercises.domain.dtos.user;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsCountDtoWrapper {

    @XmlAttribute(name = "count")
    private Integer usersCount;

    @XmlElement(name = "user")
    private List<UserWithSoldProductsInfoDto> users;

    public UserWithSoldProductsCountDtoWrapper() {
    }

    public UserWithSoldProductsCountDtoWrapper(List<UserWithSoldProductsInfoDto> users) {
        this.users = users;
        this.usersCount = users.size();
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
