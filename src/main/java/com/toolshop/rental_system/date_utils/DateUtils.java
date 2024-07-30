package com.toolshop.rental_system.date_utils;

import java.time.LocalDate;
import java.time.temporal.TemporalQuery;

public class DateUtils {

    /**
     * Returns the number of days in the provided range that match the query
     * 
     * @param start start date (inclusive)
     * @param end   end date (inclusive)
     * @param query query to run over the date range
     * @return count of days in the range that match the query
     */
    public static long queryOverRange(LocalDate start, LocalDate end, TemporalQuery<Boolean> query) {
        // datesUntil is exclusive, so we need the day after the end date
        LocalDate dayAfterDueDate = end.plusDays(1);
        return start.datesUntil(dayAfterDueDate)
                .filter(date -> date.query(query)).count();
    }
}
