package org.example._09_xmlprocessingexercises.services.Seed;

import org.example._09_xmlprocessingexercises.domain.dtos.category.CategoryImportDtoWrapper;
import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsImportDtoWrapper;
import org.example._09_xmlprocessingexercises.domain.dtos.user.UsersImportDtoWrapper;
import org.example._09_xmlprocessingexercises.domain.entities.Category;
import org.example._09_xmlprocessingexercises.domain.entities.Product;
import org.example._09_xmlprocessingexercises.domain.entities.User;
import org.example._09_xmlprocessingexercises.repositories.CategoryRepository;
import org.example._09_xmlprocessingexercises.repositories.ProductRepository;
import org.example._09_xmlprocessingexercises.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.example._09_xmlprocessingexercises.constants.Paths.*;
import static org.example._09_xmlprocessingexercises.constants.Utils.MAPPER;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void seedUsers() throws IOException, JAXBException {
        if (this.userRepository.count() == 0) {

            final FileReader fileReader = new FileReader(USER_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(UsersImportDtoWrapper.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final UsersImportDtoWrapper usersDto = (UsersImportDtoWrapper) unmarshaller.unmarshal(fileReader);

            final List<User> users = usersDto.getUsers().stream()
                    .map(userDto -> MAPPER.map(userDto, User.class))
                    .toList();

            this.userRepository.saveAllAndFlush(users);
        }
    }

    @Override
    public void seedCategories() throws FileNotFoundException, JAXBException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORIES_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(CategoryImportDtoWrapper.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final CategoryImportDtoWrapper categoriesDto = (CategoryImportDtoWrapper) unmarshaller.unmarshal(fileReader);

            final List<Category> categories = categoriesDto.getCategories().stream()
                    .map(categoryDto -> MAPPER.map(categoryDto, Category.class))
                    .collect(Collectors.toList());

            this.categoryRepository.saveAllAndFlush(categories);
        }
    }

    @Override
    public void seedProducts() throws FileNotFoundException, JAXBException {
        if (this.productRepository.count() == 0) {

            final FileReader fileReader = new FileReader(PRODUCTS_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(ProductsImportDtoWrapper.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final ProductsImportDtoWrapper productsDto = (ProductsImportDtoWrapper) unmarshaller.unmarshal(fileReader);

            final List<Product> products = productsDto.getProducts().stream()
                    .map(productDto -> MAPPER.map(productDto, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .toList();

            this.productRepository.saveAllAndFlush(products);
        }
    }

    private Product setRandomCategories(Product product) {

        final Random random = new Random();
        final long categoriesCount = this.categoryRepository.count();
        final long numberOfCategories = random.nextLong(categoriesCount + 1);

        final Set<Category> categories = new HashSet<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Long randomId = random.nextLong(1, categoriesCount + 1);
            Optional<Category> category = this.categoryRepository.findById(randomId);
            categories.add(category.get());
        }

        product.setCategories(categories);

        return product;
    }

    private Product setRandomBuyer(Product product) {

        if (product.getPrice().compareTo(BigDecimal.valueOf(900)) > 0) {
            return product;
        }

        final long usersCount = this.userRepository.count();
        final long randomId = new Random().nextLong(1, usersCount + 1);
        Optional<User> buyer = this.userRepository.findById(randomId);

        while (buyer.equals(product.getSeller())) {
            buyer = this.userRepository.findById(randomId);
        }

        product.setBuyer(buyer.get());
        return product;
    }

    private Product setRandomSeller(Product product) {
        final long usersCount = this.userRepository.count();
        final long randomId = new Random().nextLong(1, usersCount + 1);
        final Optional<User> seller = this.userRepository.findById(randomId);
        product.setSeller(seller.get());
        return product;
    }
}
