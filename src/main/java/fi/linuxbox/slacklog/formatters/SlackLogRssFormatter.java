package fi.linuxbox.slacklog.formatters;

public class SlackLogRssFormatter extends SlackLogFormatter {
    public SlackLogRssFormatter() {
        super(SlackLogRssFormatter.class);
    }

    public void setSlackware(final String slackware) {
        setattr("slackware", slackware);
    }

    public void setRssLink(final String rssLink) {
        setattr("rssLink", rssLink);
    }

    public void setWebLink(final String webLink) {
        setattr("webLink", webLink);
    }

    public void setDescription(final String description) {
        setattr("description", description);
    }

    public void setLanguage(final String language) {
        setattr("language", language);
    }

    public void setManagingEditor(final String managingEditor) {
        setattr("managingEditor", managingEditor);
    }

    public void setWebMaster(final String webMaster) {
        setattr("webMaster", webMaster);
    }
}
