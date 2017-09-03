package fi.linuxbox.slacklog.formatters;

import java.time.ZonedDateTime;

/**
 * Concrete SlackLog formatter that generates an RSS feed.
 */
public class SlackLogRssFormatter extends SlackLogFormatter {
    public SlackLogRssFormatter() {
        super(SlackLogRssFormatter.class);
    }

    /**
     * Description of the distro version.
     * <p>
     *     E.g. 'Slackware 13.37' or 'Slackware64 current'.
     * </p>
     * @param slackware The distribution and version
     */
    public void setSlackware(final String slackware) {
        setattr("slackware", slackware);
    }

    /**
     * Full URL of the RSS feed.
     *
     * @param rssLink RSS link
     */
    public void setRssLink(final String rssLink) {
        setattr("rssLink", rssLink);
    }

    /**
     * Full URL of the WWW version of the feed.
     *
     * @param webLink Web link
     */
    public void setWebLink(final String webLink) {
        setattr("webLink", webLink);
    }

    /**
     * Description of the feed.
     *
     * @param description Description
     */
    public void setDescription(final String description) {
        setattr("description", description);
    }

    /**
     * Language identifier.
     * <p>
     *     E.g. 'en'.
     * </p>
     *
     * @param language Language identifier
     */
    public void setLanguage(final String language) {
        setattr("language", language);
    }

    /**
     * Email, and possibly name, of the feed manager.
     * <p>
     *     E.g. 'jane@doe.net (Jane Doe)'.
     * </p>
     * @param managingEditor Feed manager
     */
    public void setManagingEditor(final String managingEditor) {
        setattr("managingEditor", managingEditor);
    }

    /**
     * Email, and possibly name, of the webmaster.
     * <p>
     *     E.g. 'john@doe.net (John Doe)'.
     * </p>
     * @param webMaster Webmaster
     */
    public void setWebMaster(final String webMaster) {
        setattr("webMaster", webMaster);
    }

    /**
     * Timestamp when this feed was last generated.
     * <p>
     *     If this is not set, the current timestamp will be generated.
     * </p>
     * @param lastBuildDate Feed generation timestamp.
     */
    public void setLastBuildDate(final ZonedDateTime lastBuildDate) {
        setattr("lastBuildDate", lastBuildDate);
    }
}
