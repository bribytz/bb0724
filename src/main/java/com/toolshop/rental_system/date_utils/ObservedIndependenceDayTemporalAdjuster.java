package com.toolshop.rental_system.date_utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * Finds the "observed" Independence Day for the temporal. If the 4th falls on a
 * weekend, the closest weekday is returned. (ex if the 4th is on a Saturday,
 * the Friday before will be returned)
 * 
 */
public class ObservedIndependenceDayTemporalAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        // get July 4th for temporal's year
        LocalDate date = LocalDate.of(temporal.get(ChronoField.YEAR), 7, 4);

        // check if it's a weekend and adjust if needed
        if (DayOfWeek.SATURDAY.equals(date.getDayOfWeek())) {
            date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        } else if (DayOfWeek.SUNDAY.equals(date.getDayOfWeek())) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return temporal.with(date);
    }

}
