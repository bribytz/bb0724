package com.toolshop.rental_system.date_utils;

import java.time.DayOfWeek;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.List;

/**
 * Returns true if temporal is a Saturday or Sunday
 */
public class WeekendTemporalQuery implements TemporalQuery<Boolean> {

    private final List<DayOfWeek> weekends = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    @Override
    public Boolean queryFrom(TemporalAccessor temporal) {
        DayOfWeek dayOfWeek = DayOfWeek.from(temporal);

        return weekends.contains(dayOfWeek);
    }

}
