package net.mavroprovato.springcms.component;

import net.mavroprovato.springcms.entity.Parameter;
import net.mavroprovato.springcms.service.ConfigurationParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Utilities for generating URLs.
 */
@Component
public class UrlUtils {

    /** The current HTTP request */
    private final HttpServletRequest request;

    /** The configuration parameter service */
    private final ConfigurationParameterService configurationParameterService;

    /**
     * Create the URL utilities.
     *
     * @param request The current HTTP request.
     */
    @Autowired
    public UrlUtils(HttpServletRequest request, ConfigurationParameterService configurationParameterService) {
        this.request = request;
        this.configurationParameterService = configurationParameterService;
    }

    /**
     * Return the URL of the next page.
     *
     * @param urlPrefix The URL prefix.
     * @param page The page number.
     * @return The URL of the next page.
     */
    public String getOlderPage(String urlPrefix, Page<?> page) {
        if (!urlPrefix.endsWith("/")) {
            urlPrefix += "/";
        }
        if (page.isLast()) {
            return urlPrefix;
        }

        String url = urlPrefix + "page/" + (page.getNumber() + 2);
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        return url;
    }

    /**
     * Return the URL of the previous page.
     *
     * @param urlPrefix The URL prefix.
     * @param page The page number.
     * @return The URL of the previous page.
     */
    public String getNewerPage(String urlPrefix, Page<?> page) {
        if (!urlPrefix.endsWith("/")) {
            urlPrefix += "/";
        }
        if (page.isFirst()) {
            return urlPrefix;
        }
        if (page.getNumber() == 1) {
            return urlPrefix;
        }

        String url = urlPrefix + "page/" + page.getNumber();
        if (request.getQueryString() != null) {
            url += "?" + request.getQueryString();
        }

        return url;
    }

    /**
     * Get the absolute URL from the path.
     *
     * @param path The path.
     * @return The absolute URL.
     */
    public String getAbsoluteUrl(String path) {
        String rootUrl = configurationParameterService.getString(Parameter.SITE_URL);
        if (rootUrl == null || rootUrl.isEmpty()) {
            rootUrl = request.getScheme() + "//" + request.getServerName() + ":" + request.getLocalPort();
        }

        return rootUrl + path;
    }
}
