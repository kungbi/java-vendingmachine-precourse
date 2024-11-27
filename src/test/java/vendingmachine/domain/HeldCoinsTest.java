package vendingmachine.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import vendingmachine.Coin;
import vendingmachine.dtos.CoinsDto;

class HeldCoinsTest {
    HeldCoins heldCoins;

    static Stream<Arguments> 거스름_잔돈_구하기_테스트_케이스() {
        return Stream.of(
                Arguments.of(
                        1_000,
                        Map.of(Coin.COIN_500, 2)
                ),
                Arguments.of(
                        1_500,
                        Map.of(Coin.COIN_500, 3)
                ),
                Arguments.of(
                        1_400,
                        Map.of(Coin.COIN_500, 2, Coin.COIN_100, 3, Coin.COIN_50, 2)
                ),
                Arguments.of(
                        3_500,
                        Map.of(Coin.COIN_500, 5, Coin.COIN_100, 3, Coin.COIN_50, 10, Coin.COIN_10, 20)
                ),
                Arguments.of(
                        3_600,
                        Map.of(Coin.COIN_500, 5, Coin.COIN_100, 3, Coin.COIN_50, 10, Coin.COIN_10, 20)
                )
        );
    }

    @BeforeEach
    void setUp() {
        /**
         * 500 x 5 = 2,500
         * 100 x 3 = 300
         * 50 x 10 = 500
         * 10 x 20 = 200
         */
        Map<Coin, Integer> coins = Map.of(Coin.COIN_500, 5, Coin.COIN_100, 3, Coin.COIN_50, 10, Coin.COIN_10, 20);
        heldCoins = new HeldCoins(new HashMap<>(coins));
    }

    @Test
    void 가지고_있는_금액_확인() {
        int totalAmount = heldCoins.getTotalAmount();
        Assertions.assertThat(totalAmount).isEqualTo(3_500);
    }

    @Test
    void 동전_개수_확인() {
        int numberOfCoins = heldCoins.getNumberOfCoins(Coin.COIN_500);
        Assertions.assertThat(numberOfCoins).isEqualTo(5);
    }

    @ParameterizedTest
    @MethodSource("거스름_잔돈_구하기_테스트_케이스")
    void 거스름_잔돈_구하기(int inputAmount, Map<Coin, Integer> expected) {
        CoinsDto changeCoins = heldCoins.getChangeCoins(inputAmount);
        Assertions.assertThat(changeCoins.coins()).isEqualTo(expected);
    }


    @Test
    void 랜덤_잔돈_생성_테스트() {
        HeldCoins randomHeldCoins = HeldCoins.createRandomHeldCoins(1000);
        int totalAmount = randomHeldCoins.getTotalAmount();
        System.out.println(randomHeldCoins);
        Assertions.assertThat(totalAmount).isEqualTo(1000);
    }
}