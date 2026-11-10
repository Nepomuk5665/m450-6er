package ch.schule.bank.junit5;

import ch.schule.PromoYouthSavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests für das Promo-Jugend-Sparkonto.
 *
 * @author Nepomuk Crhonek
 * @version 1.0
 */
public class PromoYouthSavingsAccountTests
{
	private PromoYouthSavingsAccount account;

	@BeforeEach
	public void setUp()
	{
		account = new PromoYouthSavingsAccount("Y-1");
	}

	/**
	 * Der Test.
	 */
	@Test
	public void test()
	{
		assertTrue(account.deposit(1, 100000));
		assertEquals(101000, account.getBalance());
	}

	@Test
	public void testNoBonusForSmallAmount()
	{
		assertTrue(account.deposit(1, 99));
		assertEquals(99, account.getBalance());
	}

	@Test
	public void testNegativeDeposit()
	{
		assertFalse(account.deposit(1, -100000));
		assertEquals(0, account.getBalance());
	}

	@Test
	public void testWithdrawLikeSavingsAccount()
	{
		account.deposit(1, 100000);

		assertFalse(account.withdraw(2, 101001));
		assertTrue(account.withdraw(2, 101000));
		assertEquals(0, account.getBalance());
	}
}
