package com.toolshop.rental_system.date_utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalQuery;

public class DateUtils {

    public static final WeekendTemporalQuery WEEKEND_QUERY = new WeekendTemporalQuery();

    public static final LaborDayTemporalAdjuster LABOR_DAY_ADJUSTER = new LaborDayTemporalAdjuster();

    public static final ObservedIndependenceDayTemporalAdjuster OBSERVED_INDEPENDENCE_DAY_ADJUSTER = new ObservedIndependenceDayTemporalAdjuster();

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
        LocalDate dayAfterEnd = end.plusDays(1);
        return start.datesUntil(dayAfterEnd)
                .filter(date -> date.query(query)).count();
    }

    /**
     * Returns the number of days in the provided date range that are equal to the
     * date specified by the adjuster for the years covered by the range.
     * 
     * @param start    start date (inclusive)
     * @param end      end date (inclusive)
     * @param adjuster TemporalAdjuster to apply to dates in range.
     * @return count of Labor Days in range
     */
    public static long rangeContainsAdjustedDate(LocalDate start, LocalDate end, TemporalAdjuster adjuster) {

        LocalDate dayAfterEnd = end.plusDays(1);

        if (start.getYear() != end.getYear()) {
            return start.datesUntil(dayAfterEnd).filter(date -> date.equals(date.with(adjuster)))
                    .count();
        } else {
            LocalDate adjustedDate = start.with(adjuster);
            return start.datesUntil(dayAfterEnd).filter(date -> date.equals(adjustedDate)).count();
        }
    }
}
