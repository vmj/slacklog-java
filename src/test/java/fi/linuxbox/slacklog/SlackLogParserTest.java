package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.models.SlackLogEntry;
import fi.linuxbox.slacklog.models.SlackLogPkg;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Test;
import org.python.core.PyObject;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class SlackLogParserTest extends JythonTestSupport {

    private SlackLogParser parser() {
        return new SlackLogParser();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(parser());
    }

    @Test
    public void testParseDate() {
        assertNotNull(parser().parseDate("Wed May  7 16:13:31 CDT 2008"));
    }

    @Test
    public void testSetMinDate() {
        final SlackLogParser parser = parser();
        final ZonedDateTime minDate = parser.parseDate("Wed May  7 16:13:31 CDT 2008");

        parser.setMinDate(minDate);
        parser.setMinDate(null);
    }

    @Test
    public void testSetQuiet() {
        final SlackLogParser parser = parser();

        parser.setQuiet(true);
        parser.setQuiet(false);
    }

    @Test
    public void testParse() {
        final SlackLogParser parser = parser();
        final SlackLog slacklog = parser.parse("Fri Oct 18 02:41:09 UTC 2013\n" +
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
                "  (* Security fix *)\n" +
                "+--------------------------+\n");
        assertNotNull(slacklog);

        final List<SlackLogEntry> entries = slacklog.getEntries();
        assertNotNull(entries);
        assertEquals(1, entries.size());

        final SlackLogEntry slackLogEntry = entries.get(0);
        assertNotNull(slackLogEntry);
        assertTrue(slackLogEntry.getLog() == slacklog);
        assertEquals("", slackLogEntry.getDescription());

        final List<SlackLogPkg> pkgs = slackLogEntry.getPkgs();
        assertNotNull(pkgs);
        assertEquals(1, pkgs.size());

        final SlackLogPkg slackLogPkg = pkgs.get(0);
        assertNotNull(slackLogPkg);
        assertTrue(slackLogPkg.getEntry() == slackLogEntry);
        assertEquals("patches/packages/libtiff-3.9.7-i486-1_slack12.2.tgz", slackLogPkg.getPkg());
        assertTrue(slackLogPkg.getDescription().startsWith("  Upgraded.\n"));
        assertTrue(slackLogPkg.getDescription().endsWith("  (* Security fix *)\n"));
    }

    @Test
    public void testEntryTimezone() {
        final SlackLogParser parser = parser();
        final SlackLog slacklog = parser.parse("Tue May 19 15:36:49 CDT 2009\n" +
                "<tick> <tick> Ermm... is this thing on?\n" +
                "\n" +
                "Initial public release of Slackware64-current.\n" +
                "He's trying to lay low, but thanks to Eric Hameleers for the huge amount\n" +
                "of work he did to make this possible.  :-)\n" +
                "\n" +
                "Enjoy!\n");
        assertNotNull(slacklog);

        final List<SlackLogEntry> entries = slacklog.getEntries();
        assertNotNull(entries);
        assertEquals(1, entries.size());

        final SlackLogEntry slackLogEntry = entries.get(0);
        assertNotNull(slackLogEntry);

        final ZoneOffset offset = slackLogEntry.getTimezone();
        assertNotNull(offset);
        assertEquals(-5 * 60 * 60, offset.getTotalSeconds());
    }
}
