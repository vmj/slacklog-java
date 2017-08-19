package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.Py;
import org.python.core.PyObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class SlackLog extends PyObjectWrapper {
    public SlackLog() {
        super(SlackLog.class);
    }

    public SlackLog(final PyObject pyInstance) {
        this.pyInstance = pyInstance;
    }

    /**
     * Returns the list of entries in this log.
     * <p>
     *     The returned list is an unmodifiable list.  The elements in the
     *     list are, however, not immutable.  In other words, while elements
     *     can not be added, removed, or replaced, they can be modified.
     * </p>
     * @return A non-null list of {@link SlackLogEntry} instances.
     */
    public List<SlackLogEntry> getEntries() {
        final PyObject pySeq = getattr("entries");
        if (pySeq == null || pySeq.equals(Py.None) || !pySeq.isSequenceType())
            return emptyList();

        final int len = pySeq.__len__();

        final List<SlackLogEntry> list = new ArrayList<>(len);

        for (int i = 0; i < len; i++) {
            final PyObject pyEntry = pySeq.__getitem__(i);
            list.add(new SlackLogEntry(pyEntry, this));
        }

        return unmodifiableList(list);
    }

    public PyObject unwrap() {
        return pyInstance;
    }
}
