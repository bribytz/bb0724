package com.toolshop.rental_system.date_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObservedIndependenceDayTemporalAdjusterTests {

    @Test
    public void testRangeContainsFourth() {
        LocalDate start = LocalDate.of(2024, 7, 1);
        LocalDate end = LocalDate.of(2024, 7, 5);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.OBSERVED_INDEPENDENCE_DAY_ADJUSTER));
    }

    @Test
    public void testRangeDoesNotContainFourth() {
        LocalDate start = LocalDate.of(2024, 7, 5);
        LocalDate end = LocalDate.of(2024, 7, 10);

        assertEquals(0, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.OBSERVED_INDEPENDENCE_DAY_ADJUSTER));
    }

    @Test
    public void testRangeContainsFourthOnObservedOnFriday() {
        LocalDate start = LocalDate.of(2026, 7, 1);
        LocalDate end = LocalDate.of(2026, 7, 3);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.OBSERVED_INDEPENDENCE_DAY_ADJUSTER));
    }

    @Test
    public void testRangeContainsFourthOnObservedOnMonday() {
        LocalDate start = LocalDate.of(2027, 7, 5);
        LocalDate end = LocalDate.of(2027, 7, 6);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.OBSERVED_INDEPENDENCE_DAY_ADJUSTER));
    }
}
