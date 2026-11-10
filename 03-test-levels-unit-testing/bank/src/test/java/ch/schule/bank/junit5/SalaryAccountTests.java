package ch.schule.bank.junit5;

import ch.schule.SalaryAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests der Klasse SalaryAccount.
 *
 * @author Nepomuk Crhonek
 * @version 1.1
 */
public class SalaryAccountTests
{
	private SalaryAccount account;

	@BeforeEach
	public void setUp()
	{
		account = new SalaryAccount("P-1", -100000);
	}

	/**
	 * Der Test.
	 */
	@Test
	public void test()
	{
		assertTrue(account.withdraw(1, 100000));
		assertEquals(-100000, account.getBalance());
	}

	@Test
	public void testWithdrawOverCreditLimit()
	{
		assertFalse(account.withdraw(1, 100001));
		assertEquals(0, account.getBalance());
	}

	@Test
	public void testDepositAfterOverdraft()
	{
		account.withdraw(1, 100000);

		assertTrue(account.deposit(2, 50000));
		assertEquals(-50000, account.getBalance());

		assertTrue(account.withdraw(3, 50000));
		assertFalse(account.withdraw(4, 1));
		assertEquals(-100000, account.getBalance());
	}
}
