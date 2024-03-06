package org.example._09_xmlprocessingexercises;

import org.example._09_xmlprocessingexercises.services.Category.CategoryService;
import org.example._09_xmlprocessingexercises.services.Product.ProductService;
import org.example._09_xmlprocessingexercises.services.Seed.SeedService;
import org.example._09_xmlprocessingexercises.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         ProductService productService,
                         UserService userService, CategoryService categoryService
    ) {
        this.seedService = seedService;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedService.seedAll();

//        this.productService.getProductsInRangeWithNoBuyer(500, 1000);
//        this.userService.getUsersWithSoldProducts();
//        this.categoryService.findAllByNumberOfProducts();

//        this.userService.getUsersWithSoldProductsCount();
//        this.userService.getUsersWithSoldProductsCount();
    }
}
