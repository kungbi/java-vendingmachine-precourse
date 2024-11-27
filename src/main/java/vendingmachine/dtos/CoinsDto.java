package vendingmachine.dtos;

import java.util.Map;
import vendingmachine.Coin;

public record CoinsDto(Map<Coin, Integer> coins) {

    public boolean isEmpty() {
        if (coins.isEmpty()) {
            return true;
        }
        if (coins.values().stream().allMatch(value -> value == 0)) {
            return true;
        }
        return false;
    }
}
