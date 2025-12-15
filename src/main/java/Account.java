import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Account class representing a bank account with deposit, withdraw and statement printing capabilities.
 */
public class Account {
    private int balance;
    private final List<Transaction> transactions;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }
    
    /**
     * Deposits money into the account
     * @param amount The amount to deposit
     * @param date The date of the transaction
     * @throws IllegalArgumentException if amount is negative or zero
     */
    public void deposit(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        balance += amount;
        transactions.add(new Transaction(date, amount, balance, TransactionType.DEPOSIT));
    }
    
    /**
     * Withdraws money from the account
     * @param amount The amount to withdraw
     * @param date The date of the transaction
     * @throws IllegalArgumentException if amount is negative or zero
     * @throws IllegalStateException if insufficient funds
     */
    public void withdraw(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        
        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance, TransactionType.WITHDRAWAL));
    }
    
    /**
     * Prints the bank statement showing all transactions in reverse chronological order
     */
    public void printStatement() {
        System.out.println("Date       | Amount | Balance");
        
        // Print transactions in reverse order (newest first)
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            String formattedDate = transaction.getDate().format(DATE_FORMATTER);
            System.out.println(String.format("%s | %d | %d", 
                formattedDate, 
                transaction.getAmount(), 
                transaction.getBalance()));
        }
    }
    
    /**
     * Gets the current balance
     * @return current balance
     */
    public int getBalance() {
        return balance;
    }
    
    /**
     * Gets all transactions (for testing purposes)
     * @return list of transactions
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
    
    /**
     * Inner class representing a transaction
     */
    static class Transaction {
        private final LocalDate date;
        private final int amount;
        private final int balance;
        private final TransactionType type;
        
        public Transaction(LocalDate date, int amount, int balance, TransactionType type) {
            this.date = date;
            this.amount = amount;
            this.balance = balance;
            this.type = type;
        }
        
        public LocalDate getDate() {
            return date;
        }
        
        public int getAmount() {
            return amount;
        }
        
        public int getBalance() {
            return balance;
        }
        
        public TransactionType getType() {
            return type;
        }
    }
    
    /**
     * Enum for transaction types
     */
    enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }
}