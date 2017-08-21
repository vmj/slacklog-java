/**
 * Convert Slackware ChangeLog to various formats.
 * <p>
 *     Start with instantiating a
 *     {@link fi.linuxbox.slacklog.parsers.SlackLogParser}.  Use that to
 *     parse a {@code ChangeLog.txt} into a
 *     {@link fi.linuxbox.slacklog.models.SlackLog}.  Then instantiate one
 *     of the {@link fi.linuxbox.slacklog.formatters.SlackLogFormatter}
 *     subclasses and use that to generate the desired output.
 * </p>
 */
package fi.linuxbox.slacklog;