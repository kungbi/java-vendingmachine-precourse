package vendingmachine;

import java.util.Arrays;
import java.util.List;

public enum Coin {
    COIN_500(500),
    COIN_100(100),
    COIN_50(50),
    COIN_10(10);

    private final int amount;

    Coin(final int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static List<Coin> getSortedCoins() {
        return Arrays.stream(Coin.values()).sorted((o1, o2) -> o2.getAmount() - o1.getAmount()).toList();
    }
}
