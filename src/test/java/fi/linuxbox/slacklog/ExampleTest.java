package fi.linuxbox.slacklog;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import fi.linuxbox.slacklog.formatters.SlackLogJsonFormatter;
import fi.linuxbox.slacklog.models.SlackLog;
import fi.linuxbox.slacklog.parsers.SlackLogParser;
import org.python.core.Py;
import org.python.core.PySystemState;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ExampleTest {

    @Test
    public void test() throws IOException {
        // Initialize Jython
        PySystemState.initialize();

        // Read the source ChangeLog.txt
        final String changelog = read("src/test/resources/ChangeLog.txt", ISO_8859_1);

        // Parse the ChangeLog.txt
        final SlackLog slacklog = new SlackLogParser().parse(changelog);

        // Convert the ChangeLog to JSON
        final SlackLogJsonFormatter formatter = new SlackLogJsonFormatter();
        formatter.setIndent(4);
        final String json = formatter.format(slacklog);

        // Write the output
        write("src/test/resources/ChangeLog.json", json.getBytes(UTF_8));

        // Close the resources
        Py.getSystemState().close();
    }

    private String read(final String path, final Charset charset) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), charset);
    }

    private void write(final String path, final byte[] data) throws IOException {
        Files.write(Paths.get(path), data);
    }

    @BeforeClass
    public static void setup() throws Exception {
        try {
            Files.delete(Paths.get("src/test/resources/ChangeLog.json"));
        } catch (final NoSuchFileException e) {
            // OK
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Files.delete(Paths.get("src/test/resources/ChangeLog.json"));
    }
}
