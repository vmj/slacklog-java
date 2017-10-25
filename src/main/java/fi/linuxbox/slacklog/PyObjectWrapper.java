package fi.linuxbox.slacklog;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyUnicode;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static fi.linuxbox.slacklog.PyClassImporter.importClass;

public abstract class PyObjectWrapper {
    private static final int javaPackagePrefixLen = "fi.linuxbox.".length();

    protected PyObject pyInstance;

    protected PyObjectWrapper(final String pyModuleName,
                              final String pyClassName) {
        final PyObject pyClass = importClass(pyModuleName, pyClassName);

        this.pyInstance = pyClass.__call__();
    }

    protected PyObjectWrapper(final Class klass) {
        this(klass.getPackage().getName().substring(javaPackagePrefixLen),
                klass.getSimpleName());
    }

    protected PyObjectWrapper() {
    }

    protected PyObject getattr(final String name) {
        return pyInstance.__getattr__(name);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getattr(final String name, final Class<T> javaClass) {
        final PyObject value = getattr(name);
        if (value == null || value.equals(Py.None))
            return null;
        if (javaClass.equals(ZonedDateTime.class))
            return (T) toJavaDateTime(value);
        return (T) value.__tojava__(javaClass);
    }

    protected void setattr(final String name, final Boolean value) {
        final PyObject pyValue = value != null
                ? Py.newBoolean(value) // does not actually create a new value
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final String value) {
        final PyObject pyValue = value != null
                ? new PyUnicode(value)
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final Integer value) {
        final PyObject pyValue = value != null
                ? Py.newInteger(value)
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final ZonedDateTime value) {
        final PyObject pyValue = value != null
                ? toPyDateTime(value)
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final PyObject value) {
        final PyObject pyValue = value != null
                ? value
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    // SlackLogParser.parseDate(String) first uses Python side to parse (which converts to UTC) and then calls this.
    protected static ZonedDateTime toJavaDateTime(final PyObject datetime) {
        if (datetime == null || datetime.equals(Py.None))
            return null;
        // Java year is from -999_999_999 to 999_999_999
        // Python year is only from 1 to 9999
        final int year = (int) datetime.__getattr__("year").__tojava__(Integer.class);
        // Both types have months from 1 to 12.
        final int month = (int) datetime.__getattr__("month").__tojava__(Integer.class);
        // Python day is from 1 to "the number of days in the given month of the given year"
        // while Java says just "from 1 to 31".
        final int dayOfMonth = (int) datetime.__getattr__("day").__tojava__(Integer.class);
        // Both types have hours from 0 to 23.
        final int hour = (int) datetime.__getattr__("hour").__tojava__(Integer.class);
        // Both types have minutes from 0 to 59.
        final int minute = (int) datetime.__getattr__("minute").__tojava__(Integer.class);
        // Both types have seconds from 0 to 59.
        final int second = (int) datetime.__getattr__("second").__tojava__(Integer.class);
        // Python has microseconds, while Java has nanoseconds.
        // Java nanoOfSecond range is from 0 to 999,999,999, which is one smaller
        // than Python microsecond range(1,000,000).
        int nanoOfSecond = 0;
        final int microsecond = (int) datetime.__getattr__("microsecond").__tojava__(Integer.class);
        if (microsecond < 1000000) {
            // fraction of a second
            nanoOfSecond = 1000 * microsecond;
        }

        // Only UTC expected here
        final ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(0); // from -64800 to +64800 (+-18 hours)

        final ZonedDateTime zdt = ZonedDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond, zoneOffset);

        // Add the microseconds that amounted to a full second (and wasn't converted to nanoOfSecond).
        if (microsecond == 1000000)
            return zdt.plusSeconds(1);

        return zdt;
    }

    // SlackLogAtomFormatter.setUpdated(ZonedDateTime) - on Python side, not enforced but assumed UTC
    // SlackLogRssFormatter.setLastBuildDate(ZonedDateTime) - on Python side, not enforced but assumed UTC
    // SlackLogParser.setMinDate(ZonedDateTime) - on Python side, tzinfo not specified
    //
    //  In all above, the Python script functions all use parser.parse_date(unicode) to set those dates.
    //  I.e. they all use UTC.
    protected static PyObject toPyDateTime(final ZonedDateTime datetime) {
        if (datetime == null)
            return Py.None;
        if (datetime.getOffset().getTotalSeconds() != 0)
            throw new IllegalArgumentException("only 0 offset supported for now");

        final PyObject tz = PyClassImporter.importClass("dateutil", "tz");
        final PyObject tzinfo = tz.invoke("tzutc");

        final PyObject pyClass = PyClassImporter.importClass("datetime", "datetime");
        final PyObject pyDateTime = pyClass.__call__(Py.javas2pys(
                datetime.getYear(), // This can overflow
                datetime.getMonthValue(),
                datetime.getDayOfMonth(), // OK (assuming ZonedDateTime validates max day same as datetime)
                datetime.getHour(),
                datetime.getMinute(),
                datetime.getSecond(),
                datetime.getNano() / 1000, // microsecond fractions will get lost
                tzinfo
        ));

        return pyDateTime;
    }
}
