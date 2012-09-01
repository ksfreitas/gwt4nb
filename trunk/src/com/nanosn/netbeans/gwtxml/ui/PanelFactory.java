/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 */
package com.nanosn.netbeans.gwtxml.ui;

import com.nanosn.netbeans.gwtxml.GwtXmlDataObject;
import com.nanosn.netbeans.gwtxml.ui.gwtmodule.CompilerPanel;
import com.nanosn.netbeans.gwtxml.ui.gwtmodule.ModulePanel;
import com.nanosn.netbeans.gwtxml.ui.gwtmodule.NotYetImplementedPanel;
import org.netbeans.modules.xml.multiview.ui.InnerPanelFactory;
import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.ui.ToolBarDesignEditor;

/**
 *
 * @author selkhateeb
 */
public class PanelFactory implements InnerPanelFactory {

    private GwtXmlDataObject dObj;
    ToolBarDesignEditor editor;

    public PanelFactory(ToolBarDesignEditor editor, GwtXmlDataObject dObj) {
        this.dObj = dObj;
        this.editor = editor;
    }

    public SectionInnerPanel createInnerPanel(Object key) {

        SectionInnerPanel sectionView = null;
        if (key instanceof PanelType) {
            PanelType panelType = (PanelType) key;
            switch (panelType) {
                case ModulePanel:
                    sectionView = new ModulePanel((SectionView) editor.getContentView(), dObj);
                    break;

                case CompilerPanel:
                    sectionView = new CompilerPanel((SectionView) editor.getContentView(), dObj);
                    break;

                default:
                    sectionView = new NotYetImplementedPanel();
            }

        }

        return sectionView;
    }
}
