import java.time.LocalDate;

public class Transaction {
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
    
    public LocalDate getDate() { return date; }
    public int getAmount() { return amount; }
    public int getBalance() { return balance; }
    public TransactionType getType() { return type; }
}