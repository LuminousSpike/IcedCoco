package com.limbo.icedcoco;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;

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

    void save (String filepath) {
        try {
            File file = new File(filepath);
            if (!file.exists())
                file.createNewFile();

            JAXBContext jc = JAXBContext.newInstance("com.limbo.icedcoco");

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static SettingsInfo load (String filepath) throws Exception, IOException, JAXBException, UnmarshalException {
            File file = new File(filepath);
            if (file.exists()) {
                JAXBContext jaxbContext = JAXBContext.newInstance("com.limbo.icedcoco");
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (SettingsInfo) unmarshaller.unmarshal(file);
            }
        return null;
    }

    void loadDefault () {
        defaultSaveLocation = "/How/Are/You/";
        showHelpDialogOnStartup = false;
        showHelpDialogOnUpdate = true;
        showMetaDataWarning = true;
        checkForUpdates = SettingsInfo.OptionFrequency.Always;
        reopenLastFile = SettingsInfo.OptionFrequency.Ask;
        reopenLastDirectory = SettingsInfo.OptionFrequency.Ask;
        automaticallyExport = SettingsInfo.OptionFrequency.Never;
    }
}
