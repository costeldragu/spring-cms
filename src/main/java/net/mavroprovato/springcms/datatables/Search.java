package net.mavroprovato.springcms.datatables;

/**
 * A search predicate, to be applied on a field or globally.
 */
public class Search {

    /** The search value. To be applied to all columns which have searchable as true. */
    private String value;

    /** True if the search filter should be treated as a regular expression for advanced searching, false otherwise. */
    private boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }
}
