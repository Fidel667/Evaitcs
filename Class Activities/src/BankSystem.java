import java.util.*;

public class BankSystem {

    public static void main(String[] args) {


        Set<String> users = new HashSet<>();
        users.add("Ali");
        users.add("Sara");
        users.add("Ali");

        Map<String, Integer> balance = new HashMap<>();
        balance.put("Ali",  100);
        balance.put("Sara", 200);

        List<String> transactionHistory = new ArrayList<>();

        Queue<String> transactions = new LinkedList<>();
        transactions.add("Ali:50");
        transactions.add("Sara:-30");
        transactions.add("Ali:-200");
        transactions.add("Omar:100");

        System.out.println("\nRegistered Users : " + users);
        System.out.println("Starting Balances: " + balance);
        System.out.println("Transactions Queue: " + transactions);

        System.out.println("\nTransactions\n");

        while (!transactions.isEmpty()) {

            String transaction = transactions.remove();

            String[] parts  = transaction.split(":");
            String   name   = parts[0];
            int      amount = Integer.parseInt(parts[1]);

            System.out.println("Processing: " + transaction);

            if (!users.contains(name)) {
                String log = "[FAILED]  " + name + " is not a registered user.";
                transactionHistory.add(log);
                System.out.println(log);

            } else if (balance.get(name) + amount < 0) {
                String log = "[FAILED]  " + name
                        + " | Insufficient funds. Balance: $"
                        + balance.get(name)
                        + " | Attempted: $" + amount;
                transactionHistory.add(log);
                System.out.println(log);

            } else {
                int oldBalance = balance.get(name);
                int newBalance = oldBalance + amount;
                balance.put(name, newBalance);

                String action = amount >= 0 ? "Deposit  " : "Withdrawal";
                String log    = "[SUCCESS] " + name
                        + " | " + action
                        + " $" + Math.abs(amount)
                        + " | $" + oldBalance + " → $" + newBalance;
                transactionHistory.add(log);
                System.out.println(log);
            }

            System.out.println();
        }

        System.out.println("           FINAL BALANCES                   ");
        for (String user : users) {
            System.out.println("  " + user + " → $" + balance.get(user));
        }

        System.out.println("         FULL TRANSACTION HISTORY           ");
        for (int i = 0; i < transactionHistory.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + transactionHistory.get(i));
        }
        System.out.println("Total transactions processed: "
                + transactionHistory.size());
    }
}