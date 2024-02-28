package org.example._08_jsonprocessingexercises.constants;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path USER_JSON_PATH = Path.of("src", "main", "resources", "dbContent", "users.json");
    public static final Path CATEGORIES_JSON_PATH = Path.of("src", "main", "resources", "dbContent", "categories.json");
    public static final Path PRODUCTS_JSON_PATH = Path.of("src", "main", "resources", "dbContent", "products.json");

    public static final Path PRODUCTS_IN_RANGE_JSON_PATH =
            Path.of("src", "main", "resources", "outputJson", "products-in-range.json");

    public static final Path USERS_WITH_SOLD_PRODUCTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputJson", "users-sold-products.json");

    public static final Path CATEGORIES_BY_PRODUCTS_COUNT_JSON_PATH =
            Path.of("src", "main", "resources", "outputJson", "categories-by-products.json");

public static final Path USERS_WITH_SOLD_PRODUCTS_INFO_JSON_PATH =
            Path.of("src", "main", "resources", "outputJson", "users-and-products.json");
}
