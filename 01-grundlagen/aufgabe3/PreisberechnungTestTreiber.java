public class PreisberechnungTestTreiber {

    public static void main(String[] args) {
        if (test_calculate_price()) {
            System.out.println("Alle Tests OK");
        } else {
            System.out.println("Es sind Tests fehlgeschlagen");
        }
    }

    static boolean test_calculate_price() {
        double price;
        boolean test_ok = true;

        price = Preisberechnung.calculatePrice(20000, 3000, 0, 0, 0);
        test_ok &= check("T1", 23000, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 2000, 2, 0);
        test_ok &= check("T2", 25000, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 3000, 3, 0);
        test_ok &= check("T3", 25700, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 4000, 4, 0);
        test_ok &= check("T4", 26600, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 5000, 5, 0);
        test_ok &= check("T5", 27250, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 6000, 6, 0);
        test_ok &= check("T6", 28100, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 0, 0, 10);
        test_ok &= check("T7", 21000, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 3000, 3, 5);
        test_ok &= check("T8", 24700, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 2000, 2, 20);
        test_ok &= check("T9", 21000, price);

        price = Preisberechnung.calculatePrice(20000, 3000, 5000, 5, 20);
        test_ok &= check("T10", 23250, price);

        return test_ok;
    }

    static boolean check(String test, double expected, double actual) {
        if (Math.abs(expected - actual) < 0.001) {
            System.out.println(test + " OK");
            return true;
        }
        System.out.println(test + " FEHLER: erwartet " + expected + ", war aber " + actual);
        return false;
    }
}
