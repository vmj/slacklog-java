package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.formatters.SlackLogPyblosxomFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SlackLogPyblosxomFormatterTest extends JythonTestSupport {

    @Rule public TemporaryFolder tmpDir = new TemporaryFolder();

    private SlackLogPyblosxomFormatter formatter() {
        return new SlackLogPyblosxomFormatter();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(formatter());
    }

    @Test
    public void testFormatEmpty() throws Exception {
        final File entriesDir = tmpDir.newFolder("entries");

        final SlackLogPyblosxomFormatter formatter = formatter();
        formatter.setDatadir(entriesDir.getAbsolutePath());

        final SlackLog slackLog = new SlackLog();

        final String data = formatter.format(slackLog);
        assertNotNull(data);
        assertEquals("", data);

        // No FS activity since pyblosxom formatter only deals with entries
        // (but JUnit actually creates the directory)
        final File[] files = entriesDir.listFiles();
        assertNotNull(files);
        assertEquals(0, files.length);
    }

    @Test
    public void testFormatSingleEntryLog() throws Exception {
        final File entriesDir = tmpDir.newFolder("entries");

        final SlackLogPyblosxomFormatter formatter = formatter();
        formatter.setQuiet(false);
        formatter.setSlackware("Slackware64 -current");
        formatter.setDatadir(entriesDir.getAbsolutePath());
        formatter.setExtension("yaml");
        formatter.setEncoding("ISO-8859-1");
        formatter.setTagsSeparator("|");
        formatter.setPkgSeparator(":");
        formatter.setPyfilemtime(true);
        formatter.setOverwrite(true);
        formatter.setBackup(true);
        formatter.setEntryPreamble("---\n");
        formatter.setEntryPostamble("");
        formatter.setEntryDescPreamble("");
        formatter.setEntryDescPostamble("");
        formatter.setEntryPkgsPreamble("");
        formatter.setEntryPkgsPostamble("");
        formatter.setPkgPreamble("");
        formatter.setPkgPostamble("");
        formatter.setPkgNamePreamble("");
        formatter.setPkgNamePostamble("");
        formatter.setPkgDescPreamble("");
        formatter.setPkgDescPostamble("");

        /*
        ---
        Title: entry title
        #tags: ...
        Description: entry description
        # Updates follow
        Changes:
        # Start list of packages
          package name: package description
        # End list of packages
        # End of changes
        # Have fun!
         */

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
        assertEquals("Slackware64 -current changes for Fri, 18 Oct 2013 02:41:09 GMT\n" +
                "#tags Slackware64|-current\n" +
                "---\n" +
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
                "  (* Security fix *)\n", data);

        // assert that there's one file in datadir
        final File[] files = entriesDir.listFiles();
        assertNotNull(files);
        assertEquals(1, files.length);

        final File yaml = files[0];
        assertEquals("slackware64--current-2013-10-18-02-41.yaml", yaml.getName());


        formatter.format(slacklog);

        // assert that there's one backup in datadir
        final File[] files2 = entriesDir.listFiles();
        assertNotNull(files2);
        assertEquals(2, files2.length);

        final String name1 = files2[0].getName();
        final String name2 = files2[1].getName();
        if (name1.length() < name2.length()) {
            assertEquals("slackware64--current-2013-10-18-02-41.yaml", name1);
            assertEquals("slackware64--current-2013-10-18-02-41.yaml~1~", name2);
        } else {
            assertEquals("slackware64--current-2013-10-18-02-41.yaml", name2);
            assertEquals("slackware64--current-2013-10-18-02-41.yaml~1~", name1);
        }
    }
}
