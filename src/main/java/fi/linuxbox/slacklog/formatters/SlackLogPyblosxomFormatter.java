package fi.linuxbox.slacklog.formatters;

/**
 * Concrete SlackLog formatter that generates Pyblosxom blog entries.
 */
public class SlackLogPyblosxomFormatter extends SlackLogFormatter {
    public SlackLogPyblosxomFormatter() {
        super(SlackLogPyblosxomFormatter.class);
    }

    /**
     * Whether information output is printed.
     *
     * @param quiet {@code true} if file operations should be more verbose.
     */
    public void setQuiet(final Boolean quiet) {
        setattr("quiet", quiet);
    }

    /**
     * Description of the distro version.
     * <p>
     *     E.g. 'Slackware 13.37' or 'Slackware64 current'.
     * </p>
     * @param slackware The distribution and version
     */
    public void setSlackware(final String slackware) {
        setattr("slackware", slackware);
    }

    /**
     * Blog entry directory.
     *
     * @param datadir Entry directory
     */
    public void setDatadir(final String datadir) {
        setattr("datadir", datadir);
    }

    /**
     * Blog entry filename extension.
     *
     * @param extension File extension
     */
    public void setExtension(final String extension) {
        setattr("extension", extension);
    }

    /**
     * Blog entry file encoding.
     *
     * @param encoding File encoding
     */
    public void setEncoding(final String encoding) {
        setattr("encoding", encoding);
    }

    /**
     * Separator for tags.
     *
     * @param tagsSeparator Tag separator
     */
    public void setTagsSeparator(final String tagsSeparator) {
        setattr("tags_separator", tagsSeparator);
    }

    /**
     * Separator for packages.
     *
     * @param pkgSeparator Package separator
     */
    public void setPkgSeparator(final String pkgSeparator) {
        setattr("pkg_separator", pkgSeparator);
    }

    /**
     * Whether pyfilemtime compatible filenames are generated.
     *
     * @param pyfilemtime {@code true} if timestamp is added to file name.
     */
    public void setPyfilemtime(final Boolean pyfilemtime) {
        setattr("pyfilemtime", pyfilemtime);
    }

    /**
     * Whether already existing blog entries are overwritten.
     *
     * @param overwrite {@code true} if files are overwritten
     */
    public void setOverwrite(final Boolean overwrite) {
        setattr("overwrite", overwrite);
    }

    /**
     * Whether already existing blog entries are copied to backups before overwriting.
     *
     * @param backup (@code true} if backups are made
     */
    public void setBackup(final Boolean backup) {
        setattr("backup", backup);
    }

    /**
     * HTML to insert before the entry.
     *
     * @param entryPreamble HTML to insert before the entry.
     */
    public void setEntryPreamble(final String entryPreamble) {
        setattr("entry_preamble", entryPreamble);
    }

    /**
     * HTML to insert after the entry.
     *
     * @param entryPostamble HTML to insert after the entry.
     */
    public void setEntryPostamble(final String entryPostamble) {
        setattr("entry_postamble", entryPostamble);
    }

    /**
     * HTML to insert before the entry description.
     *
     * @param entryDescPreamble HTML to insert before the entry description.
     */
    public void setEntryDescPreamble(final String entryDescPreamble) {
        setattr("entry_desc_preamble", entryDescPreamble);
    }

    /**
     * HTML to insert after the entry description.
     *
     * @param entryDescPostamble HTML to insert after the entry description.
     */
    public void setEntryDescPostamble(final String entryDescPostamble) {
        setattr("entry_desc_postamble", entryDescPostamble);
    }

    /**
     * HTML to insert before the list of packages.
     *
     * @param entryPkgsPreamble HTML to insert before the list of packages.
     */
    public void setEntryPkgsPreamble(final String entryPkgsPreamble) {
        setattr("entry_pkgs_preamble", entryPkgsPreamble);
    }

    /**
     * HTML to insert after the list of packages.
     *
     * @param entryPkgsPostamble HTML to insert after the list of packages.
     */
    public void setEntryPkgsPostamble(final String entryPkgsPostamble) {
        setattr("entry_pkgs_postamble", entryPkgsPostamble);
    }

    /**
     * HTML to insert before a package.
     *
     * @param pkgPreamble HTML to insert before a package.
     */
    public void setPkgPreamble(final String pkgPreamble) {
        setattr("pkg_preamble", pkgPreamble);
    }

    /**
     * HTML to insert after a package.
     *
     * @param pkgPostamble HTML to insert after a package.
     */
    public void setPkgPostamble(final String pkgPostamble) {
        setattr("pkg_postamble", pkgPostamble);
    }

    /**
     * HTML to insert before package name.
     *
     * @param pkgNamePreamble HTML to insert before package name.
     */
    public void setPkgNamePreamble(final String pkgNamePreamble) {
        setattr("pkg_name_preamble", pkgNamePreamble);
    }

    /**
     * HTML to insert after package name.
     *
     * @param pkgNamePostamble HTML to insert after package name.
     */
    public void setPkgNamePostamble(final String pkgNamePostamble) {
        setattr("pkg_name_postamble", pkgNamePostamble);
    }

    /**
     * HTML to insert before package description.
     *
     * @param pkgDescPreamble HTML to insert before package description.
     */
    public void setPkgDescPreamble(final String pkgDescPreamble) {
        setattr("pkg_desc_preamble", pkgDescPreamble);
    }

    /**
     * HTML to insert after package description.
     *
     * @param pkgDescPostamble HTML to insert after package description.
     */
    public void setPkgDescPostamble(final String pkgDescPostamble) {
        setattr("pkg_desc_postamble", pkgDescPostamble);
    }
}
