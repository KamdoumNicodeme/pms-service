package dev.hexa.pmsservice.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonthEndPeriodTest {

    @Test
    void shouldComputePreviousMonthFromMonthEndId() {
        MonthEndPeriod period = MonthEndPeriod.from(202601);

        assertEquals(202601, period.monthEndId());
        assertEquals(202512, period.previousMonthEndId());
        assertEquals("2026-01-01", period.currentMonthStart().toString());
        assertEquals("2025-12-01", period.previousMonthStart().toString());
    }
}
