package com.limbo.icedcoco;

import javafx.scene.input.KeyCombination;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.event.ActionEvent;
import java.io.File;

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

    void save (String filepath) {
        try {
            JAXBContext jc = JAXBContext.newInstance("com.limbo.icedcoco");

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static HotkeysInfo load (String filepath) {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                JAXBContext jaxbContext = JAXBContext.newInstance("com.limbo.icedcoco");
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                HotkeysInfo hotkeysInfo = (HotkeysInfo) unmarshaller.unmarshal(file);
                return hotkeysInfo;
            }
        }
        catch (Exception ex) {
            System.err.println(ex.getStackTrace());
        }
        return null;
    }

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
