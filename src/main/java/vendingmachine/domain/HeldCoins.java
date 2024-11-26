package vendingmachine.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vendingmachine.Coin;
import vendingmachine.dtos.CoinsDto;

public class HeldCoins {
    private final Map<Coin, Integer> coins;

    public HeldCoins(Map<Coin, Integer> coins) {
        if (coins == null || coins.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.coins = new HashMap<>(coins);
    }

    public static HeldCoins createRandomHeldCoins(int amount) {
        Map<Coin, Integer> coins = new HashMap<>();
        int remainAmount = amount;
        for (Coin coin : Coin.getSortedCoins()) {
            if (coin == Coin.COIN_10) {
                break;
            }
            int maxNumberOfCoins = remainAmount / coin.getAmount();
            int numberOfCoins = Randoms.pickNumberInRange(0, maxNumberOfCoins);
            coins.put(coin, numberOfCoins);
            remainAmount -= numberOfCoins * coin.getAmount();
        }
        ;
        coins.put(Coin.COIN_10, remainAmount / Coin.COIN_10.getAmount());
        return new HeldCoins(coins);
    }

    public int getTotalAmount() {
        int total = 0;
        for (Map.Entry<Coin, Integer> entry : coins.entrySet()) {
            int amount = entry.getKey().getAmount();
            int numberOfCoins = entry.getValue();
            total += amount * numberOfCoins;
        }
        return total;
    }

    public int getNumberOfCoins(Coin coin) {
        return coins.getOrDefault(coin, 0);
    }

    private void decreaseCount(Coin coin, int count) {
        int numberOfCoins = this.coins.getOrDefault(coin, 0);
        if (numberOfCoins < count) {
            throw new IllegalArgumentException();
        }
        this.coins.put(coin, numberOfCoins - count);
    }

    public CoinsDto getChangeCoins(int change) {
        List<Coin> sortedCoins = Coin.getSortedCoins();
        Map<Coin, Integer> coins = new HashMap<>();
        int remainChange = change;
        for (Coin coin : sortedCoins) {
            int numberOfCoins = this.getNumberOfCoins(coin);
            int returnCount = Math.min(remainChange / coin.getAmount(), numberOfCoins);
            this.decreaseCount(coin, returnCount);
            remainChange -= coin.getAmount() * returnCount;
            coins.put(coin, returnCount);
            if (remainChange == 0) {
                break;
            }
        }
        return new CoinsDto(coins);
    }

    @Override
    public String toString() {
        return "HeldCoins{" +
               "coins=" + coins +
               '}';
    }
}
