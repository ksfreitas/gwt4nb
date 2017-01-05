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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.gwt4nb;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.api.java.source.ElementHandle;
import org.netbeans.api.java.source.CancellableTask;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle;

/**
 * Creates missing keys in the .properties file for a
 */
final class ConstantsCreateKeyFix implements Fix {
    private final EditableProperties ep;
    private final FileObject file;
    private final List<ElementHandle<ExecutableElement>> ehs;

    /**
     * @param file .java file for an interface implementing Constants
     * @param ehs methods in the interface
     * @param ep already read in properties
     */
    ConstantsCreateKeyFix(FileObject file,
            List<ElementHandle<ExecutableElement>> ehs, EditableProperties ep) {
        this.file = file;
        this.ehs = ehs;
        this.ep = ep;
    }

    public String getText() {
        return NbBundle.getMessage(ConstantsCreateKeyFix.class,
                "ConstantsCreateKeyFix.Text", // NOI18N
                ehs.size());
    }

    public ChangeInfo implement() throws Exception {
        JavaSource js = JavaSource.forFileObject(file);

        try {
            js.runModificationTask(new CancellableTask<WorkingCopy>() {
                public void cancel() {
                }
                public void run(WorkingCopy workingCopy) throws IOException {
                    run0(workingCopy);
                }
            }).commit();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void run0(WorkingCopy workingCopy) throws IOException {
        for (ElementHandle<ExecutableElement> eh: ehs) {
            ExecutableElement ee = eh.resolve(workingCopy);
            final String n = ee.getSimpleName().toString();
            ep.put(n, ""); // NOI18N
        }
        FileObject p =
                FileUtil.findBrother(file, "properties"); // NOI18N
        if (p != null) {
            OutputStream os = p.getOutputStream();
            try {
                ep.store(os);
            } finally {
                os.close();
            }
            DataObject do_ = DataObject.find(p);
            OpenCookie oc = do_.getCookie(OpenCookie.class);
            oc.open();
        }
    }
}

