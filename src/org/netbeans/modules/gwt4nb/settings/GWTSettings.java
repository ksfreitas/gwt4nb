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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb.settings;

import java.io.File;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;

/**
 * Settings.
 * 
 * @author tom
 * @author see https://github.com/gwt4nb/gwt4nb/
 */
public class GWTSettings {
    private static final String GWT_LOCATION = "gwtLocation";   // NOI18N

    private final static Preferences preferencesNode =
            NbPreferences.forModule(GWTSettings.class);

    /** Creates a new instance of GWTSettings */
    private GWTSettings() {
    }

    /**
     * @return the default GWT location or null if unknown
     */
    public static File getGWTLocation() {
        final String file = preferencesNode.get(GWT_LOCATION, null);
        return file != null ? new File(file) : null;
    }

    public static void setGWTLocation(final File gwtLocation) {
        preferencesNode.put(GWT_LOCATION, gwtLocation.getAbsolutePath());
    }
}
