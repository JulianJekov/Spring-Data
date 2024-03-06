package org.example._09_xmlprocessingexercises.services.User;

import jakarta.transaction.Transactional;
import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductSoldInfoDto;
import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsSoldDto;
import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsSoldDtoWrapper;
import org.example._09_xmlprocessingexercises.domain.dtos.product.SoldProductsCountDto;
import org.example._09_xmlprocessingexercises.domain.dtos.user.*;
import org.example._09_xmlprocessingexercises.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example._09_xmlprocessingexercises.constants.Paths.*;
import static org.example._09_xmlprocessingexercises.constants.Utils.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<UserWithSoldProductsDto> getUsersWithSoldProducts() throws JAXBException {

        final List<UserWithSoldProductsDto> users = this.userRepository.findAllWithSoldProducts()
                .stream()
                .map(user -> {
                    final UserWithSoldProductsDto userDto = MAPPER.map(user, UserWithSoldProductsDto.class);
                    final ProductsSoldDtoWrapper productsSoldDtoWrapper = new ProductsSoldDtoWrapper();

                    productsSoldDtoWrapper.setProducts(user
                            .getSoldProducts()
                            .stream()
                            .filter(product -> product.getBuyer() != null)
                            .map(product -> MAPPER.map(product, ProductsSoldDto.class)).
                            collect(Collectors.toList()));

                    userDto.setSoldProducts(productsSoldDtoWrapper);

                    return userDto;
                }).toList();

        final UsersWithSoldProductsDtoWrapper usersWithSoldProductsDtoWrapper = new UsersWithSoldProductsDtoWrapper();
        usersWithSoldProductsDtoWrapper.setUsers(users);

        writeIntoXmlFile(usersWithSoldProductsDtoWrapper, USERS_WITH_SOLD_PRODUCTS_XML_PATH);

        return users;
    }

    @Override
    @Transactional
    public UserWithSoldProductsCountDtoWrapper getUsersWithSoldProductsCount() throws JAXBException {

        final List<UserWithSoldProductsInfoDto> users = this.userRepository.findAllBySoldProductsCount()
                .stream()
                .map(user -> {
                    final UserWithSoldProductsInfoDto userDto = MAPPER.map(user, UserWithSoldProductsInfoDto.class);

                    final SoldProductsCountDto soldProductsCountDto = new SoldProductsCountDto();

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
                .sorted(Comparator.comparingInt((UserWithSoldProductsInfoDto u) ->
                                u.getSoldProducts().getCount()).reversed()
                        .thenComparing(UserWithSoldProductsInfoDto::getLastName))
                .toList();

        final UserWithSoldProductsCountDtoWrapper userWithSoldProductsCountDtoWrapper =
                new UserWithSoldProductsCountDtoWrapper();

        userWithSoldProductsCountDtoWrapper.setUsers(users);
        userWithSoldProductsCountDtoWrapper.setUsersCount(users.size());

        writeIntoXmlFile(userWithSoldProductsCountDtoWrapper, USERS_WITH_SOLD_PRODUCTS_INFO_XML_PATH);

        return userWithSoldProductsCountDtoWrapper;
    }
}