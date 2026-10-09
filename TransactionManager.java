import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionManager {
    private static final String PREFIX = "ILD552"; // initials + last 3 digits
    private final List<Transaction> transactions = new ArrayList<>();
    private int counter = 0;

    public String nextId() {
        return String.format("%s-%03d", PREFIX, ++counter);
    }

    public void add(Transaction t) throws InvalidTransactionException {
        if (t == null)
            throw new InvalidTransactionException("Null transaction.");
        transactions.add(t);
    }

    public List<Transaction> search(String keyword) {
        List<Transaction> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Transaction t : transactions) {
            if (t.getId().toLowerCase().contains(k)
                    || t.getCustomerName().toLowerCase().contains(k)) {
                result.add(t);
            }
        }
        return result;
    }

    public Transaction findById(String id) throws InvalidTransactionException {
        for (Transaction t : transactions)
            if (t.getId().equalsIgnoreCase(id.trim())) return t;
        throw new InvalidTransactionException("No transaction with ID: " + id);
    }

    public void remove(String id) throws InvalidTransactionException {
        transactions.remove(findById(id));
    }

    /** Polymorphic report sorted by fee, descending (A=5). */
    public List<Transaction> sortedReport() {
        List<Transaction> copy = new ArrayList<>(transactions);
        Comparator<Transaction> byChargeDesc =
                Comparator.comparingDouble((Transaction t) -> t.calculateCharge()).reversed();
        copy.sort(byChargeDesc);
        return copy;
    }

    public double totalCharges() {
        double sum = 0;
        for (Chargeable c : transactions) sum += c.calculateCharge();
        return sum;
    }

    public double totalDiscounts() {
        double sum = 0;
        for (Transaction t : transactions) sum += t.getDiscountAmount();
        return sum;
    }

    public boolean isEmpty() { return transactions.isEmpty(); }
}
