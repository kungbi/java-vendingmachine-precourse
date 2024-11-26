package vendingmachine.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import vendingmachine.Coin;
import vendingmachine.dtos.CoinsDto;

class OutputViewTest {

    @Test
    void 전체_출력_테스트() {
        OutputView.printHeldCoins(
                new CoinsDto(Map.of(Coin.COIN_500, 2, Coin.COIN_100, 1, Coin.COIN_50, 5))
        );

        OutputView.printInputAmount(1000);

        OutputView.printChange(
                new CoinsDto(Map.of(Coin.COIN_100, 1, Coin.COIN_50, 3))
        );
    }

    @Test
    void 잔돈_없음() {
        OutputView.printHeldCoins(
                new CoinsDto(Map.of(Coin.COIN_500, 2, Coin.COIN_100, 1, Coin.COIN_50, 5))
        );

        OutputView.printInputAmount(0);

        OutputView.printChange(
                new CoinsDto(Map.of())
        );
    }

}