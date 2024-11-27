package vendingmachine.config;

import vendingmachine.controller.Controller;
import vendingmachine.controller.RetryInputUtil;
import vendingmachine.repository.ProductRepository;
import vendingmachine.service.PaymentService;
import vendingmachine.validator.InputValidator;

public class DependencyInjector {
    public Controller createController() {
        ProductRepository productRepository = new ProductRepository();
        PaymentService paymentService = new PaymentService(productRepository);
        InputValidator inputValidator = new InputValidator(productRepository);
        RetryInputUtil retryInputUtil = new RetryInputUtil(inputValidator);

        return new Controller(productRepository, paymentService, retryInputUtil);
    }
}
