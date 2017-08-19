package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.Py;
import org.python.core.PyObject;

/**
 * An entry in a SlackLogEntry.
 * <p>
 *     Consists of a unicode package identifier and a unicode description.
 * </p>
 */
public class SlackLogPkg extends PyObjectWrapper {
    /*
    public SlackLogPkg(final String pkg,
                       final String description,
                       final SlackLogEntry entry) {
        final PyObject pyClass = importClass(SlackLogPkg.class);
        pyInstance = pyClass.__call__(
                new PyUnicode(pkg),
                new PyUnicode(description),
                entry.asPyObject());
    }
    */

    public SlackLogPkg(final PyObject pyInstance) {
        this.pyInstance = pyInstance;
    }


    public String getPkg() {
        return getattr("pkg", String.class);
    }

    public String getDescription() {
        return getattr("description", String.class);
    }

    public SlackLogEntry getEntry() {
        return new SlackLogEntry(getattr("entry"));
    }

    /*
    public void withPkg(final String pkg) {
        setattr("pkg", pkg);
    }

    public void withDescription(final String description) {
        setattr("description", description);
    }
    */
}
