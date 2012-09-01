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
package com.nanosn.netbeans.gwtxml;

import com.nanosn.netbeans.gwtxml.gwtmodule.Module;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import org.netbeans.api.xml.cookies.CheckXMLCookie;
import org.netbeans.api.xml.cookies.ValidateXMLCookie;
import org.netbeans.modules.schema2beans.Schema2BeansException;
import org.netbeans.modules.xml.multiview.DesignMultiViewDesc;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataObject;
import org.netbeans.modules.xml.multiview.XmlMultiViewDataSynchronizer;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.ErrorManager;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiFileLoader;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.xml.sax.InputSource;

public class GwtXmlDataObject extends XmlMultiViewDataObject {
    private static final long serialVersionUID = 1;

    private ModelSynchronizer modelSynchronizer;
    Module module;

    public GwtXmlDataObject(FileObject pf, 
            MultiFileLoader loader) throws DataObjectExistsException,
            IOException {
        super(pf, loader);
        modelSynchronizer = new ModelSynchronizer(this);
        InputSource in = DataObjectAdapters.inputSource(this);
        CheckXMLCookie checkCookie = new CheckXMLSupport(in);

        CookieSet cookies = getCookieSet();
        cookies.add(checkCookie);
        ValidateXMLCookie validateCookie = new ValidateXMLSupport(in);
        cookies.add(validateCookie);
        try {
            parseDocument();
        }
        catch (IOException ex) {
            System.out.println("ex=" + ex); // NOI18N
        }
    }

    @Override
    protected Node createNodeDelegate() {
        //TODO
        return new GwtModuleDataNode(this);
    }

    private void parseDocument() throws IOException {
        if (module == null) {
            module = getModule();
        }
        else {
            java.io.InputStream is = getEditorSupport().getInputStream();
            Module newModule = null;
            try {
                newModule = Module.createGraph(is);
            }
            catch (RuntimeException ex) {
                System.out.println("runtime error " + ex); // NOI18N
            }
            if (newModule != null) {
                module.merge(newModule,
                        org.netbeans.modules.schema2beans.BaseBean.MERGE_UPDATE);
            }
        }
    }

    public Module getModule() throws IOException {
        if (module == null) {
            final FileObject fo = getPrimaryFile();
            InputStream is = fo.getInputStream();
            try {
                module = Module.createGraph(is, false);
            } finally {
                is.close();
            }
        }
        return module;
    }

    @Override
    protected DesignMultiViewDesc[] getMultiViewDesc() {
        return new DesignMultiViewDesc[]{
                    new GeneralView(this), // new CompilerView(this)
                };
    }

    // <editor-fold desc="DesignView Class">
    private static class GeneralView extends DesignMultiViewDesc {
        private static final long serialVersionUID = 1;

        GeneralView(GwtXmlDataObject dObj) {
            super(dObj, NbBundle.getMessage(
                    GwtXmlDataObject.class, "GENERAL")); // NOI18N
        }

        public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
            GwtXmlDataObject dObj = (GwtXmlDataObject) getDataObject();
//            if (type==TYPE_TOOLBAR) return new BookToolBarMVElement(dObj);
//            else return new BookTreePanelMVElement(dObj);
            return new GwtxmlToolBarMultiViewElement(dObj);
        }

        public java.awt.Image getIcon() {
            return org.openide.util.ImageUtilities.loadImage("org/netbeans/modules/gwt4nb/gwticon.png"); // NOI18N
        }

        public String preferredID() {
            return "gwtxml_multiview_design"; // NOI18N
        }
    }

    private static class CompilerView extends DesignMultiViewDesc {
        private static final long serialVersionUID = 1;

        CompilerView(GwtXmlDataObject dObj) {
            super(dObj, NbBundle.getMessage(
                    GwtXmlDataObject.class, "COMPILER")); // NOI18N
        }

        public org.netbeans.core.spi.multiview.MultiViewElement createElement() {
            GwtXmlDataObject dObj = (GwtXmlDataObject) getDataObject();
//            if (type==TYPE_TOOLBAR) return new BookToolBarMVElement(dObj);
//            else return new BookTreePanelMVElement(dObj);
            return new GwtxmlToolBarMultiViewElement(dObj);
        }

        public java.awt.Image getIcon() {
            return org.openide.util.ImageUtilities.loadImage(
                    "org/netbeans/modules/gwt4nb/gwticon.png"); // NOI18N
        }

        public String preferredID() {
            return "gwtxml_multiview_compiler"; // NOI18N
        }
    }
    // </editor-fold>

    public void modelUpdatedFromUI() {
        modelSynchronizer.requestUpdateData();
    }

    @Override
    protected String getPrefixMark() {
        return null;
    }

    // <editor-fold desc="Model Synchronizer Class">
    private class ModelSynchronizer extends XmlMultiViewDataSynchronizer {
        private static final long serialVersionUID = 1;

        public ModelSynchronizer(XmlMultiViewDataObject dataObject) {
            super(dataObject, 500);
        }

        protected boolean mayUpdateData(boolean allowDialog) {
            return true;
        }

        protected void updateDataFromModel(Object model, org.openide.filesystems.FileLock lock, boolean modify) {
            if (model == null) {
                return;
            }
            try {
                Writer out = new StringWriter();
                ((Module) model).write(out);
                out.close();
                getDataCache().setData(lock, out.toString(), modify);
            }
            catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
            catch (Schema2BeansException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }

        protected Object getModel() {
            try {
                return getModule();
            }
            catch (IOException e) {
                ErrorManager.getDefault().notify(org.openide.ErrorManager.INFORMATIONAL, e);
                return null;
            }
        }

        protected void reloadModelFromData() {
            try {
                parseDocument();
            }
            catch (IOException e) {
                ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, e);
            }
        }
    }
    // </editor-fold>
}
