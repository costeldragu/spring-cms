package net.mavroprovato.springcms.datatables;

/**
 * The ordering for a column.
 */
public class Order {
    /** Enumeration for the orderings. */
    public enum Direction {
        /** Sort ascending. */
        asc,
        /** Sort descending. */
        desc
    }

    /** Column to which ordering should be applied. This is an index reference to the columns array of information that
     * is also submitted to the server. */
    private int column;

    /** Ordering direction for this column. It will be asc or desc to indicate ascending ordering or descending
     * ordering, respectively. */
    private Direction dir;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
