package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.Py;
import org.python.core.PyObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * An entry in a SlackLog.
 * <p>
 *     Consist of a timestamp in UTC, and a unicode description which may be
 *     empty.
 * </p>
 * <p>
 *     Also contains a list of SlackLogPkg objects.
 * </p>
 * <p>
 *     Since 0.9.1 a checksum was added, which is either None or a unicode
 *     string.  If using the default parser, the value is a SHA-512 as a
 *     HEX string (and never None), and identifies the entry by content.
 *     Note that two entries in different changelogs may have the same
 *     checksum, but different parents.
 * </p>
 * <p>
 *      Since 0.9.1 an identifier was added, which is either None or a
 *      unicode string.  If using the default parser, the value is a SHA-512
 *      as a HEX string (and never None), and identifies the entry by
 *      content and parent.
 * </p>
 * <p>
 *     Since 0.9.1 a parent was added, which is either None or a unicode
 *     string.  If using the default parser, the value is the identifier of
 *     the next (older) log entry.
 * </p>
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

    /*
    public PyObject getTimestamp() {
        return getattr("timestamp"); // TODO: datetime.datetime -> ZonedDateTime
    }
    */

    public String getDescription() {
        return getattr("description", String.class);
    }

    public SlackLog getLog() {
        return log;
    }

    public String getChecksum() {
        return getattr("checksum", String.class);
    }

    public String getIdentifier() {
        return getattr("identifier", String.class);
    }

    public String getParent() {
        return getattr("parent", String.class);
    }

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
