package vendingmachine.service;

import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import vendingmachine.Coin;
import vendingmachine.domain.HeldCoins;
import vendingmachine.domain.PaymentAmount;
import vendingmachine.domain.Product;
import vendingmachine.dtos.PurchaseInputDto;
import vendingmachine.repository.ProductRepository;

class PaymentServiceTest {
    ProductRepository productRepository;

    static Stream<Arguments> 구매_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        4000,
                        "바나나",
                        1000,
                        0
                ),
                Arguments.of(
                        4000,
                        "콜라",
                        2000,
                        1
                )
        );
    }

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productRepository.add(new Product("사이다", 1000, 0));
        productRepository.add(new Product("콜라", 2000, 2));
        productRepository.add(new Product("바나나", 3000, 1));
    }

    @ParameterizedTest
    @MethodSource("구매_테스트_케이스")
    void 구매_테스트(int inputAmount, String productName, int expectedInputAmount, int expectedQuantity) {
        // given
        PaymentService paymentService = new PaymentService(productRepository);
        PaymentAmount paymentAmount = new PaymentAmount(inputAmount);
        HeldCoins heldCoins = new HeldCoins(
                Map.of(Coin.COIN_500, 2, Coin.COIN_100, 5, Coin.COIN_50, 10, Coin.COIN_10, 20));

        // when
        PurchaseInputDto purchaseInputDto = new PurchaseInputDto(paymentAmount, heldCoins, productName);
        paymentService.purchase(purchaseInputDto);

        // then
        Assertions.assertThat(paymentAmount.getAmount()).isEqualTo(expectedInputAmount);
        Assertions.assertThat(productRepository.findByName(productName).get().getQuantity())
                .isEqualTo(expectedQuantity);
    }


}