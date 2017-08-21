package fi.linuxbox.slacklog.formatters;

/**
 * Concrete SlackLog formatter that tries to regenerate the original
 * ChangeLog.txt.
 */
public class SlackLogTxtFormatter extends SlackLogFormatter {
    public SlackLogTxtFormatter() {
        super(SlackLogTxtFormatter.class);
    }
}
