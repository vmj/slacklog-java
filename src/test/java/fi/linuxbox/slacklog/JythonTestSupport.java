package fi.linuxbox.slacklog;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.python.core.Py;
import org.python.core.PySystemState;

public class JythonTestSupport {
    @BeforeClass
    public static void setup() throws Exception {
        PySystemState.initialize();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Py.getSystemState().close();
    }
}
