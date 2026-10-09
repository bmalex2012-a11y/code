import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println(" Name: INIKWALI Lipay Dorcas");
        System.out.println(" Registration No.: VU-BBC-2511-1552-DAY");
        System.out.println(" Mobile money agent");
        System.out.println("==============================================");

        TransactionManager manager = new TransactionManager();
        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add a deposit");
            System.out.println("2. Add a withdrawal");
            System.out.println("3. Show sorted report (fees, descending)");
            System.out.println("4. Search a transaction");
            System.out.println("5. Delete a transaction");
            System.out.println("6. Summary (total fees and discounts)");
            System.out.println("0. Quit");
            int choice = InputHelper.readInt("Choice: ", 0, 6);

            try {
                switch (choice) {
                    case 1:
                    case 2:
                        addTransaction(manager, choice == 1);
                        break;
                    case 3:
                        showReport(manager);
                        break;
                    case 4:
                        search(manager);
                        break;
                    case 5:
                        delete(manager);
                        break;
                    case 6:
                        System.out.printf("Total fees charged: %,.0f%n", manager.totalCharges());
                        System.out.printf("Total discounts granted: %,.0f%n", manager.totalDiscounts());
                        break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                }
            } catch (InvalidTransactionException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addTransaction(TransactionManager m, boolean deposit)
            throws InvalidTransactionException {
        String name = InputHelper.readNonEmpty("Customer name: ");
        double amount = InputHelper.readPositiveDouble("Amount: ");
        String id = m.nextId();
        Transaction t = deposit ? new Deposit(id, name, amount)
                                : new Withdrawal(id, name, amount);
        m.add(t);
        System.out.println("Added: " + t);
    }

    private static void showReport(TransactionManager m) {
        if (m.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        for (Transaction t : m.sortedReport()) {   // polymorphism
            System.out.println(t);
        }
    }

    private static void search(TransactionManager m) {
        String k = InputHelper.readNonEmpty("ID or name: ");
        List<Transaction> found = m.search(k);
        if (found.isEmpty()) System.out.println("No results.");
        else found.forEach(System.out::println);
    }

    private static void delete(TransactionManager m) throws InvalidTransactionException {
        String id = InputHelper.readNonEmpty("ID to delete: ");
        Transaction t = m.findById(id);
        System.out.println(t);
        if (InputHelper.confirm("Confirm deletion")) {
            m.remove(id);
            System.out.println("Deleted.");
        } else {
            System.out.println("Cancelled.");
        }
    }
}
