package ch.tbz.m450;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    private static final double DELTA = 0.000001;

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Addition von zwei positiven Zahlen")
    void addPositive() {
        assertEquals(5, calculator.add(2, 3), DELTA);
    }

    @Test
    @DisplayName("Addition mit negativer Zahl")
    void addNegative() {
        assertEquals(-1, calculator.add(2, -3), DELTA);
    }

    @Test
    @DisplayName("Addition mit Kommazahlen")
    void addDecimals() {
        assertEquals(0.3, calculator.add(0.1, 0.2), DELTA);
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "0, 0, 0",
            "10, 0, 10",
            "-4, -6, -10",
            "1.5, 2.25, 3.75"
    })
    void addMany(double a, double b, double expected) {
        assertEquals(expected, calculator.add(a, b), DELTA);
    }

    @Test
    @DisplayName("Subtraktion mit positivem Resultat")
    void subtractPositive() {
        assertEquals(4, calculator.subtract(10, 6), DELTA);
    }

    @Test
    @DisplayName("Subtraktion mit negativem Resultat")
    void subtractNegative() {
        assertEquals(-4, calculator.subtract(6, 10), DELTA);
    }

    @Test
    @DisplayName("Subtraktion einer negativen Zahl")
    void subtractNegativeNumber() {
        assertEquals(9, calculator.subtract(4, -5), DELTA);
    }

    @Test
    @DisplayName("Multiplikation von zwei positiven Zahlen")
    void multiplyPositive() {
        assertEquals(12, calculator.multiply(3, 4), DELTA);
    }

    @Test
    @DisplayName("Multiplikation mit negativer Zahl")
    void multiplyNegative() {
        assertEquals(-12, calculator.multiply(3, -4), DELTA);
    }

    @Test
    @DisplayName("Multiplikation mit 0")
    void multiplyZero() {
        assertEquals(0, calculator.multiply(123.45, 0), DELTA);
    }

    @Test
    @DisplayName("Division ohne Rest")
    void divide() {
        assertEquals(4, calculator.divide(12, 3), DELTA);
    }

    @Test
    @DisplayName("Division mit Kommazahl als Resultat")
    void divideDecimal() {
        assertEquals(2.5, calculator.divide(5, 2), DELTA);
    }

    @Test
    @DisplayName("Division mit negativer Zahl")
    void divideNegative() {
        assertEquals(-3, calculator.divide(9, -3), DELTA);
    }

    @Test
    @DisplayName("Division von 0")
    void divideZeroByNumber() {
        assertEquals(0, calculator.divide(0, 5), DELTA);
    }

    @Test
    @DisplayName("Division durch 0 wirft Exception")
    void divideByZero() {
        ArithmeticException e = assertThrows(ArithmeticException.class, () -> calculator.divide(5, 0));
        assertEquals("Division durch 0 ist nicht erlaubt", e.getMessage());
    }
}
