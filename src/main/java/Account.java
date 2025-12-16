import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Account class that implements the AccountService interface.
 * Handles deposits, withdrawals, and statement printing for a bank account.
 */
public class Account implements AccountService {
    private int balance;
    private final List<Transaction> transactions;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Creates a new account with zero balance
     */
    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }
    
    /**
     * Deposits money into the account
     * @param amount The amount to deposit (must be positive)
     * @param date The date of the transaction (cannot be null)
     * @throws IllegalArgumentException if amount is not positive or date is null
     */
    @Override
    public void deposit(int amount, LocalDate date) {
        validateAmount(amount);
        validateDate(date);
        
        balance += amount;
        transactions.add(new Transaction(date, amount, balance, TransactionType.DEPOSIT));
    }
    
    /**
     * Withdraws money from the account
     * @param amount The amount to withdraw (must be positive)
     * @param date The date of the transaction (cannot be null)
     * @throws IllegalArgumentException if amount is not positive or date is null
     * @throws IllegalStateException if insufficient funds
     */
    @Override
    public void withdraw(int amount, LocalDate date) {
        validateAmount(amount);
        validateDate(date);
        
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds");
        }
        
        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance, TransactionType.WITHDRAWAL));
    }
    
    /**
     * Prints the bank statement showing all transactions in reverse chronological order
     * Format: Date | Amount | Balance
     */
    @Override
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
     * Validates that the amount is positive
     * @param amount the amount to validate
     * @throws IllegalArgumentException if amount is not positive
     */
    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
    
    /**
     * Validates that the date is not null
     * @param date the date to validate
     * @throws IllegalArgumentException if date is null
     */
    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
    }
}