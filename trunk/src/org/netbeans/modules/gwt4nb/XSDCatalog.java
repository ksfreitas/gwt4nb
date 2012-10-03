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

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.netbeans.modules.xml.catalog.spi.CatalogDescriptor;
import org.netbeans.modules.xml.catalog.spi.CatalogListener;
import org.netbeans.modules.xml.catalog.spi.CatalogReader;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Register Schema files to runtime tab. See
 * http://blogs.sun.com/vivek/entry/schema_based_code_completion_tutorial
 */
public class XSDCatalog implements CatalogReader, CatalogDescriptor,
        EntityResolver {

    private static final String UI_PUBLIC_ID =
            "urn:import:com.google.gwt.user.client.ui"; // NOI18N
    private static final String UI_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/" + // NOI18N
            "com.google.gwt.user.client.ui.xsd"; // NOI18N
    private static final String UI_BINDER_PUBLIC_ID =
            "urn:ui:com.google.gwt.uibinder"; // NOI18N
    private static final String UI_BINDER_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/" + // NOI18N
            "UiBinder.xsd"; // NOI18N
    private static final String XHTML_SYSTEM_ID =
            "http://dl.google.com/gwt/DTD/xhtml.ent"; // NOI18N
    private static final String XHTML_URL =
            "nbres:/org/netbeans/modules/gwt4nb/resources/xhtml.ent"; // NOI18N
    private static final String UI_IMPORT_PACKAGE =
            "urn:import:"; // NOI18N

    @Override
    public Iterator<String> getPublicIDs() {
        List<String> list = new ArrayList<String>();
        list.add(UI_PUBLIC_ID);
        list.add(UI_BINDER_PUBLIC_ID);

        return list.listIterator();
    }

    @Override
    public void refresh() {
    }

    @Override
    public String getSystemID(String publicId) {
        if (publicId.equals(UI_PUBLIC_ID)) {
            return UI_URL;
        } else if (publicId.equals(UI_BINDER_PUBLIC_ID)) {
            return UI_BINDER_URL;
        } else {
            return null;
        }
    }

    @Override
    public String resolveURI(String name) {
        return null;
    }

    @Override
    public String resolvePublic(String string) {
        return null;
    }

    @Override
    public void addCatalogListener(CatalogListener catalogListener) {
    }

    @Override
    public void removeCatalogListener(CatalogListener catalogListener) {
    }

    @Override
    public Image getIcon(int i) {
        return ImageUtilities.loadImage(
                "org/netbeans/modules/gwt4nb/gwticon.png"); // NOI18N
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(XSDCatalog.class, "GWTCat"); // NOI18N
    }

    @Override
    public String getShortDescription() {
        return NbBundle.getMessage(XSDCatalog.class, "XMLCat"); // NOI18N
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        if (UI_PUBLIC_ID.equals(publicId)) {
            return new org.xml.sax.InputSource(UI_URL);
        }
        if (systemId != null && systemId.contains(UI_PUBLIC_ID)) {
            return new org.xml.sax.InputSource(UI_URL);
        }
        if (systemId != null && systemId.contains(UI_BINDER_PUBLIC_ID)) {
            return new org.xml.sax.InputSource(UI_BINDER_URL);
        }
        if (XHTML_SYSTEM_ID.equals(systemId)) {
            return new org.xml.sax.InputSource(XHTML_URL);
        }

        if (systemId != null && systemId.contains(UI_IMPORT_PACKAGE)) {
            File f = ProjectPackageCatalog.createXSD(systemId);
            if (f != null) {
                return new org.xml.sax.InputSource("file:" + f.getPath());
            }
        }

        return null;
    }
}
