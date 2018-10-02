package fi.linuxbox.slacklog.formatters;

/**
 * Concrete SlackLog formatter that generates an JSON file.
 */
public class SlackLogJsonFormatter extends SlackLogFormatter {
    public SlackLogJsonFormatter() {
        super(SlackLogJsonFormatter.class);
    }

    /**
     * Number of spaces to use for indenting array elements and object keys.
     * <p>
     *     Set to <code>null</code> (the default) to disable any indent or newlines.
     * </p>
     * @param indent Number of spaces to use for indent.
     */
    public void setIndent(final Integer indent) {
        setattr("indent", indent);
    }
}
