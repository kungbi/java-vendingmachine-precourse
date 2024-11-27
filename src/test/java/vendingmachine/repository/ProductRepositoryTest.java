package vendingmachine.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vendingmachine.domain.Product;

class ProductRepositoryTest {
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productRepository.add(new Product("사이다", 1000, 0));
        productRepository.add(new Product("콜라", 2000, 1));
        productRepository.add(new Product("바나나", 3000, 1));
    }

    @Test
    void 상품_최저_금액_테스트() {
        int minPrice = productRepository.getMinPrice();
        Assertions.assertThat(minPrice).isEqualTo(2000);
    }

}