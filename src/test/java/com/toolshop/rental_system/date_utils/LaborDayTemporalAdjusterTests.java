package com.toolshop.rental_system.date_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LaborDayTemporalAdjusterTests {

    // Labor day for 2024 is Monday the 2nd

    @Test
    public void testRangeContainsLaborDay() {
        LocalDate start = LocalDate.of(2024, 8, 28);
        LocalDate end = LocalDate.of(2024, 9, 3);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

    @Test
    public void testOneDayRangeContainsLaborDay() {
        LocalDate start = LocalDate.of(2024, 2, 2);
        LocalDate end = LocalDate.of(2024, 9, 2);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

    @Test
    public void testRangeDoesNotContainLaborDay() {
        LocalDate start = LocalDate.of(2024, 8, 28);
        LocalDate end = LocalDate.of(2024, 9, 1);

        assertEquals(0, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

    @Test
    public void testLaborDayIsFirstDayOfMonth() {
        LocalDate start = LocalDate.of(2025, 8, 28);
        LocalDate end = LocalDate.of(2025, 9, 1);

        assertEquals(1, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

    @Test
    public void testRangeAfterLaborDayDoesNotContain() {
        LocalDate start = LocalDate.of(2024, 9, 28);
        LocalDate end = LocalDate.of(2024, 10, 1);

        assertEquals(0, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

    @Test
    public void testMultipleLaborDays() {
        LocalDate start = LocalDate.of(2024, 8, 28);
        LocalDate end = LocalDate.of(2025, 9, 2);

        assertEquals(2, DateUtils.rangeContainsAdjustedDate(start, end, DateUtils.LABOR_DAY_ADJUSTER));
    }

}
