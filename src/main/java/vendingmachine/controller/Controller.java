package vendingmachine.controller;

import java.util.List;
import vendingmachine.domain.HeldCoins;
import vendingmachine.domain.PaymentAmount;
import vendingmachine.domain.Product;
import vendingmachine.dtos.CoinsDto;
import vendingmachine.dtos.PurchaseInputDto;
import vendingmachine.repository.ProductRepository;
import vendingmachine.service.PaymentService;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

public class Controller {
    private final ProductRepository productRepository;
    private final PaymentService paymentService;
    private final RetryInputUtil retryInputUtil;

    public Controller(ProductRepository productRepository, PaymentService paymentService,
                      RetryInputUtil retryInputUtil) {
        this.productRepository = productRepository;
        this.paymentService = paymentService;
        this.retryInputUtil = retryInputUtil;
    }

    public void run() {
        int vendingMachineBalance = retryInputUtil.getVendingMachineBalance();
        HeldCoins heldCoins = HeldCoins.createRandomHeldCoins(vendingMachineBalance);

        OutputView.printHeldCoins(heldCoins.getChangeCoins(vendingMachineBalance));

        List<Product> products = retryInputUtil.getProducts();
        for (Product product : products) {
            productRepository.add(product);
        }

        int inputAmount = retryInputUtil.getInputAmount();
        PaymentAmount paymentAmount = new PaymentAmount(inputAmount);

        while (true) {
            try {
                OutputView.printInputAmount(paymentAmount.getAmount());
                if (productRepository.isStockEmpty() || paymentAmount.getAmount() < productRepository.getMinPrice()) {
                    break;
                }

                String orderProduct = InputView.getOrderProduct().strip();
                paymentService.purchase(new PurchaseInputDto(paymentAmount, heldCoins, orderProduct));
            } catch (IllegalArgumentException error) {
                OutputView.printError(error.getMessage());
            }
        }
        CoinsDto changeCoins = heldCoins.getChangeCoins(paymentAmount.getAmount());
        OutputView.printChange(changeCoins);
    }

}
