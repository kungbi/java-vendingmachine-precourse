package vendingmachine.view;

import java.util.Arrays;
import java.util.List;
import vendingmachine.Coin;
import vendingmachine.dtos.CoinsDto;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();

    public static void printHeldCoins(CoinsDto coins) {
        System.out.println("자판기가 보유한 동전");
        printAllCoins(coins);
        newLine();
    }

    public static void printChange(CoinsDto coins) {
        if (coins.isEmpty()) {
            System.out.println("잔돈없음");
            return;
        }
        System.out.println("잔돈");
        printAllCoins(coins);
    }

    public static void printInputAmount(int amount) {
        System.out.printf("투입 금액: %d원", amount);
        newLine();
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
        newLine();
    }

    private static void newLine() {
        System.out.print(NEW_LINE);
    }

    private static void printCoinsExceptNone(CoinsDto coins) {
        List<Coin> sortedCoins = Arrays.stream(Coin.values()).sorted((o1, o2) -> o2.getAmount() - o1.getAmount())
                .toList();

        for (Coin coin : sortedCoins) {
            if (!coins.coins().containsKey(coin) || coins.coins().get(coin) == 0) {
                continue;
            }
            int amount = coin.getAmount();
            int numberOfCoin = coins.coins().get(coin);
            System.out.printf("%d원 - %d개", amount, numberOfCoin);
            newLine();
        }
    }

    private static void printAllCoins(CoinsDto coins) {
        List<Coin> sortedCoins = Arrays.stream(Coin.values()).sorted((o1, o2) -> o2.getAmount() - o1.getAmount())
                .toList();

        for (Coin coin : sortedCoins) {
            int amount = coin.getAmount();
            int numberOfCoin = coins.coins().getOrDefault(coin, 0);
            System.out.printf("%d원 - %d개", amount, numberOfCoin);
            newLine();
        }
    }
}
