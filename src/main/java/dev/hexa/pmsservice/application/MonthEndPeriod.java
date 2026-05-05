package dev.hexa.pmsservice.application;

import java.time.LocalDate;
import java.time.YearMonth;

public record MonthEndPeriod(int monthEndId, int previousMonthEndId, LocalDate currentMonthStart, LocalDate previousMonthStart) {

    public static MonthEndPeriod from(int monthEndId) {
        YearMonth current = YearMonth.of(monthEndId / 100, monthEndId % 100);
        YearMonth previous = current.minusMonths(1);
        int previousId = Integer.parseInt(previous.toString().replace("-", ""));
        return new MonthEndPeriod(monthEndId, previousId, current.atDay(1), previous.atDay(1));
    }
}
