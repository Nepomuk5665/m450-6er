package ch.schule.bank.junit5;

import ch.schule.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Klasse 'Bank'.
 *
 * @author Nepomuk Crhonek
 * @version 1.0
 */
public class BankTests {

    private Bank bank;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        originalOut = System.out;
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private String[] printedLines() {
        return output.toString().split("\\R");
    }

    private void createSixAccounts() {
        long[] amounts = {100, 600, 300, 500, 200, 400};
        for (long amount : amounts) {
            bank.deposit(bank.createSavingsAccount(), 1, amount);
        }
    }

    /**
     * Tests to create new Accounts
     */
    @Test
    public void testCreate() {
        assertEquals("S-1000", bank.createSavingsAccount());
        assertEquals("Y-1001", bank.createPromoYouthSavingsAccount());
        assertEquals("P-1002", bank.createSalaryAccount(-100000));
        assertEquals("P-1003", bank.createSalaryAccount(0));
        assertNull(bank.createSalaryAccount(1));
        assertEquals("S-1004", bank.createSavingsAccount());
    }

    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        String id = bank.createSavingsAccount();

        assertTrue(bank.deposit(id, 1, 100000));
        assertEquals(100000, bank.getBalance(id));

        assertFalse(bank.deposit(id, 2, -100));
        assertEquals(100000, bank.getBalance(id));
    }

    @Test
    public void testUnknownAccount() {
        assertFalse(bank.deposit("S-9999", 1, 100000));
        assertFalse(bank.withdraw("S-9999", 1, 100000));
        assertEquals(0, bank.getBalance("S-9999"));
    }

    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 1, 100000);

        assertTrue(bank.withdraw(id, 2, 40000));
        assertEquals(60000, bank.getBalance(id));

        assertFalse(bank.withdraw(id, 3, 60001));
        assertEquals(60000, bank.getBalance(id));
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 0, 100000);

        bank.print(id);
        bank.print("S-9999");

        String[] lines = printedLines();
        assertEquals(3, lines.length);
        assertEquals("Kontoauszug 'S-1000'", lines[0]);
        assertTrue(lines[2].startsWith("01.01.1970"));
    }

    /**
     * Experimente mit print(year, month).
     */
    @Test
    public void testMonthlyPrint() {
        String id = bank.createSavingsAccount();
        bank.deposit(id, 18039, 50000);

        bank.print(id, 2020, 2);
        bank.print("S-9999", 2020, 2);

        String[] lines = printedLines();
        assertEquals(3, lines.length);
        assertEquals("Kontoauszug 'S-1000' Monat: 2.2020", lines[0]);
        assertTrue(lines[2].startsWith("10.02.2020"));
    }

    /**
     * Testet den Gesamtkontostand der Bank.
     */
    @Test
    public void testBalance() {
        assertEquals(0, bank.getBalance());

        String savings = bank.createSavingsAccount();
        String salary = bank.createSalaryAccount(-100000);
        bank.deposit(savings, 1, 300000);
        bank.withdraw(salary, 1, 50000);

        assertEquals(-250000, bank.getBalance());
    }

    /**
     * Tested die Ausgabe der "top 5" konten.
     */
    @Test
    public void testTop5() {
        createSixAccounts();

        bank.printTop5();

        assertArrayEquals(new String[]{
                "S-1001: 600",
                "S-1003: 500",
                "S-1005: 400",
                "S-1002: 300",
                "S-1004: 200"
        }, printedLines());
    }

    /**
     * Tested die Ausgabe der "bottom 5" konten.
     */
    @Test
    public void testBottom5() {
        createSixAccounts();

        bank.printBottom5();

        assertArrayEquals(new String[]{
                "S-1000: 100",
                "S-1004: 200",
                "S-1002: 300",
                "S-1005: 400",
                "S-1003: 500"
        }, printedLines());
    }

    @Test
    public void testTop5AndBottom5WithEqualBalances() {
        bank.createSavingsAccount();
        bank.createSavingsAccount();

        bank.printTop5();
        bank.printBottom5();

        assertArrayEquals(new String[]{
                "S-1000: 0",
                "S-1001: 0",
                "S-1000: 0",
                "S-1001: 0"
        }, printedLines());
    }
}
