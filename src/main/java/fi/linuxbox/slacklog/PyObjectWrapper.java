package fi.linuxbox.slacklog;

import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyUnicode;

import java.time.ZonedDateTime;

import static fi.linuxbox.slacklog.PyClassImporter.importClass;

public abstract class PyObjectWrapper {
    private static final int javaPackagePrefixLen = "fi.linuxbox.".length();

    protected PyObject pyInstance;

    protected PyObjectWrapper(final String pyModuleName,
                              final String pyClassName) {
        final PyObject pyClass = importClass(pyModuleName, pyClassName);

        this.pyInstance = pyClass.__call__();
    }

    protected PyObjectWrapper(final Class klass) {
        this(klass.getPackage().getName().substring(javaPackagePrefixLen),
                klass.getSimpleName());
    }

    protected PyObjectWrapper() {
    }

    protected PyObject getattr(final String name) {
        return pyInstance.__getattr__(name);
    }

    protected <T> T getattr(final String name, final Class<T> javaClass) {
        final PyObject value = getattr(name);
        if (value == null || value.equals(Py.None))
            return null;
        return (T) value.__tojava__(javaClass);
    }

    protected void setattr(final String name, final Boolean value) {
        final PyObject pyValue = value != null
                ? Py.newBoolean(value) // does not actually create a new value
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final String value) {
        final PyObject pyValue = value != null
                ? new PyUnicode(value)
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final Integer value) {
        final PyObject pyValue = value != null
                ? Py.newInteger(value)
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }

    protected void setattr(final String name, final ZonedDateTime value) {
        // TODO: ZonedDateTime -> datetime.datetime
    }

    protected void setattr(final String name, final PyObject value) {
        final PyObject pyValue = value != null
                ? value
                : Py.None;
        pyInstance.__setattr__(name, pyValue);
    }
}
