package net.mavroprovato.springcms.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Object mapping for page.
 */
@Entity
@DiscriminatorValue(ContentType.Values.PAGE)
public class Page extends Content {

    /** The page order */
    @Column(name="page_order")
    private int order;

    /**
     * Return the order of the page.
     *
     * @return The order of the page.
     */
    public int getOrder() {
        return order;
    }

    /**
     * Set the order of the page.
     *
     * @param order The order of the page.
     */
    public void setOrder(int order) {
        this.order = order;
    }
}
