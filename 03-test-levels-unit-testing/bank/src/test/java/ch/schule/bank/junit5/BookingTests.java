package ch.schule.bank.junit5;

import ch.schule.BankUtils;
import ch.schule.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests für die Klasse Booking.
 *
 * @author Luigi Cavuoti
 * @version 1.1
 */
public class BookingTests
{
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private PrintStream originalOut;

	@BeforeEach
	public void setUp()
	{
		originalOut = System.out;
		System.setOut(new PrintStream(output));
	}

	@AfterEach
	public void tearDown()
	{
		System.setOut(originalOut);
	}

	/**
	 * Tests für die Erzeugung von Buchungen.
	 */
	@Test
	public void testInitialization()
	{
		Booking booking = new Booking(18039, 50000);
		assertEquals(18039, booking.getDate());
		assertEquals(50000, booking.getAmount());

		Booking withdrawal = new Booking(0, -25000);
		assertEquals(-25000, withdrawal.getAmount());
	}

	/**
	 * Experimente mit print().
	 */
	@Test
	public void testPrint()
	{
		new Booking(18039, 50000).print(100000);

		String line = output.toString().split("\\R")[0];
		assertEquals("10.02.2020 " + BankUtils.formatAmount(50000) + " " + BankUtils.formatAmount(150000), line);
	}
}
