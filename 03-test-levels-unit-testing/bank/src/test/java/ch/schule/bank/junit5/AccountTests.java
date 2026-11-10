package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.BankUtils;
import ch.schule.Booking;
import ch.schule.SalaryAccount;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Klasse Account.
 *
 * @author Nepomuk Crhonek
 * @version 1.0
 */
public class AccountTests {

    private Account account;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        account = new SavingsAccount("S-1");
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

    /**
     * Tested die Initialisierung eines Kontos.
     */
    @Test
    public void testInit() {
        assertEquals("S-1", account.getId());
        assertEquals(0, account.getBalance());
        assertTrue(account.canTransact(0));
    }

    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        assertTrue(account.deposit(1, 100000));
        assertEquals(100000, account.getBalance());

        assertTrue(account.deposit(2, 0));
        assertEquals(100000, account.getBalance());

        assertFalse(account.deposit(3, -1));
        assertEquals(100000, account.getBalance());
    }

    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        Account salaryAccount = new SalaryAccount("P-1", -50000);

        assertTrue(salaryAccount.withdraw(1, 30000));
        assertEquals(-30000, salaryAccount.getBalance());

        assertFalse(salaryAccount.withdraw(2, -1));
        assertEquals(-30000, salaryAccount.getBalance());
    }

    @Test
    public void testWithdrawNegativeAmount() {
        assertFalse(account.withdraw(1, -500));
        assertEquals(0, account.getBalance());
    }

    /**
     * Tests the reference from SavingsAccount
     */
    @Test
    public void testReferences() {
        assertInstanceOf(Account.class, account);
        assertNull(account.getBooking());

        Booking booking = new Booking(1, 500);
        account.setBooking(booking);
        assertSame(booking, account.getBooking());
    }

    /**
     * teste the canTransact Flag
     */
    @Test
    public void testCanTransact() {
        assertTrue(account.deposit(10, 1000));

        assertTrue(account.canTransact(10));
        assertTrue(account.canTransact(11));
        assertFalse(account.canTransact(9));

        assertFalse(account.deposit(9, 1000));
        assertFalse(account.withdraw(9, 500));
        assertEquals(1000, account.getBalance());
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        account.deposit(0, 100000);
        account.withdraw(30, 25000);

        account.print();

        String[] lines = printedLines();
        assertEquals(4, lines.length);
        assertEquals("Kontoauszug 'S-1'", lines[0]);
        assertEquals("Datum          Betrag      Saldo", lines[1]);
        assertEquals("01.01.1970 " + BankUtils.formatAmount(100000) + " " + BankUtils.formatAmount(100000), lines[2]);
        assertEquals("01.02.1970 " + BankUtils.formatAmount(-25000) + " " + BankUtils.formatAmount(75000), lines[3]);
    }

    /**
     * Experimente mit print(year,month).
     */
    @Test
    public void testMonthlyPrint() {
        account.deposit(18004, 100000);
        account.deposit(18039, 50000);
        account.deposit(18074, 20000);

        account.print(2020, 2);

        String[] lines = printedLines();
        assertEquals(3, lines.length);
        assertEquals("Kontoauszug 'S-1' Monat: 2.2020", lines[0]);
        assertEquals("Datum          Betrag      Saldo", lines[1]);
        assertEquals("10.02.2020 " + BankUtils.formatAmount(50000) + " " + BankUtils.formatAmount(150000), lines[2]);
    }

    @Test
    public void testMonthlyPrintWithoutBookings() {
        account.deposit(18004, 100000);

        account.print(2021, 1);

        assertEquals(2, printedLines().length);
    }
}
