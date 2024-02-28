package org.example._08_jsonprocessingexercises.services;

import jakarta.transaction.Transactional;
import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductsSoldDto;
import org.example._08_jsonprocessingexercises.domain.dtos.user.*;
import org.example._08_jsonprocessingexercises.domain.entities.Product;
import org.example._08_jsonprocessingexercises.domain.entities.User;
import org.example._08_jsonprocessingexercises.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example._08_jsonprocessingexercises.constants.Paths.USERS_WITH_SOLD_PRODUCTS_INFO_JSON_PATH;
import static org.example._08_jsonprocessingexercises.constants.Paths.USERS_WITH_SOLD_PRODUCTS_JSON_PATH;
import static org.example._08_jsonprocessingexercises.constants.Utils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<UserWithSoldItemsDto> getUsersWithSoldProducts() throws IOException {
        List<UserWithSoldItemsDto> users = this.userRepository.findAllWithSoldProducts()
                .stream()
                .map(user -> {
                    UserWithSoldItemsDto userDto = MAPPER.map(user, UserWithSoldItemsDto.class);
                    userDto.setSoldProducts(user
                            .getSoldProducts()
                            .stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(product -> MAPPER.map(product, ProductsSoldDto.class)).
                            collect(Collectors.toList()));
                    return userDto;
                }).toList();
        writeIntoJsonFile(users, USERS_WITH_SOLD_PRODUCTS_JSON_PATH);
        return users;
    }

    @Override
    @Transactional
    public UserWithSoldProductsCountDto getUsersWithSoldProductsCount() throws IOException {
        List<UserWithSoldProductsInfoDto> users = this.userRepository.findAllBySoldProductsCount()
                .stream()
                .map(user -> {
                    UserWithSoldProductsInfoDto userDto = MAPPER.map(user, UserWithSoldProductsInfoDto.class);

                    SoldProductsCountDto soldProductsCountDto = new SoldProductsCountDto();

                    soldProductsCountDto.setProducts(user
                            .getSoldProducts()
                            .stream()
                            .filter(sold -> sold.getBuyer() != null)
                            .map(sold -> MAPPER.map(sold, ProductSoldInfoDto.class))
                            .collect(Collectors.toSet()));
                    soldProductsCountDto.setCount(soldProductsCountDto.getProducts().size());
                    userDto.setSoldProducts(soldProductsCountDto);

                    return userDto;
                })
                .sorted((u1, u2) -> {
                    int compare = u2.getSoldProducts().getCount() - u1.getSoldProducts().getCount();
                    if (compare == 0) {
                        compare = u1.getLastName().compareTo(u2.getLastName());
                    }
                    return compare;
                })
                .toList();

        UserWithSoldProductsCountDto userWithSoldProductsCountDto = new UserWithSoldProductsCountDto();
        userWithSoldProductsCountDto.setUsers(users);
        userWithSoldProductsCountDto.setUsersCount(users.size());

        FileWriter fileWriter = new FileWriter(USERS_WITH_SOLD_PRODUCTS_INFO_JSON_PATH.toFile());
        GSON.toJson(userWithSoldProductsCountDto, fileWriter);

        return userWithSoldProductsCountDto;
    }
}
