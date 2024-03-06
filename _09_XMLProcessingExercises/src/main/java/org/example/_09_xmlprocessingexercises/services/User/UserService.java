package org.example._09_xmlprocessingexercises.services.User;

import org.example._09_xmlprocessingexercises.domain.dtos.user.UserWithSoldProductsDto;
import org.example._09_xmlprocessingexercises.domain.dtos.user.UserWithSoldProductsCountDtoWrapper;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UserWithSoldProductsDto> getUsersWithSoldProducts() throws IOException, JAXBException;
    UserWithSoldProductsCountDtoWrapper getUsersWithSoldProductsCount() throws IOException, JAXBException;


}
