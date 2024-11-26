package vendingmachine.dtos;

import vendingmachine.domain.HeldCoins;
import vendingmachine.domain.PaymentAmount;

public record PurchaseInputDto(PaymentAmount paymentAmount, HeldCoins coins, String productName) {

}