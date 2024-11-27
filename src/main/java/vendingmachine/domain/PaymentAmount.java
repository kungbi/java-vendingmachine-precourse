package vendingmachine.domain;

public class PaymentAmount {
    private int amount;

    public PaymentAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void decreaseAmount(int amount) {
        if (this.amount < amount) {
            throw new IllegalArgumentException();
        }
        this.amount -= amount;
    }
}
