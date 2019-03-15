package fi.linuxbox.slacklog.parsers;

import fi.linuxbox.slacklog.PyObjectWrapper;
import fi.linuxbox.slacklog.models.SlackLog;
import org.python.core.Py;
import org.python.core.PyObject;

import java.time.ZonedDateTime;

/**
 * Parser for recent (12.x and newer) Slackware ChangeLogs.
 */
public class SlackLogParser extends PyObjectWrapper {
    public SlackLogParser() {
        super(SlackLogParser.class);
    }

    /**
     * Whether warnings about date parsing are printed.
     *
     * @param quiet {@code true} if output is not allowed
     */
    public void setQuiet(final Boolean quiet) {
        setattr("quiet", quiet);
    }

    /**
     * Older log entries are ignored (not parsed).
     * <p>
     *     Note: Use the {@link #parseDate(String)} to create the
     *     'datetime.datetime' or 'None' argument.
     * </p>
     * @param datetime Either 'datetime.datetime' or None.
     */
    public void setMinDate(final ZonedDateTime datetime) {
        setattr("min_date", datetime);
    }

    /**
     * Return the in-memory representation of the data.
     *
     * @param data ChangeLog.txt contents
     * @return {@link SlackLog} instance
     */
    public SlackLog parse(final String data) {
        final PyObject slackLog = pyInstance.invoke("parse", Py.newUnicode(data));
        return new SlackLog(slackLog);
    }

    /**
     * Parse a time string into a timestamp.
     *
     * @param date Date string.
     * @return 'datetime.datetime' Python object, or Python None.
     */
    public ZonedDateTime parseDate(final String date) {
        final PyObject arg = date != null
                ? Py.newUnicode(date)
                : Py.None;
        return toJavaDateTime(pyInstance.invoke("parse_date", arg));
    }
}
