package com.limbo.icedcoco;

import javafx.scene.input.KeyCombination;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Wrapper class for the hotkeys.
 */
@XmlRootElement(name = "hotkeys")
public class HotkeysInfo {
    // 26 Hotkeys START!
    KeyCombination GlobalZoomInPrimary;
    KeyCombination GlobalZoomInAlt;
    KeyCombination GlobalZoomOutPrimary;
    KeyCombination GlobalZoomOutAlt;
    KeyCombination GlobalSaveFilePrimary;
    KeyCombination GlobalSaveFileAlt;
    KeyCombination GlobalSaveFileAsPrimary;
    KeyCombination GlobalSaveFileAsAlt;
    KeyCombination GlobalExportMaskPrimary;
    KeyCombination GlobalExportMaskAlt;
    KeyCombination ToolPolygonPrimary;
    KeyCombination ToolPolygonAlt;
    KeyCombination ToolEllipsePrimary;
    KeyCombination ToolEllipseAlt;
    KeyCombination ToolSelectPrimary;
    KeyCombination ToolSelectAlt;
    KeyCombination ToolMovePrimary;
    KeyCombination ToolMoveAlt;
    KeyCombination ActionPrimaryPrimary;
    KeyCombination ActionPrimaryAlt;
    KeyCombination ActionSecondaryPrimary;
    KeyCombination ActionSecondaryAlt;
    KeyCombination ActionModifierPrimary;
    KeyCombination ActionModifierAlt;
    KeyCombination ActionUndoPrimary;
    KeyCombination ActionUndoAlt;
    // 26 Hotkeys END!

    // 26 Hotkeys Getters START!
    @XmlElement String getGlobalZoomInPrimary() { return GlobalZoomInPrimary.toString(); };
    @XmlElement String getGlobalZoomInAlt() { return GlobalZoomInAlt.toString(); };
    @XmlElement String getGlobalZoomOutPrimary() { return  GlobalZoomOutPrimary.toString(); };
    @XmlElement String getGlobalZoomOutAlt() { return GlobalZoomOutAlt.toString(); };
    @XmlElement String getGlobalSaveFilePrimary() { return GlobalSaveFilePrimary.toString();};
    @XmlElement String getGlobalSaveFileAlt() { return GlobalSaveFileAlt.toString(); };
    @XmlElement String getGlobalSaveFileAsPrimary() { return GlobalSaveFileAsPrimary.toString(); };
    @XmlElement String getGlobalSaveFileAsAlt() { return GlobalSaveFileAsAlt.toString(); };
    @XmlElement String getGlobalExportMaskPrimary() {return  GlobalExportMaskPrimary.toString(); };
    @XmlElement String getGlobalExportMaskAlt() { return  GlobalExportMaskAlt.toString(); };
    @XmlElement String getToolPolygonPrimary() { return ToolPolygonPrimary.toString(); };
    @XmlElement String getToolPolygonAlt() { return ToolPolygonAlt.toString(); };
    @XmlElement String getToolEllipsePrimary() { return ToolEllipsePrimary.toString(); };
    @XmlElement String getToolEllipseAlt() { return ToolEllipseAlt.toString(); };
    @XmlElement String getToolSelectPrimary() { return ToolSelectPrimary.toString(); };
    @XmlElement String getToolSelectAlt() { return ToolSelectAlt.toString(); };
    @XmlElement String getToolMovePrimary() { return ToolMovePrimary.toString(); };
    @XmlElement String getToolMoveAlt() { return ToolMoveAlt.toString(); };
    @XmlElement String getActionPrimaryPrimary() { return ActionPrimaryPrimary.toString(); };
    @XmlElement String getActionPrimaryAlt() { return ActionPrimaryAlt.toString(); };
    @XmlElement String getActionSecondaryPrimary() { return ActionSecondaryPrimary.toString(); };
    @XmlElement String getActionSecondaryAlt() { return ActionSecondaryAlt.toString(); };
    @XmlElement String getActionModifierPrimary() { return ActionModifierPrimary.toString(); };
    @XmlElement String getActionModifierAlt() { return ActionModifierAlt.toString(); };
    @XmlElement String getActionUndoPrimary() { return ActionUndoPrimary.toString(); };
    @XmlElement String getActionUndoAlt() { return  ActionUndoAlt.toString(); };
    // 26 Hotkeys Getters END!

