package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.PyObject;

import static java.util.Objects.requireNonNull;

/**
 * An entry in a {@link SlackLogEntry}.
 */
public class SlackLogPkg extends PyObjectWrapper {
    private final SlackLogEntry entry;

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

    public SlackLogPkg(final PyObject pyInstance, final SlackLogEntry entry) {
        requireNonNull(pyInstance);
        requireNonNull(entry);
        if (pyInstance.__getattr__("entry") != entry.unwrap())
            throw new IllegalArgumentException("wrong entry");
        this.pyInstance = pyInstance;
        this.entry = entry;
    }

    /**
     * A unicode package identifier.
     *
     * @return Package identifier.
     */
    public String getPkg() {
        return getattr("pkg", String.class);
    }

    /**
     * A unicode description.
     *
     * @return Package description.
     */
    public String getDescription() {
        return getattr("description", String.class);
    }

    /**
     * Reference to the {@link SlackLogEntry} that contains this package.
     *
     * @return The entry containing this package.
     */
    public SlackLogEntry getEntry() {
        return entry;
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
