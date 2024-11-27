package vendingmachine.validator;

public class ProductValidator {
    public static void validate(int price, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (price < 1_000) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (price % 10 != 0) {
            throw new IllegalArgumentException("Price must be a multiple of 10");
        }
    }
}
