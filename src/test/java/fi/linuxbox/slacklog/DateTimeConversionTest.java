package fi.linuxbox.slacklog;

import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyObject;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DateTimeConversionTest {

    @Test
    public void testToPython() {
        final ZonedDateTime zdt = ZonedDateTime.of(1978, 8, 4,
                15, 34, 43, 12345678, ZoneId.of("UTC"));
        final PyDateTime dob = new PyDateTime(zdt);
        assertEquals(1978, dob.get("year"));
        assertEquals(8, dob.get("month"));
        assertEquals(4, dob.get("day"));
        assertEquals(15, dob.get("hour"));
        assertEquals(34, dob.get("minute"));
        assertEquals(43, dob.get("second"));
        assertEquals(12345, dob.get("microsecond")); // fraction lost, as expected
        assertTrue(dob.hasTzinfo());
        assertTrue(dob.isUTC());
    }

    @Test
    public void testToJava() {
        // Use parser as a convenience: uses actual python code to construct the PyObject, which is then converted
        // on the Java side.
        final ZonedDateTime zonedDateTime = new SlackLogParser().parseDate("Wed May  7 16:13:31 CDT 2008");
        assertEquals(2008, zonedDateTime.getYear());
        assertEquals(5, zonedDateTime.getMonthValue());
        assertEquals(7, zonedDateTime.getDayOfMonth());
        assertEquals(21, zonedDateTime.getHour()); // parser converts to UTC on the python side
        assertEquals(13, zonedDateTime.getMinute());
        assertEquals(31, zonedDateTime.getSecond());
        assertEquals(0, zonedDateTime.getNano());
        final ZoneOffset offset = zonedDateTime.getOffset();
        assertNotNull(offset);
        assertEquals(0, offset.getTotalSeconds());
    }

    private static class PyDateTime extends PyObjectWrapper {
        public PyDateTime(final ZonedDateTime zonedDateTime) {
            pyInstance = toPyDateTime(zonedDateTime);
        }

        public int get(final String field) {
            return getattr(field, Integer.class);
        }

        public boolean hasTzinfo() {
            final PyObject tzinfo = pyInstance.__getattr__("tzinfo");
            return tzinfo != null && !tzinfo.equals(Py.None);
        }

        public boolean isUTC() {
            final PyObject timedelta = pyInstance.invoke("utcoffset");
            if (timedelta == null || timedelta.equals(Py.None))
                return false;

            return timedelta.invoke("total_seconds").__tojava__(Double.class).equals(0.0);
        }
    }
}
