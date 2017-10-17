package com.limbo.icedcoco;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "settings")
public class SettingsInfo {
    enum OptionFrequency {
        Always("Always"),
        Ask("Ask"),
        Never("Never");

        private final String text;

        OptionFrequency(final String text) {
            this.text = text;
        }

        String getKey() {
            return text;
        }
    }

    @XmlElement boolean showHelpDialogOnStartup, showHelpDialogOnUpdate;
    @XmlElement boolean showMetaDataWarning;
    @XmlElement boolean useDefaultSaveLocation;
    @XmlElement String defaultSaveLocation;
    @XmlElement OptionFrequency checkForUpdates, reopenLastFile, reopenLastDirectory, automaticallyExport;
}
