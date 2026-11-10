package ch.schule.bank.junit5;

import ch.schule.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für die Klasse SavingsAccount.
 *
 * @author Nepomuk Crhonek
 * @version 1.0
 */
public class SavingsAccountTests
{
	private SavingsAccount account;

	@BeforeEach
	public void setUp()
	{
		account = new SavingsAccount("S-1");
	}

	@Test
	public void test()
	{
		account.deposit(1, 100000);

		assertTrue(account.withdraw(2, 100000));
		assertEquals(0, account.getBalance());
	}

	@Test
	public void testNoOverdraft()
	{
		assertFalse(account.withdraw(1, 1));
		assertEquals(0, account.getBalance());
	}
}
