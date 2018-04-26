package net.mavroprovato.springcms.dto;

import java.time.YearMonth;

/**
 * An object that holds counts per month.
 */
public final class CountByMonth {

    /** The month **/
    private final YearMonth month;

    /** The object count .*/
    private final long count;

    /**
     * Create the object.
     *
     * @param year The year.
     * @param month The month of year, from 1 (January) to 12 (December)
     * @param count The object count.
     */
    public CountByMonth(int year, int month, long count) {
        this.month = YearMonth.of(year, month);
        this.count = count;
    }

    /**
     * Return the month.
     *
     * @return The month.
     */
    public YearMonth getMonth() {
        return month;
    }

    /**
     * Return the object count.
     *
     * @return The object count.
     */
    public long getCount() {
        return count;
    }
}
