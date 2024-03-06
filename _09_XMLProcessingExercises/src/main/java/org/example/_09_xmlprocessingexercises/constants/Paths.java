package org.example._09_xmlprocessingexercises.constants;

import java.nio.file.Path;

public enum Paths {
    ;

    public static final Path USER_XML_PATH = Path.of("src", "main", "resources", "DbContent", "users.xml");
    public static final Path CATEGORIES_XML_PATH = Path.of("src", "main", "resources", "dbContent", "categories.xml");
    public static final Path PRODUCTS_XML_PATH = Path.of("src", "main", "resources", "dbContent", "products.xml");

    public static final Path PRODUCTS_IN_RANGE_XML_PATH =
            Path.of("src", "main", "resources", "OutputXML", "products-in-range.xml");

    public static final Path USERS_WITH_SOLD_PRODUCTS_XML_PATH =
            Path.of("src", "main", "resources", "OutputXML", "users-sold-products.xml");

    public static final Path CATEGORIES_BY_PRODUCTS_COUNT_XML_PATH =
            Path.of("src", "main", "resources", "OutputXML", "categories-by-products.xml");

    public static final Path USERS_WITH_SOLD_PRODUCTS_INFO_XML_PATH =
            Path.of("src", "main", "resources", "OutputXML", "users-and-products.xml");
}
