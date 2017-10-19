package com.limbo.icedcoco;

import javafx.scene.input.KeyCombination;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "hotkeys")
public class HotkeysInfo {
    // 26 Hotkeys START!
    @XmlElement KeyCombination GlobalZoomInPrimary;
    @XmlElement KeyCombination GlobalZoomInAlt;
    @XmlElement KeyCombination GlobalZoomOutPrimary;
    @XmlElement KeyCombination GlobalZoomOutAlt;
    @XmlElement KeyCombination GlobalSaveFilePrimary;
    @XmlElement KeyCombination GlobalSaveFileAlt;
    @XmlElement KeyCombination GlobalSaveFileAsPrimary;
    @XmlElement KeyCombination GlobalSaveFileAsAlt;
    @XmlElement KeyCombination GlobalExportMaskPrimary;
    @XmlElement KeyCombination GlobalExportMaskAlt;
    @XmlElement KeyCombination ToolPolygonPrimary;
    @XmlElement KeyCombination ToolPolygonAlt;
    @XmlElement KeyCombination ToolEllipsePrimary;
    @XmlElement KeyCombination ToolEllipseAlt;
    @XmlElement KeyCombination ToolSelectPrimary;
    @XmlElement KeyCombination ToolSelectAlt;
    @XmlElement KeyCombination ToolMovePrimary;
    @XmlElement KeyCombination ToolMoveAlt;
    @XmlElement KeyCombination ActionPrimaryPrimary;
    @XmlElement KeyCombination ActionPrimaryAlt;
    @XmlElement KeyCombination ActionSecondaryPrimary;
    @XmlElement KeyCombination ActionSecondaryAlt;
    @XmlElement KeyCombination ActionModifierPrimary;
    @XmlElement KeyCombination ActionModifierAlt;
    @XmlElement KeyCombination ActionUndoPrimary;
    @XmlElement KeyCombination ActionUndoAlt;
    // 26 Hotkeys END!
}
