package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogAtomFormatter;
import fi.linuxbox.slacklog.formatters.SlackLogFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogAtomFormatterTest extends JythonTestSupport {
    private SlackLogAtomFormatter formatter() {
        return new SlackLogAtomFormatter();
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
        final String atom1 = "<?xml version=\"1.0\"?>\n" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                "    <link href=\"None\" rel=\"self\" type=\"application/rss+xml\" />\n" +
                "    <title>None ChangeLog</title>\n" +
                "    <link href=\"None\" />\n" +
                "    <updated>";
        // updated is a timestamp or runtime.
        final String atom2 = "</updated>\n" +
                "    <author>\n" +
                "        <name>None</name>\n" +
                "        <email>None</email>\n" +
                "    </author>\n" +
                "    <id>None</id>\n" +
                "</feed>\n";
        assertEquals(atom1, data.substring(0, atom1.length()));
        assertEquals(atom2, data.substring(data.length() - atom2.length()));
    }

    @Test
    public void testFormatSingleEntryLog() {
        final SlackLogAtomFormatter formatter = formatter();
        formatter.setSlackware("Slackware64 -current");
        formatter.setLink("http://expected.url/changes.atom");
        formatter.setWebLink("http://expected.url/changes.html");
        formatter.setName("Jane Doe");
        formatter.setEmail("jane@doe.net");

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
                "  (* Security fix *)\n"+
                "+--------------------------+\n";

        final SlackLogParser parser = new SlackLogParser();
        final SlackLog slacklog = parser.parse(origData);
        assertNotNull(slacklog);

        final String data = formatter.format(slacklog);
        assertNotNull(data);
        final String atom1 = "<?xml version=\"1.0\"?>\n" +
                "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
                "    <link href=\"http://expected.url/changes.atom\" rel=\"self\" type=\"application/rss+xml\" />\n" +
                "    <title>Slackware64 -current ChangeLog</title>\n" +
                "    <link href=\"http://expected.url/changes.html\" />\n" +
                "    <updated>";
        // updated is a timestamp or runtime.
        final String atom2 = "</updated>\n" +
                "    <author>\n" +
                "        <name>Jane Doe</name>\n" +
                "        <email>jane@doe.net</email>\n" +
                "    </author>\n" +
                "    <id>http://expected.url/changes.atom</id>\n" +
                "    <entry>\n" +
                "        <title>Slackware64 -current changes for Fri, 18 Oct 2013 02:41:09 GMT</title>\n" +
                "        <link href=\"http://expected.url/changes.html#20131018T024109Z\" />\n" +
                "        <updated>2013-10-18T02:41:09Z</updated>\n" +
                "        <id>http://expected.url/changes.atom#20131018T024109Z</id>\n" +
                "        <content type=\"html\"><![CDATA[<pre>patches/packages/libtiff-3.9.7-i486-1_slack12.2.tgz:  Upgraded.\n" +
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
                "</pre>]]></content>\n" +
                "    </entry>\n" +
                "</feed>\n";
        assertEquals(atom1, data.substring(0, atom1.length()));
        assertEquals(atom2, data.substring(data.length() - atom2.length()));
    }
}
