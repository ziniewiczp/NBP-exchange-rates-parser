package pl.parser.nbp;

import java.time.LocalDate;

public class Validator {
    public enum Currency {
        EUR, USD, CHF, GBP;
    }

    private static Boolean validateCurrency(String providedCurrency) {
        for (Currency currency : Currency.values()) {
            if (providedCurrency.equals(currency.name())) {
                return true;
            }
        }

        System.out.println("Provided currency is invalid. Available currencies are: EUR, USD, CHF and GBP.");
        return false;
    }

    private static Boolean validateDate(String stringDate) {
        if (!stringDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Invalid date format. Required format is: YYYY-MM-DD.");
            return false;
        }

        LocalDate firstDate = LocalDate.parse("2002-01-01");
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse(stringDate);

        if (date.isBefore(firstDate)) {
            System.out.println("You can't use date before 2002-01-01.");
            return false;
        }

        if (date.isAfter(now)) {
            System.out.println("You can't use future date.");
            return false;
        }

        return true;
    }

    public Boolean validateInput(String providedCurrency, String startDate, String endDate) {
        if (validateCurrency(providedCurrency) && validateDate(startDate) && validateDate(endDate)) {
            return true;
        } else
            return false;
    }
}
