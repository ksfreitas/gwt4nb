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
import org.openide.options.SystemOption;
import org.openide.util.NbBundle;

/**
 *
 * @author tom
 */
public class GWTSettings extends SystemOption {

    private static final String GWT_LOCATION = "gwtLocation";   //NOI18N

    /** Creates a new instance of GWTSettings */
    public GWTSettings() {
    }

    public String displayName() {
        return NbBundle.getMessage(GWTSettings.class,"TXT_GWTSettings");
    }

    public File getGWTLocation () {
        String file = (String)this.getProperty (GWT_LOCATION);
        if (file == null) {
            return null;
        }
        return new File (file);
    }
    
    public void setGWTLocation (final File gwtLocation) {
        this.putProperty(GWT_LOCATION, gwtLocation.getAbsolutePath(),true);
    }
    
    public static GWTSettings getDefault () {
        return (GWTSettings) SystemOption.findObject (GWTSettings.class, true);
    }
    
}
