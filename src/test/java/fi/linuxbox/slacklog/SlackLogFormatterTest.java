package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogFormatterTest extends JythonTestSupport {
    private SlackLogFormatter formatter() {
        return new SlackLogFormatter();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(formatter());
    }

    @Test
    public void testMaxEntries() {
        final SlackLogFormatter formatter = formatter();

        formatter.setMaxEntries(10);
        formatter.setMaxEntries(null);
    }

    @Test
    public void testMaxPkgs() {
        final SlackLogFormatter formatter = formatter();

        formatter.setMaxPkgs(10);
        formatter.setMaxPkgs(null);
    }

    @Test
    public void testFormatEmtpy() {
        final SlackLogFormatter formatter = formatter();

        final SlackLog slackLog = new SlackLog();

        final String data = formatter.format(slackLog);
        assertNotNull(data);
        assertEquals("", data);
    }
}
