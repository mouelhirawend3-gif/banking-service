import java.time.LocalDate;

/**
 * Main class to demonstrate the Account functionality
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Banking Service Demo ===\n");
        
        // Create a new account
        Account account = new Account();
        
        try {
            // Perform the acceptance test scenario
            System.out.println("Performing transactions...");
            account.deposit(1000, LocalDate.of(2012, 1, 10));
            System.out.println("Deposited 1000 on 10-01-2012");
            
            account.deposit(2000, LocalDate.of(2012, 1, 13));
            System.out.println("Deposited 2000 on 13-01-2012");
            
            account.withdraw(500, LocalDate.of(2012, 1, 14));
            System.out.println("Withdrew 500 on 14-01-2012");
            
            System.out.println("\nCurrent balance: " + account.getBalance());
            
            System.out.println("\n=== Bank Statement ===");
            account.printStatement();
            
            // Demonstrate error handling
            System.out.println("\n=== Error Handling Demo ===");
            demonstrateErrorHandling();
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateErrorHandling() {
        Account testAccount = new Account();
        
        // Test 1: Negative deposit
        System.out.println("\n1. Testing negative deposit:");
        try {
            testAccount.deposit(-100, LocalDate.now());
            System.out.println("Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }
        
        // Test 2: Insufficient funds
        System.out.println("\n2. Testing withdrawal with insufficient funds:");
        try {
            testAccount.deposit(100, LocalDate.now());
            testAccount.withdraw(200, LocalDate.now());
            System.out.println("Should have thrown exception!");
        } catch (IllegalStateException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }
        
        // Test 3: Null date
        System.out.println("\n3. Testing null date:");
        try {
            testAccount.deposit(100, null);
            System.out.println("Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }
        
        // Test 4: Zero amount
        System.out.println("\n4. Testing zero amount deposit:");
        try {
            testAccount.deposit(0, LocalDate.now());
            System.out.println("Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }
    }
}