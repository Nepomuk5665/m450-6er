package ch.schule.bank.junit5;

import ch.schule.BankUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.DecimalFormatSymbols;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests für die Klasse BankUtils.
 *
 * @author Nepomuk Crhonek
 * @version 1.0
 */
public class BankUtilsTests
{
	@ParameterizedTest(name = "Banktag {0} = {1}")
	@CsvSource({
			"0, 01.01.1970",
			"29, 30.01.1970",
			"30, 01.02.1970",
			"359, 30.12.1970",
			"360, 01.01.1971",
			"18039, 10.02.2020"
	})
	public void testFormatBankDate(int date, String expected)
	{
		assertEquals(expected, BankUtils.formatBankDate(date));
	}

	@Test
	public void testFormatAmount()
	{
		DecimalFormatSymbols symbols = BankUtils.AMOUNT_FORMAT.getDecimalFormatSymbols();
		char d = symbols.getDecimalSeparator();
		char g = symbols.getGroupingSeparator();

		assertEquals("      1" + d + "00", BankUtils.formatAmount(100000));
		assertEquals("     -0" + d + "25", BankUtils.formatAmount(-25000));
		assertEquals("  1" + g + "234" + d + "57", BankUtils.formatAmount(123456789));
	}
}
