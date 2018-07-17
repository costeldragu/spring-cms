package net.mavroprovato.springcms.datatables;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Represents the request that is sent by the data table javascript library.
 */
public class DataTableRequest {

    /** Draw counter. This is used by DataTables to ensure that the Ajax returns from server-side processing requests
     * are drawn in sequence by DataTables */
    private int draw;

    /** Paging first record indicator. This is the start point in the current data set. */
    private int start;

    /** Number of records that the table can display in the current draw. It is expected that the number of records
     * returned will be equal to this number, unless the server has fewer records to return. Note that this can be -1
     * to indicate that all records should be returned */
    private int length;

    /**
     * Return the draw counter.
     *
     * @return The draw counter.
     */
    public int getDraw() {
        return draw;
    }

    /**
     * Set the draw counter.
     *
     * @param draw The draw counter.
     */
    public void setDraw(int draw) {
        this.draw = draw;
    }

    /**
     * Return the paging first record indicator.
     *
     * @return The paging first record indicator
     */
    public int getStart() {
        return start;
    }

    /**
     * Set the paging first record indicator.
     *
     * @param start The paging first record indicator
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Return the number of records that the table can display in the current draw.
     *
     * @return The number of records that the table can display in the current draw.
     */
    public int getLength() {
        return length;
    }

    /**
     * Set the number of records that the table can display in the current draw.
     *
     * @param length The number of records that the table can display in the current draw.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the page request for the data table request.
     *
     * @return The page request for the data table request.
     */
    public PageRequest getPageRequest() {
        return PageRequest.of(start / length, length, Sort.Direction.DESC, "updatedAt");
    }
}
