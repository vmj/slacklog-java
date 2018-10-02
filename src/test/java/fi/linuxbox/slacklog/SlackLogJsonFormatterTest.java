package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogFormatter;
import fi.linuxbox.slacklog.formatters.SlackLogJsonFormatter;
import fi.linuxbox.slacklog.formatters.SlackLogRssFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogJsonFormatterTest extends JythonTestSupport {
    private SlackLogJsonFormatter formatter() {
        final SlackLogJsonFormatter formatter = new SlackLogJsonFormatter();
        return formatter;
    }

    @Test
    public void testNewInstance() {
        assertNotNull(formatter());
    }

    @Test
    public void testFormatEmpty() {
        final SlackLogFormatter formatter = formatter();

        final SlackLog slackLog = new SlackLog();

        final String data = formatter.format(slackLog);
        assertNotNull(data);
        final String json = "{\"endsWithSeparator\":false,\"entries\":[],\"startsWithSeparator\":false}";
        assertEquals(json, data);
    }

    @Test
    public void testFormatIndent() {
        final SlackLogJsonFormatter formatter = formatter();
        formatter.setIndent(4);

        final SlackLog slackLog = new SlackLog();

        final String data = formatter.format(slackLog);
        assertNotNull(data);
        final String json = "{\n" +
                "    \"endsWithSeparator\": false,\n" +
                "    \"entries\": [],\n" +
                "    \"startsWithSeparator\": false\n" +
                "}";
        assertEquals(json, data);
    }
}
