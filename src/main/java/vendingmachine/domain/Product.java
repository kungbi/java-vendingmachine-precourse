package vendingmachine.domain;

import vendingmachine.validator.ProductValidator;

public class Product {
    private final String name;
    private final int price;
    private final int quantity;

    public Product(String name, int price, int quantity) {
        ProductValidator.validate(price, quantity);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isAvailableToBuy() {
        return quantity > 0;
    }

}
