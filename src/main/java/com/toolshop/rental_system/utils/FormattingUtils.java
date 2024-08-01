package com.toolshop.rental_system.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormattingUtils {

    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/uu");

    /**
     * Formats value to US representation of currency ($00.00)
     * 
     * @param value decimal to format
     * @return string version of value formatted as US currency
     */
    public static String formatCurrency(BigDecimal value) {
        return CURRENCY_FORMAT.format(value);
    }

    /**
     * Adds a percent sign to input
     * 
     * @param value number to format
     * @return string with percent at the end
     */
    public static String formatPercent(long value) {
        return value + "%";
    }

    /**
     * Formats date to mm/dd/yy format
     * 
     * @param value date to format
     * @return string version of date in mm/dd/yy format
     */
    public static String formatDate(LocalDate value) {
        return DATE_FORMAT.format(value);
    }
}
