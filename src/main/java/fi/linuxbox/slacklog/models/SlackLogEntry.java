package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.Py;
import org.python.core.PyObject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * An entry in a {@link SlackLog}.
 */
public class SlackLogEntry extends PyObjectWrapper {
    private final SlackLog log;

    /*
    public SlackLogEntry(final ZonedDateTime timestamp,
                         final String description,
                         final SlackLog log,
                         final String checksum,
                         final String identifier,
                         final String parent) {
        final PyObject pyClass = importClass(SlackLogEntry.class);
        pyInstance = pyClass.__call__(new PyObject[] {
                Py.java2py(timestamp),
                new PyUnicode(description),
                log.asPyObject(),
                new PyUnicode(checksum),
                new PyUnicode(identifier),
                new PyUnicode(parent) });
    }
    */

    public SlackLogEntry(final PyObject pyInstance, final SlackLog log) {
        requireNonNull(pyInstance);
        requireNonNull(log);
        if (pyInstance.__getattr__("log") != log.unwrap())
            throw new IllegalArgumentException("wrong log");
        this.pyInstance = pyInstance;
        this.log = log;
    }

    /**
     * Entry timestamp in UTC.
     *
     * @return Entry timestamp.
     */
    public ZonedDateTime getTimestamp() {
        return getattr("timestamp", ZonedDateTime.class);
    }

    /**
     * A unicode description which may be empty.
     *
     * @return Entry description.
     */
    public String getDescription() {
        return getattr("description", String.class);
    }

    /**
     * Reference to the {@link SlackLog} that contains this entry.
     *
     * @return The log containing this entry.
     */
    public SlackLog getLog() {
        return log;
    }

    /**
     * A unicode checksum or <code>null</code>.
     * <p>
     *     This should identify the entry by content.  Two different logs may have the same entry,
     *     but those entries have different parent.
     * </p>
     * @return Entry checksum.
     */
    public String getChecksum() {
        return getattr("checksum", String.class);
    }

    /**
     * A unicode identifier or <code>null</code>.
     * <p>
     *     This should identify the entry by content and parent.
     * </p>
     * @return Entry identifier.
     */
    public String getIdentifier() {
        return getattr("identifier", String.class);
    }

    /**
     * A unicode parent identifier or <code>null</code>.
     *
     * @return Entry parent identifier.
     */
    public String getParent() {
        return getattr("parent", String.class);
    }

    /**
     * The original timezone of the entry as ? or <code>null</code>.
     *
     * @return Original entry timezone.
     */
    /*
    public PyObject getTimezone() {
        return getattr("timezone"); // TODO: datetime.tzinfo -> ?
    }
    */

    /**
     * Returns the list of packages in this entry.
     * <p>
     *     The returned list is an unmodifiable list.  The elements in the
     *     list are, however, not immutable.  In other words, while elements
     *     can not be added, removed, or replaced, they can be modified.
     * </p>
     * @return A non-null list of {@link SlackLogPkg} instances.
     */
    public List<SlackLogPkg> getPkgs() {
        final PyObject pySeq = getattr("pkgs");
        if (pySeq == null || pySeq.equals(Py.None) || !pySeq.isSequenceType())
            return emptyList();

        final int len = pySeq.__len__();

        final List<SlackLogPkg> list = new ArrayList<>(len);

        for (int i = 0; i < len; i++) {
            final PyObject pyPkg = pySeq.__getitem__(i);
            list.add(new SlackLogPkg(pyPkg, this));
        }

        return unmodifiableList(list);
    }

    /*
    public void withTimestamp(final ZonedDateTime timestamp) {
        setattr("timestamp", timestamp);
    }

    public void withDescription(final String description) {
        setattr("description", description);
    }

    public void withChecksum(final String checksum) {
        setattr("checksum", checksum);
    }

    public void withIdentifier(final String identifier) {
        setattr("identifier", identifier);
    }

    public void withParent(final String parent) {
        setattr("parent", parent);
    }

    public void withPkgs(final List<SlackLogPkg> pkgs) {
        //setattr("pkgs", pkgs);
    }
    */

    public PyObject unwrap() {
        return pyInstance;
    }
}
