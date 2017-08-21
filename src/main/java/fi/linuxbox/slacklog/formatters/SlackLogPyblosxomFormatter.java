package fi.linuxbox.slacklog.formatters;

public class SlackLogPyblosxomFormatter extends SlackLogFormatter {
    public SlackLogPyblosxomFormatter() {
        super(SlackLogPyblosxomFormatter.class);
    }

    public void setQuiet(final Boolean quiet) {
        setattr("quiet", quiet);
    }

    public void setSlackware(final String slackware) {
        setattr("slackware", slackware);
    }

    public void setDatadir(final String datadir) {
        setattr("datadir", datadir);
    }

    public void setExtension(final String extension) {
        setattr("extension", extension);
    }

    public void setEncoding(final String encoding) {
        setattr("encoding", encoding);
    }

    public void setTagsSeparator(final String tagsSeparator) {
        setattr("tags_separator", tagsSeparator);
    }

    public void setPkgSeparator(final String pkgSeparator) {
        setattr("pkg_separator", pkgSeparator);
    }

    public void setPyfilemtime(final Boolean pyfilemtime) {
        setattr("pyfilemtime", pyfilemtime);
    }

    public void setOverwrite(final Boolean overwrite) {
        setattr("overwrite", overwrite);
    }

    public void setBackup(final Boolean backup) {
        setattr("backup", backup);
    }

    public void setEntryPreamble(final String entryPreamble) {
        setattr("entry_preamble", entryPreamble);
    }

    public void setEntryPostamble(final String entryPostamble) {
        setattr("entry_postamble", entryPostamble);
    }

    public void setEntryDescPreable(final String entryDescPreable) {
        setattr("entry_desc_preamble", entryDescPreable);
    }

    public void setEntryDescPostamble(final String entryDescPostamble) {
        setattr("entry_desc_postamble", entryDescPostamble);
    }

    public void setEntryPkgsPreamble(final String entryPkgsPreamble) {
        setattr("entry_pkgs_preamble", entryPkgsPreamble);
    }

    public void setEntryPkgsPostamble(final String entryPkgsPostamble) {
        setattr("entry_pkgs_postamble", entryPkgsPostamble);
    }

    public void setPkgPreamble(final String pkgPreamble) {
        setattr("pkg_preamble", pkgPreamble);
    }

    public void setPkgPostamble(final String pkgPostamble) {
        setattr("pkg_postamble", pkgPostamble);
    }

    public void setPkgNamePreamble(final String pkgNamePreamble) {
        setattr("pkg_name_preamble", pkgNamePreamble);
    }

    public void setPkgNamePostamble(final String pkgNamePostamble) {
        setattr("pkg_name_postamble", pkgNamePostamble);
    }

    public void setPkgDescPreamble(final String pkgDescPreamble) {
        setattr("pkg_desc_preamble", pkgDescPreamble);
    }

    public void setPkgDescPostamble(final String pkgDescPostamble) {
        setattr("pkg_desc_postamble", pkgDescPostamble);
    }
}
