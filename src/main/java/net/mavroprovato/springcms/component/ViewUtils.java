package net.mavroprovato.springcms.component;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ViewUtils {

    /**
     * Return the URL of the next page.
     *
     * @param urlPrefix The URL prefix.
     * @param page The page number.
     * @return The URL of the next page.
     */
    public static String getOlderPage(String urlPrefix, Page<?> page) {
        if (page.isLast()) {
            return "#";
        }

        return urlPrefix + "page/" + (page.getNumber() + 2);
    }

    /**
     * Return the URL of the previous page.
     *
     * @param urlPrefix The URL prefix.
     * @param page The page number.
     * @return The URL of the previous page.
     */
    public static String getNewerPage(String urlPrefix, Page<?> page) {
        if (page.isFirst()) {
            return "#";
        }

        if (page.getNumber() == 1) {
            return urlPrefix;
        }

        return urlPrefix + "page/" + page.getNumber();
    }
}
