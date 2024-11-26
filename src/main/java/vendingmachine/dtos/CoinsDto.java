package vendingmachine.dtos;

import java.util.Map;
import vendingmachine.Coin;

public record CoinsDto(Map<Coin, Integer> coins) {
}