    // 26 Hotkeys setters START!
    void setGlobalZoomInPrimary(String combination) { GlobalZoomInPrimary = KeyCombination.keyCombination(combination); }
    void setGlobalZoomInAlt(String combination) { GlobalZoomInAlt = KeyCombination.keyCombination(combination); }
    void setGlobalZoomOutPrimary(String combination) { GlobalZoomOutPrimary = KeyCombination.keyCombination(combination); }
    void setGlobalZoomOutAlt(String combination) { GlobalZoomOutAlt = KeyCombination.keyCombination(combination); }
    void setGlobalSaveFilePrimary(String combination) { GlobalSaveFilePrimary = KeyCombination.keyCombination(combination); }
    void setGlobalSaveFileAlt(String combination) { GlobalSaveFileAlt = KeyCombination.keyCombination(combination); }
    void setGlobalSaveFileAsPrimary(String combination) { GlobalSaveFileAsPrimary = KeyCombination.keyCombination(combination); }
    void setGlobalSaveFileAsAlt(String combination) { GlobalSaveFileAsAlt = KeyCombination.keyCombination(combination); }
    void setGlobalExportMaskPrimary(String combination) {GlobalExportMaskPrimary = KeyCombination.keyCombination(combination); }
    void setGlobalExportMaskAlt(String combination) { GlobalExportMaskAlt = KeyCombination.keyCombination(combination); }
    void setToolPolygonPrimary(String combination) { ToolPolygonPrimary = KeyCombination.keyCombination(combination); }
    void setToolPolygonAlt(String combination) { ToolPolygonAlt = KeyCombination.keyCombination(combination); }
    void setToolEllipsePrimary(String combination) { ToolEllipsePrimary = KeyCombination.keyCombination(combination); }
    void setToolEllipseAlt(String combination) { ToolEllipseAlt = KeyCombination.keyCombination(combination); }
    void setToolSelectPrimary(String combination) { ToolSelectPrimary = KeyCombination.keyCombination(combination); }
    void setToolSelectAlt(String combination) { ToolSelectAlt = KeyCombination.keyCombination(combination); }
    void setToolMovePrimary(String combination) { ToolMovePrimary = KeyCombination.keyCombination(combination); }
    void setToolMoveAlt(String combination) { ToolMoveAlt = KeyCombination.keyCombination(combination); }
    void setActionPrimaryPrimary(String combination) { ActionPrimaryPrimary = KeyCombination.keyCombination(combination); }
    void setActionPrimaryAlt(String combination) { ActionPrimaryAlt = KeyCombination.keyCombination(combination); }
    void setActionSecondaryPrimary(String combination) { ActionSecondaryPrimary = KeyCombination.keyCombination(combination); }
    void setActionSecondaryAlt(String combination) { ActionSecondaryAlt = KeyCombination.keyCombination(combination); }
    void setActionModifierPrimary(String combination) { ActionModifierPrimary = KeyCombination.keyCombination(combination); }
    void setActionModifierAlt(String combination) { ActionModifierAlt = KeyCombination.keyCombination(combination); }
    void setActionUndoPrimary(String combination) { ActionUndoPrimary = KeyCombination.keyCombination(combination); }
    void setActionUndoAlt(String combination) { ActionUndoAlt = KeyCombination.keyCombination(combination); }
    // 26 Hotkeys setters END!

    /**
     * Writes out the class to xml using jaxb.
     * @param filepath Path of the file to write.
     */
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

    /**
     * Create a new HotkeysInfo from a xml file.
     * @param filepath Path of the file to load.
     * @return The loaded HotkeysInfo object.
     * @throws Exception Any general error.
     * @throws IOException Any IO related error.
     * @throws JAXBException Any JAXB related error.
     * @throws UnmarshalException Any Unmarshal related error.
     */
    static HotkeysInfo load (String filepath) throws Exception, IOException, JAXBException, UnmarshalException {
            File file = new File(filepath);
            if (file.exists()) {
                JAXBContext jaxbContext = JAXBContext.newInstance("com.limbo.icedcoco");
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (HotkeysInfo) unmarshaller.unmarshal(file);
            }
        return null;
    }

    /**
     * Hardcoded default key bindings for the hotkeys.
     */
    void loadDefault () {
        GlobalZoomInPrimary = KeyCombination.keyCombination("SHORTCUT+N");
        GlobalZoomInAlt = KeyCombination.keyCombination("SHORTCUT+N");
        GlobalZoomOutPrimary = KeyCombination.keyCombination("SHORTCUT+M");
        GlobalZoomOutAlt = KeyCombination.keyCombination("SHORTCUT+M");
        GlobalSaveFilePrimary = KeyCombination.keyCombination("SHORTCUT+S");
        GlobalSaveFileAlt = KeyCombination.keyCombination("SHORTCUT+S");
        GlobalSaveFileAsPrimary = KeyCombination.keyCombination("SHORTCUT+SHIFT+S");
        GlobalSaveFileAsAlt = KeyCombination.keyCombination("SHORTCUT+SHIFT+S");
        GlobalExportMaskPrimary = KeyCombination.keyCombination("SHORTCUT+X");
        GlobalExportMaskAlt = KeyCombination.keyCombination("SHORTCUT+X");
        ToolPolygonPrimary = KeyCombination.keyCombination("P");
        ToolPolygonAlt = KeyCombination.keyCombination("P");
        ToolEllipsePrimary = KeyCombination.keyCombination("E");
        ToolEllipseAlt = KeyCombination.keyCombination("E");
        ToolSelectPrimary = KeyCombination.keyCombination("S");
        ToolSelectAlt = KeyCombination.keyCombination("S");
        ToolMovePrimary = KeyCombination.keyCombination("M");
        ToolMoveAlt = KeyCombination.keyCombination("M");
        ActionPrimaryPrimary = KeyCombination.keyCombination("1");
        ActionPrimaryAlt = KeyCombination.keyCombination("1");
        ActionSecondaryPrimary = KeyCombination.keyCombination("2");
        ActionSecondaryAlt = KeyCombination.keyCombination("2");
        ActionModifierPrimary = KeyCombination.keyCombination("3");
        ActionModifierAlt = KeyCombination.keyCombination("3");
        ActionUndoPrimary = KeyCombination.keyCombination("SHORTCUT+Z");
        ActionUndoAlt = KeyCombination.keyCombination("SHORTCUT+Z");
    }
}
