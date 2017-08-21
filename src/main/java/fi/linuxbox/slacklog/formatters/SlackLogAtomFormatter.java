package fi.linuxbox.slacklog.formatters;

public class SlackLogAtomFormatter extends SlackLogFormatter {
    public SlackLogAtomFormatter() {
        super(SlackLogAtomFormatter.class);
    }

    public void setSlackware(final String slackware) {
        setattr("slackware", slackware);
    }

    public void setLink(final String link) {
        setattr("link", link);
    }

    public void setWebLink(final String webLink) {
        setattr("webLink", webLink);
    }

    public void setName(final String name) {
        setattr("name", name);
    }

    public void setEmail(final String email) {
        setattr("email", email);
    }
}
