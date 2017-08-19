package fi.linuxbox.slacklog;

import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyObject;
import org.python.core.PyUnicode;

import static java.util.Objects.requireNonNull;

public class PyClassImporter {
    protected static final PyObject __import__ = Py.newString("__import__");
    protected static final String[] keywords = new String[]{ "name", "fromlist" };

    public static PyObject importClass(final String pyModuleName,
                                       final String pyClassName) {
        requireNonNull(pyModuleName);
        requireNonNull(pyClassName);

        final PyObject importer = Py.getSystemState().getBuiltins().__getitem__(__import__);

        final PyObject[] args = new PyObject[2];

        // name argument to the __import__ function
        args[0] = new PyUnicode(pyModuleName);
        // fromlist argument to the __import__ function
        args[1] = new PyArray(String.class, new String[] { pyClassName });

        final PyObject pyModule = importer.__call__(args, keywords);

        final PyObject pyClass = pyModule.__getattr__(pyClassName);

        return pyClass;
    }

    public static PyObject importClass(final Class javaClass) {
        return importClass(
                javaClass.getPackage().getName().substring(12),
                javaClass.getSimpleName());
    }
}
