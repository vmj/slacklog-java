package fi.linuxbox.slacklog.parsers;

import fi.linuxbox.slacklog.PyObjectWrapper;
import fi.linuxbox.slacklog.models.SlackLog;
import org.python.core.Py;
import org.python.core.PyObject;

/**
 * A Java class that exposes only the needed APIs from SlackLogParser Python class.
 */
public class SlackLogParser extends PyObjectWrapper {
    public SlackLogParser() {
        super(SlackLogParser.class);
    }

    /**
     * Exposes the SlackLogParser.quiet property to the Java land.
     *
     * @param quiet false if output is allowed
     */
    public void setQuiet(final boolean quiet) {
        setattr("quiet", quiet);
    }

    /**
     * Exposes the SlackLogParser.min_date property to the Java land.
     * <p>
     *     Note: Use the {@link #parseDate(String)} to create the
     *     'datetime.datetime' or 'None' argument.
     * </p>
     * @param datetime Either 'datetime.datetime' or None.
     */
    public void setMinDate(final PyObject datetime) {
        setattr("min_date", datetime);
    }

    /**
     * Exposes the SlackLogParser.parse method to the Java land.
     *
     * @param data ChangeLog.txt contents
     * @return SlackLog python object
     */
    public SlackLog parse(final String data) {
        final PyObject slackLog = pyInstance.invoke("parse", Py.newUnicode(data));
        return new SlackLog(slackLog);
    }

    /**
     * Exposes the SlackLogParser.parse_date method to the Java land.
     * @param date Date string.
     * @return 'datetime.datetime' Python object, or Python None.
     */
    public PyObject parseDate(final String date) {
        final PyObject arg = date != null
                ? Py.newUnicode(date)
                : Py.None;
        return pyInstance.invoke("parse_date", arg);
    }
}
