package com.toolshop.rental_system.date_utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class LaborDayTemporalAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal);

        // get september for temporal
        if (!Month.SEPTEMBER.equals(date.getMonth())) {
            date = date.withMonth(Month.SEPTEMBER.getValue());
        }

        // get first day
        date = date.with(TemporalAdjusters.firstDayOfMonth());

        // check if monday? if not use next(DayOfWeeK) to get the first Monday
        if (!DayOfWeek.MONDAY.equals(date.getDayOfWeek())) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return temporal.with(date);
    }

}
