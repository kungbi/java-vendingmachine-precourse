package vendingmachine.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.Arrays;
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
        while (0 < remainAmount) {
            int randomAmount = Randoms.pickNumberInList(Arrays.stream(Coin.values()).map(Coin::getAmount).toList());
            if (remainAmount < randomAmount) {
                continue;
            }
            Coin coin = Coin.findByAmount(randomAmount);

            coins.putIfAbsent(coin, 0);
            coins.put(coin, coins.get(coin) + 1);
            remainAmount -= coin.getAmount();
        }

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

    public CoinsDto getChangeCoins(int change) {
        List<Coin> sortedCoins = Coin.getSortedCoins();
        Map<Coin, Integer> coins = new HashMap<>();
        int remainChange = change;
        for (Coin coin : sortedCoins) {
            int numberOfCoins = this.getNumberOfCoins(coin);
            int returnCount = Math.min(remainChange / coin.getAmount(), numberOfCoins);
            if (remainChange == 0) {
                break;
            }

            remainChange -= coin.getAmount() * returnCount;
            coins.put(coin, returnCount);
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
