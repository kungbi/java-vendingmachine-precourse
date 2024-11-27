package vendingmachine.controller;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import vendingmachine.domain.Product;
import vendingmachine.validator.InputValidator;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class RetryInputUtil {
    private final InputValidator inputValidator;

    public RetryInputUtil(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public int getVendingMachineBalance() {
        return retryLogics(InputView::getVendingMachineBalance, InputParser::parseInt, inputValidator::validateBalance);
    }

    public List<Product> getProducts() {
        return retryLogics(InputView::getProducts, InputParser::parseProducts, inputValidator::validateProducts);
    }

    public int getInputAmount() {
        return retryLogics(InputView::getInputAmount, InputParser::parseInt, inputValidator::validateInputAmount);
    }

    private <T> T retryLogics(Supplier<String> userInputReader, Function<String, T> parser,
                              Consumer<T> validator) {
        while (true) {
            try {
                String userInput = userInputReader.get();
                T parsedInput = parser.apply(userInput);
                validator.accept(parsedInput);
                return parsedInput;
            } catch (IllegalArgumentException error) {
                OutputView.printError(error.getMessage());
            }
        }
    }

    private String retryLogics(Supplier<String> userInputReader, Consumer<String> validator) {
        while (true) {
            try {
                String userInput = userInputReader.get();
                validator.accept(userInput);
                return userInput;
            } catch (IllegalArgumentException error) {
                OutputView.printError(error.getMessage());
            }

        }
    }
}
