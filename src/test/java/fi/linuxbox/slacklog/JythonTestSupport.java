package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySystemState;

import java.time.ZonedDateTime;

public class JythonTestSupport {
    protected static final String INPUT_DATE_DATE = "Wed May  7 16:13:31 CDT 2008";
    protected static final String READABLE_TEST_DATE = "Wed, 07 May 2008 21:13:31 GMT";
    protected static final String ATOM_TEST_DATE = "2008-05-07T21:13:31Z";

    protected static ZonedDateTime getTestDate() {
        return new SlackLogParser().parseDate(INPUT_DATE_DATE);
    }

    @BeforeClass
    public static void setup() throws Exception {
        PySystemState.initialize();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Py.getSystemState().close();
    }
}
