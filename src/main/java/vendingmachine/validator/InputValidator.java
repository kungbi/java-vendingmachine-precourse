package vendingmachine.validator;

import java.util.List;
import vendingmachine.domain.Product;
import vendingmachine.repository.ProductRepository;

public class InputValidator {
    private final ProductRepository productRepository;

    public InputValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException();
        }
    }

    public void validateProducts(List<Product> products) {
        if (products.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (products.stream().map(Product::getName).distinct().count() != products.size()) {
            throw new IllegalArgumentException();
        }
    }

    public void validateInputAmount(int inputAmount) {
        if (inputAmount < 1_000) {
            throw new IllegalArgumentException();
        }

    }
}
