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
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb;

import java.util.Collections;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.spi.editor.codegen.CodeGenerator;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

/**
 * Adds an iframe definition for GWT history.
 */
public class HistoryFrameCodeGenerator implements CodeGenerator {
    private JTextComponent textComp;

    /**
     * @param textComp text component
     */
    private HistoryFrameCodeGenerator(JTextComponent textComp) {
        this.textComp = textComp;
    }

    public static class Factory implements CodeGenerator.Factory {
        public List<? extends CodeGenerator> create(Lookup context) {
            JTextComponent textComp = context.lookup(JTextComponent.class);
            List<? extends CodeGenerator> r = null;
            FileObject fo =
                    NbEditorUtilities.getFileObject(textComp.getDocument());
            if (fo != null) {
                Project project = FileOwnerQuery.getOwner(fo);
                if (project != null) {
                    GWTProjectInfo pi =
                            GWTProjectInfo.get(project);
                    if (pi != null) {
                        List<String> m = pi.getModules();
                        if (m.size() > 0)
                            r = Collections.singletonList(
                                    new HistoryFrameCodeGenerator(textComp));
                    }
                }
            }
            if (r == null)
                r = Collections.emptyList();
            return r;
        }
    }

    public String getDisplayName() {
        return org.openide.util.NbBundle.getMessage(
                HistoryFrameCodeGenerator.class, "insHistFrame"); // NOI18N
    }

    public void invoke() {
        Document doc = textComp.getDocument();
        try {
            doc.insertString(textComp.getCaretPosition(),
                    "<!-- GWT history support -->\n" + // NOI18N
                    "<iframe src=\"javascript:''\" " + // NOI18N
                    "id=\"__gwt_historyFrame\" tabIndex='-1' " + // NOI18N
                    "style=\"position:absolute;width:0;height:0;border:0\">" + // NOI18N
                    "</iframe>\n", // NOI18N
                    null);
        } catch (BadLocationException ex) {
            GWT4NBUtil.unexpectedException(ex);
        }
    }
}
