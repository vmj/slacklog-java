package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogFormatter;
import fi.linuxbox.slacklog.formatters.SlackLogTxtFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogTxtFormatterTest extends JythonTestSupport {
    private SlackLogFormatter formatter() {
        return new SlackLogTxtFormatter();
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

    @Test
    public void testFormatSingleEntryLog() {
        final SlackLogFormatter formatter = formatter();

        final String origData = "Fri Oct 18 02:41:09 UTC 2013\n" +
                "patches/packages/libtiff-3.9.7-i486-1_slack12.2.tgz:  Upgraded.\n" +
                "  Patched overflows, crashes, and out of bounds writes.\n" +
                "  Thanks to mancha for the backported patches.\n" +
                "  For more information, see:\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-2088\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-2113\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-4447\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2012-4564\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-1960\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-1961\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-4231\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-4232\n" +
                "    http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2013-4244\n" +
                "  (* Security fix *)\n";

        final SlackLogParser parser = new SlackLogParser();
        final SlackLog slacklog = parser.parse(origData +
                "+--------------------------+\n");
        assertNotNull(slacklog);

        final String data = formatter.format(slacklog);
        assertNotNull(data);
        assertEquals(origData, data); // NOTE: fix slacklog: drops empty element(s)
    }
}
