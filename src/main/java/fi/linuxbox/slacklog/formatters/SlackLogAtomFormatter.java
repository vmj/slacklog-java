package fi.linuxbox.slacklog.formatters;

import org.python.core.PyObject;

/**
 * Concrete SlackLog formatter that generates an Atom feed.
 */
public class SlackLogAtomFormatter extends SlackLogFormatter {
    public SlackLogAtomFormatter() {
        super(SlackLogAtomFormatter.class);
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
     * Full URL of the Atom feed.
     *
     * @param link Atom link
     */
    public void setLink(final String link) {
        setattr("link", link);
    }

    /**
     * Full URL of the HTML version.
     *
     * @param webLink Web link
     */
    public void setWebLink(final String webLink) {
        setattr("webLink", webLink);
    }

    /**
     * Name of the feed author.
     *
     * @param name Feed author name
     */
    public void setName(final String name) {
        setattr("name", name);
    }

    /**
     * Email of the feed author.
     *
     * @param email Feed author email
     */
    public void setEmail(final String email) {
        setattr("email", email);
    }
    /**
     * Timestamp when this feed was last generated.
     * <p>
     *     If this is not set, the current timestamp will be generated.
     * </p>
     * @param updated Feed generation timestamp.
     */
    public void setUpdated(final PyObject updated) {
        setattr("updated", updated);
    }
}
