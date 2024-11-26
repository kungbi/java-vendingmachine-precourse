package vendingmachine.service;

import java.util.Optional;
import vendingmachine.domain.HeldCoins;
import vendingmachine.domain.PaymentAmount;
import vendingmachine.domain.Product;
import vendingmachine.dtos.PurchaseInputDto;
import vendingmachine.repository.ProductRepository;

public class PaymentService {
    private final ProductRepository productRepository;

    public PaymentService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void purchase(PurchaseInputDto input) {
        PaymentAmount paymentAmount = input.paymentAmount();
        String productName = input.productName();
        HeldCoins heldCoins = input.coins();

        // 해당 상품이 있는지 확인
        Optional<Product> foundProduct = this.productRepository.findByName(productName);
        if (foundProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }
        Product product = foundProduct.get();

        // 상품의 재고가 있는지 확인
        if (!product.isAvailableToBuy()) {
            throw new IllegalArgumentException("Product is not available to buy");
        }

        // 가지고 있는 돈으로 해당 상품을 구매할 수 있는지 확인
        if (paymentAmount.getAmount() < product.getPrice()) {
            throw new IllegalArgumentException("Payment amount is too small");
        }

        // 투입금액 감소
        paymentAmount.decreaseAmount(product.getPrice());

        this.productRepository.update(product.getName(),
                new Product(product.getName(), product.getPrice(), product.getQuantity() - 1));
    }
}
