package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final ShampooRepository shampooRepository;
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public ConsoleRunner(ShampooRepository shampooRepository,
                         ShampooService shampooService,
                         IngredientService ingredientService) {
        this.shampooRepository = shampooRepository;
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        //P01();
        //P02();
        //P03();
        //P04();
        //P05();
        //P06();
        //P07();
        //P08();
        //P09();
        //P10();
        P11();
        //demo();
    }
    
    //Select query
    private void P01() {
        this.shampooService.selectShampoosBySize(Size.MEDIUM)
                .forEach(System.out::println);
    }

    private void P02() {
        shampooService.selectShampoosBySizeOrLabel(Size.MEDIUM, 10)
                .forEach(System.out::println);
    }

    private void P03() {
        shampooService.selectShampoosByPriceHigherThan(BigDecimal.valueOf(5))
                .forEach(System.out::println);
    }

    private void P04() {
        ingredientService.selectIngredientsByNameStartWith("M")
                .forEach(System.out::println);
    }

    private void P05() {
        ingredientService.selectIngredientsByNameInAGivenList(
                        List.of("Lavender", "Herbs", "Apple"))
                .forEach(System.out::println);
    }

    private void P06() {
        int count = shampooService.countAllWithPriceLowerThan(BigDecimal.valueOf(8.50));
        System.out.println(count);
    }

    //JPQL query
    private void P07() {
        shampooService.findAllByIngredientsNames(Set.of("Berry", "Mineral-Collagen"))
                .forEach(System.out::println);
    }

    private void P08(){
        shampooService.selectAllWithIngredientsLessThan(2)
                .forEach(s -> System.out.println(s.getBrand()));
    }


    private void P09() {
        ingredientService.deleteByName("Nettle");
    }

    private void P10() {
        ingredientService.increasePriceByPercent(BigDecimal.valueOf(0.1));
    }

    private void P11() {
        ingredientService.increasePriceByNames(List.of("Apple", "Berry"), BigDecimal.valueOf(0.1));
    }

    private void demo() {
        Scanner scanner = new Scanner(System.in);

        shampooRepository.findAllByBrand("Cotton Fresh")
                .forEach(s -> System.out.println(s.getId()));

        shampooRepository.findAllByBrandAndSize("Cotton Fresh", Size.SMALL)
                .forEach(s -> System.out.println(s.getId()));

        String sizeName = scanner.nextLine();
        Size size = Size.valueOf(sizeName);

        this.shampooRepository.findAllBySizeOrderById(size)
                .forEach(System.out::println);

        String firstIngredientName = scanner.nextLine();
        String secondIngredientName = scanner.nextLine();
        Set<String> ingredientNames = Set.of(firstIngredientName, secondIngredientName);
        shampooRepository.findByIngredientsNames(ingredientNames)
                .forEach(System.out::println);
    }
}
