package fi.linuxbox.slacklog.formatters;

import fi.linuxbox.slacklog.PyObjectWrapper;
import fi.linuxbox.slacklog.models.SlackLog;
import org.python.core.Py;
import org.python.core.PyObject;

import static java.util.Objects.requireNonNull;

/**
 * Base class for SlackLog formatters.
 * <p>
 *      This class is meant for subclassing.
 * </p>
 */
public class SlackLogFormatter extends PyObjectWrapper {
    public SlackLogFormatter() {
        super(SlackLogFormatter.class);
    }
    protected SlackLogFormatter(final String pyModuleName, final String pyClassName) {
        super(pyModuleName, pyClassName);
    }
    protected SlackLogFormatter(final String pyModuleName, final Class<? extends SlackLogFormatter> klass) {
        super(pyModuleName, klass);
    }
    protected SlackLogFormatter(final Class<? extends SlackLogFormatter> klass) {
        super(klass);
    }

    /**
     * Number of entries to format from the beginning of the log.
     * <p>
     *     Defaults to {@code null} meaning that all entries are formatted.
     * </p>
     * @param maxEntries Number of entries or {@code null}
     */
    public void setMaxEntries(final Integer maxEntries) {
        setattr("max_entries", maxEntries);
    }

    /**
     * Number of packages to format from the beginnig of each entry.
     * <p>
     *     Defaults to {@code null} meaning that all packages are formatted.
     * </p>
     * @param maxPkgs Number of packages or {@code null}
     */
    public void setMaxPkgs(final Integer maxPkgs) {
        setattr("max_pkgs", maxPkgs);
    }

    /**
     * Returns string representation of the log.
     *
     * @param log The log to format.
     * @return String representation.
     */
    public String format(final SlackLog log) {
        requireNonNull(log);
        final PyObject data = pyInstance.invoke("format", log.unwrap());
        if (data == null || data.equals(Py.None))
            return null;
        return (String) data.__tojava__(String.class);
    }
}
