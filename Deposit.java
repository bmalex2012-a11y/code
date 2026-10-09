public class Deposit extends Transaction {

    public Deposit(String id, String customerName, double amount)
            throws InvalidTransactionException {
        super(id, customerName, amount);
    }

    @Override
    public String getType() { return "Deposit"; }

    @Override
    public double calculateCharge() {
        return 0; // deposit has no fee, no discount applicable
    }
}
