package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductImportDto;
import org.example._08_jsonprocessingexercises.domain.dtos.category.CategoryImportDto;
import org.example._08_jsonprocessingexercises.domain.dtos.user.UserImportDto;
import org.example._08_jsonprocessingexercises.domain.entities.Category;
import org.example._08_jsonprocessingexercises.domain.entities.Product;
import org.example._08_jsonprocessingexercises.domain.entities.User;
import org.example._08_jsonprocessingexercises.repositories.CategoryRepository;
import org.example._08_jsonprocessingexercises.repositories.ProductRepository;
import org.example._08_jsonprocessingexercises.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

import static org.example._08_jsonprocessingexercises.constants.Paths.*;
import static org.example._08_jsonprocessingexercises.constants.Utils.GSON;
import static org.example._08_jsonprocessingexercises.constants.Utils.MAPPER;

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
    public void seedUsers() throws FileNotFoundException {
        if (this.userRepository.count() == 0) {
            final FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());
            final UserImportDto[] usersImportDto = GSON.fromJson(fileReader, UserImportDto[].class);

            final List<User> users = Arrays
                    .stream(usersImportDto)
                    .map(userImportDto -> MAPPER.map(userImportDto, User.class))
                    .toList();

            this.userRepository.saveAllAndFlush(users);
        }
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        if (this.categoryRepository.count() == 0) {
            final FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH.toFile());
            final CategoryImportDto[] categoriesImportDto = GSON.fromJson(fileReader, CategoryImportDto[].class);

            List<Category> categories = Arrays
                    .stream(categoriesImportDto)
                    .map(categoryImportDto -> MAPPER.map(categoryImportDto, Category.class))
                    .toList();

            this.categoryRepository.saveAllAndFlush(categories);
        }
    }

    @Override
    public void seedProducts() throws FileNotFoundException {
        if (this.productRepository.count() == 0) {

            final FileReader fileReader = new FileReader(PRODUCTS_JSON_PATH.toFile());
            final ProductImportDto[] productsDto = GSON.fromJson(fileReader, ProductImportDto[].class);

            List<Product> products = Arrays
                    .stream(productsDto)
                    .map(productImportDto -> MAPPER.map(productImportDto, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategories)
                    .toList();

            this.productRepository.saveAllAndFlush(products);
        }
    }

    private Product setRandomCategories(Product product) {

        Random random = new Random();
        long categoriesCount = this.categoryRepository.count();
        long numberOfCategories = random.nextLong(categoriesCount + 1);

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Long randomId = random.nextLong(1,categoriesCount + 1);
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

        long usersCount = this.userRepository.count();
        long randomId = new Random().nextLong(1,usersCount + 1);
        Optional<User> buyer = this.userRepository.findById(randomId);

        while (buyer.equals(product.getSeller())) {
            buyer = this.userRepository.findById(randomId);
        }

        product.setBuyer(buyer.get());
        return product;
    }

    private Product setRandomSeller(Product product) {
        long usersCount = this.userRepository.count();
        long randomId = new Random().nextLong(1,usersCount + 1);
        Optional<User> seller = this.userRepository.findById(randomId);
        product.setSeller(seller.get());
        return product;
    }
}
