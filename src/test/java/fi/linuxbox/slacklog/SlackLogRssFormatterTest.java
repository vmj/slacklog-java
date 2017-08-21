package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogFormatter;
import fi.linuxbox.slacklog.formatters.SlackLogRssFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogRssFormatterTest extends JythonTestSupport {
    private SlackLogRssFormatter formatter() {
        return new SlackLogRssFormatter();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(formatter());
    }

    @Test
    @Ignore("https://github.com/vmj/slacklog/issues/1")
    public void testFormatEmpty() {
        final SlackLogFormatter formatter = formatter();

        final SlackLog slackLog = new SlackLog();

        final String data = formatter.format(slackLog);
        assertNotNull(data);
        assertEquals("", data);
    }

    @Test
    public void testFormatSingleEntryLog() {
        final SlackLogRssFormatter formatter = formatter();
        formatter.setSlackware("Slackware64 -current");
        formatter.setRssLink("http://expected.url/changes.rss");
        formatter.setWebLink("http://expected.url/changes.html");
        formatter.setDescription("description...");
        formatter.setLanguage("fi");
        formatter.setManagingEditor("jane@doe.net (Jane Doe)");
        formatter.setWebMaster("john@doe.net (John Doe)");

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
        final String rss1 = "<?xml version=\"1.0\"?>\n" +
                "<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">\n" +
                "  <channel>\n" +
                "    <atom:link href=\"http://expected.url/changes.rss\" rel=\"self\" type=\"application/rss+xml\" />\n" +
                "    <title>Slackware64 -current ChangeLog</title>\n" +
                "    <link>http://expected.url/changes.html</link>\n" +
                "    <description>description...</description>\n" +
                "    <docs>http://www.rssboard.org/rss-specification</docs>\n" +
                "    <language>fi</language>\n" +
                "    <managingEditor>jane@doe.net (Jane Doe)</managingEditor>\n" +
                "    <webMaster>john@doe.net (John Doe)</webMaster>\n" +
                "    <pubDate>Fri, 18 Oct 2013 02:41:09 GMT</pubDate>\n" +
                "    <lastBuildDate>";
        // lastBuildDate is a timestamp or runtime.
        final String rss2 = "</lastBuildDate>\n" +
                "    <generator>SlackLog</generator>\n" +
                "    <item>\n" +
                "      <guid isPermaLink=\"true\">http://expected.url/changes.html#20131018T024109Z</guid>\n" +
                "      <title>Slackware64 -current changes for Fri, 18 Oct 2013 02:41:09 GMT</title>\n" +
                "      <pubDate>Fri, 18 Oct 2013 02:41:09 GMT</pubDate>\n" +
                "      <description><![CDATA[<pre>patches/packages/libtiff-3.9.7-i486-1_slack12.2.tgz:  Upgraded.\n" +
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
                "</pre>]]></description>\n" +
                "    </item>\n" +
                "  </channel>\n" +
                "</rss>\n";
        assertEquals(rss1, data.substring(0, rss1.length()));
        assertEquals(rss2, data.substring(data.length() - rss2.length()));
    }
}
