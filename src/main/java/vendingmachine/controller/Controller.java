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
        HeldCoins heldCoins = initializeVendingMachine();
        initializeProducts();

        PaymentAmount paymentAmount = getPaymentAmountFromUser();
        processPurchases(paymentAmount, heldCoins);

        CoinsDto changeCoins = heldCoins.getChangeCoins(paymentAmount.getAmount());
        OutputView.printChange(changeCoins);
    }

    private HeldCoins initializeVendingMachine() {
        int vendingMachineBalance = retryInputUtil.getVendingMachineBalance();
        HeldCoins heldCoins = HeldCoins.createRandomHeldCoins(vendingMachineBalance);
        OutputView.printHeldCoins(heldCoins.getCoinsDto());
        return heldCoins;
    }

    private void initializeProducts() {
        List<Product> products = retryInputUtil.getProducts();
        this.productRepository.addAll(products);
    }

    private PaymentAmount getPaymentAmountFromUser() {
        int inputAmount = retryInputUtil.getInputAmount();
        return new PaymentAmount(inputAmount);
    }

    private void processPurchases(PaymentAmount paymentAmount, HeldCoins heldCoins) {
        while (true) {
            OutputView.printInputAmount(paymentAmount.getAmount());
            if (!canPurchase(paymentAmount)) {
                break;
            }
            try {
                handlePurchase(paymentAmount, heldCoins);
            } catch (IllegalArgumentException error) {
                OutputView.printError(error.getMessage());
            }
        }
    }

    private boolean canPurchase(PaymentAmount paymentAmount) {
        return !productRepository.isStockEmpty() && paymentAmount.getAmount() >= productRepository.getMinPrice();
    }

    private void handlePurchase(PaymentAmount paymentAmount, HeldCoins heldCoins) {
        String orderProduct = InputView.getOrderProduct().strip();
        paymentService.purchase(new PurchaseInputDto(paymentAmount, heldCoins, orderProduct));
    }
}
