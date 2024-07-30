package com.toolshop.rental_system.date_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeekendTemporalQueryTests {
    private WeekendTemporalQuery weekendTemporalQuery = new WeekendTemporalQuery();

    @DisplayName("Test without weekends")
    @Test
    public void testRangeWithoutWeekends() {
        LocalDate start = LocalDate.of(2024, 7, 30);
        LocalDate end = LocalDate.of(2024, 7, 31);

        assertEquals(0, DateUtils.queryOverRange(start, end, weekendTemporalQuery));
    }

    @DisplayName("Test with Saturday")
    @Test
    public void testRangeWithSaturday() {
        LocalDate start = LocalDate.of(2024, 7, 26);
        LocalDate end = LocalDate.of(2024, 7, 27);

        assertEquals(1, DateUtils.queryOverRange(start, end, weekendTemporalQuery));
    }

    @DisplayName("Test with Saturday and Sunday")
    @Test
    public void testRangeWithSaturdayAndSunday() {
        LocalDate start = LocalDate.of(2024, 7, 26);
        LocalDate end = LocalDate.of(2024, 7, 30);

        assertEquals(2, DateUtils.queryOverRange(start, end, weekendTemporalQuery));
    }

    @DisplayName("Test with multiple weekends")
    @Test
    public void testRangeWithMultipleWeekends() {
        LocalDate start = LocalDate.of(2024, 7, 20);
        LocalDate end = LocalDate.of(2024, 7, 30);

        assertEquals(4, DateUtils.queryOverRange(start, end, weekendTemporalQuery));
    }
}
