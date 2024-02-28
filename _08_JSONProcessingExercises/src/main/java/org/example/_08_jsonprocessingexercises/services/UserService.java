package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.domain.dtos.user.UserWithSoldItemsDto;
import org.example._08_jsonprocessingexercises.domain.dtos.user.UserWithSoldProductsCountDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserWithSoldItemsDto> getUsersWithSoldProducts() throws IOException;
    UserWithSoldProductsCountDto getUsersWithSoldProductsCount() throws IOException;


}
