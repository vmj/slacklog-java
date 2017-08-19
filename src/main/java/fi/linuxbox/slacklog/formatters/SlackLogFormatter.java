package fi.linuxbox.slacklog.formatters;

import fi.linuxbox.slacklog.PyObjectWrapper;
import fi.linuxbox.slacklog.models.SlackLog;
import org.python.core.Py;
import org.python.core.PyObject;

public class SlackLogFormatter extends PyObjectWrapper {
    public SlackLogFormatter() {
        super(SlackLogFormatter.class);
    }

    protected SlackLogFormatter(final Class<? extends SlackLogFormatter> klass) {
        super(klass);
    }

    public void setMaxEntries(final Integer maxEntries) {
        setattr("max_entries", maxEntries);
    }

    public void setMaxPkgs(final Integer maxPkgs) {
        setattr("max_pkgs", maxPkgs);
    }

    public String format(final SlackLog log) {
        final PyObject data = pyInstance.invoke("format", log.unwrap());
        if (data == null || data.equals(Py.None))
            return null;
        return (String) data.__tojava__(String.class);
    }
}
