/**
 * SlackLog formatters.
 * <p>
 *     A SlackLog formatter takes an in-memory representation of a Slackware
 *     ChangeLog.txt and produces a different representation of it.
 * </p>
 * <p>
 *     The in-memory representation is an instance of {@link fi.linuxbox.slacklog.models.SlackLog}.
 * </p>
 * <p>
 *     Currently, the following formatters are provided:
 *     <ul>
 *       <li>{@link fi.linuxbox.slacklog.formatters.SlackLogTxtFormatter} tries to reproduce the original ChangeLog.txt.</li>
 *       <li>{@link fi.linuxbox.slacklog.formatters.SlackLogRssFormatter} produces an RSS feed.</li>
 *       <li>{@link fi.linuxbox.slacklog.formatters.SlackLogAtomFormatter} produces an Atom feed.</li>
 *       <li>{@link fi.linuxbox.slacklog.formatters.SlackLogJsonFormatter} produces a JSON representation.</li>
 *       <li>{@link fi.linuxbox.slacklog.formatters.SlackLogPyblosxomFormatter} writes the log entries to PyBlosxom HTML entries.</li>
 *     </ul>
 * </p>
 */
package fi.linuxbox.slacklog.formatters;