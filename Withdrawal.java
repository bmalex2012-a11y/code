public class Withdrawal extends Transaction {
    private static final double RATE = 0.02;
    private static final double MIN_FEE = 500;
    private static final double THRESHOLD = 500_000;
    private static final double P = 0.10; // discount P = 10 %

    public Withdrawal(String id, String customerName, double amount)
            throws InvalidTransactionException {
        super(id, customerName, amount);
        setDiscountApplied(amount > THRESHOLD);
    }

    @Override
    public void setAmount(double amount) throws InvalidTransactionException {
        super.setAmount(amount);
        setDiscountApplied(amount > THRESHOLD); // recompute if the amount changes
    }

    private double baseFee() {
        return Math.max(getAmount() * RATE, MIN_FEE);
    }

    @Override
    public double getDiscountAmount() {
        return isDiscountApplied() ? baseFee() * P : 0;
    }

    @Override
    public String getType() { return "Withdrawal"; }

    @Override
    public double calculateCharge() {
        return baseFee() - getDiscountAmount();
    }
}
