package vendingmachine.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vendingmachine.domain.Product;

public class InputParser {

    public static final int PRODUCT_INDEX = 1;
    public static final int PRICE_INDEX = 2;
    public static final int QUANTITY_INDEX = 3;

    public static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static List<Product> parseProducts(String input) {
        List<Product> products = new ArrayList<>();
        for (String item : input.split(";")) {
            Product product = parseProduct(item);
            products.add(product);
        }
        return products;
    }

    private static Product parseProduct(String input) {
        List<String> splitInput = Arrays.stream(input.split("[,\\[\\]]")).toList();
        if (splitInput.size() < 4) {
            throw new IllegalArgumentException();
        }
        String productName = splitInput.get(PRODUCT_INDEX);
        int price = parseInt(splitInput.get(PRICE_INDEX));
        int quantity = parseInt(splitInput.get(QUANTITY_INDEX));
        return new Product(productName, price, quantity);
    }

}
