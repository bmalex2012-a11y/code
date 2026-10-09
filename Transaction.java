public abstract class Transaction implements Chargeable {
    private String id;
    private String customerName;
    private double amount;
    private boolean discountApplied;

    public Transaction(String id, String customerName, double amount)
            throws InvalidTransactionException {
        setId(id);
        setCustomerName(customerName);
        setAmount(amount);
        this.discountApplied = false;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }
    public boolean isDiscountApplied() { return discountApplied; }

    public void setId(String id) throws InvalidTransactionException {
        if (id == null || id.trim().isEmpty())
            throw new InvalidTransactionException("The ID cannot be empty.");
        this.id = id.trim();
    }

    public void setCustomerName(String customerName) throws InvalidTransactionException {
        if (customerName == null || customerName.trim().isEmpty())
            throw new InvalidTransactionException("The customer name cannot be empty.");
        this.customerName = customerName.trim();
    }

    public void setAmount(double amount) throws InvalidTransactionException {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0)
            throw new InvalidTransactionException("The amount must be strictly positive.");
        this.amount = amount;
    }

    public void setDiscountApplied(boolean discountApplied) {
        this.discountApplied = discountApplied;
    }

    /** Discount amount granted (0 by default). */
    public double getDiscountAmount() { return 0; }

    public abstract String getType();

    @Override
    public abstract double calculateCharge();

    @Override
    public String toString() {
        return String.format("%-12s | %-10s | %-15s | Amount: %,.0f | Fee: %,.0f | Discount: %s",
                id, getType(), customerName, amount, calculateCharge(),
                discountApplied ? "YES (10%)" : "no");
    }
}
