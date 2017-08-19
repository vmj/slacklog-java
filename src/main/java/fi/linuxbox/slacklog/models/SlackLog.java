package fi.linuxbox.slacklog.models;

import fi.linuxbox.slacklog.PyObjectWrapper;
import org.python.core.Py;
import org.python.core.PyObject;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class SlackLog extends PyObjectWrapper {
    /*
    public SlackLog() {
        super(SlackLog.class);
    }
    */

    public SlackLog(final PyObject pyInstance) {
        this.pyInstance = pyInstance;
    }

    public List<SlackLogEntry> getEntries() {
        final PyObject pySeq = getattr("entries");
        if (pySeq == null || pySeq.equals(Py.None) || !pySeq.isSequenceType())
            return emptyList();

        final int len = pySeq.__len__();

        final List<SlackLogEntry> list = new ArrayList<>(len);

        for (int i = 0; i < len; i++) {
            list.add(new SlackLogEntry(pySeq.__getitem__(i)));
        }

        return unmodifiableList(list);
    }
}
