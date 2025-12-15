import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Account class
 */
class AccountTest {
    private Account account;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    @BeforeEach
    void setUp() {
        account = new Account();
    }
    
    @Test
    void testDepositIncreasesBalance() {
        LocalDate date = LocalDate.of(2012, 1, 10);
        account.deposit(1000, date);
        
        assertEquals(1000, account.getBalance());
    }
    
    @Test
    void testMultipleDeposits() {
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        
        assertEquals(3000, account.getBalance());
    }
    
    @Test
    void testWithdrawDecreasesBalance() {
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.withdraw(500, LocalDate.of(2012, 1, 14));
        
        assertEquals(500, account.getBalance());
    }
    
    @Test
    void testWithdrawWithInsufficientFunds() {
        account.deposit(100, LocalDate.of(2012, 1, 10));
        
        assertThrows(IllegalStateException.class, () -> {
            account.withdraw(200, LocalDate.of(2012, 1, 11));
        });
    }
    
    @Test
    void testDepositWithNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100, LocalDate.of(2012, 1, 10));
        });
    }
    
    @Test
    void testDepositWithZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0, LocalDate.of(2012, 1, 10));
        });
    }
    
    @Test
    void testWithdrawWithNegativeAmount() {
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-100, LocalDate.of(2012, 1, 11));
        });
    }
    
    @Test
    void testDepositWithNullDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(1000, null);
        });
    }
    
    @Test
    void testWithdrawWithNullDate() {
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(500, null);
        });
    }
    
    @Test
    void testTransactionsAreStored() {
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        account.withdraw(500, LocalDate.of(2012, 1, 14));
        
        assertEquals(3, account.getTransactions().size());
    }
    
    @Test
    void testPrintStatementFormat() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
        
        try {
            account.deposit(1000, LocalDate.of(2012, 1, 10));
            account.deposit(2000, LocalDate.of(2012, 1, 13));
            account.withdraw(500, LocalDate.of(2012, 1, 14));
            
            account.printStatement();
            
            String output = outputStream.toString();
            assertTrue(output.contains("Date       | Amount | Balance"));
            assertTrue(output.contains("14-01-2012 | -500 | 2500"));
            assertTrue(output.contains("13-01-2012 | 2000 | 3000"));
            assertTrue(output.contains("10-01-2012 | 1000 | 1000"));
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    void testPrintStatementReverseOrder() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
        
        try {
            account.deposit(1000, LocalDate.of(2012, 1, 10));
            account.deposit(2000, LocalDate.of(2012, 1, 13));
            account.withdraw(500, LocalDate.of(2012, 1, 14));
            
            account.printStatement();
            
            String output = outputStream.toString();
            String[] lines = output.split("\n");
            
            // Check that transactions are in reverse chronological order
            assertTrue(lines[1].contains("14-01-2012")); // Most recent first
            assertTrue(lines[2].contains("13-01-2012"));
            assertTrue(lines[3].contains("10-01-2012")); // Oldest last
        } finally {
            System.setOut(originalOut);
        }
    }
    
    @Test
    void testAcceptanceCriteria() {
        // Given a client makes a deposit of 1000 on 10-01-2012
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        
        // And a deposit of 2000 on 13-01-2012
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        
        // And a withdrawal of 500 on 14-01-2012
        account.withdraw(500, LocalDate.of(2012, 1, 14));
        
        // Then the balance should be 2500
        assertEquals(2500, account.getBalance());
        
        // And there should be 3 transactions
        assertEquals(3, account.getTransactions().size());
    }
    
    @Test
    void testEmptyAccountPrintStatement() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
        
        try {
            account.printStatement();
            
            String output = outputStream.toString();
            assertTrue(output.contains("Date       | Amount | Balance"));
        } finally {
            System.setOut(originalOut);
        }
    }
}