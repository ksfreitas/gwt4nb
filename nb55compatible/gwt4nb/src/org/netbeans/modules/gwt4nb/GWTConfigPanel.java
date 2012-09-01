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

import java.awt.Component;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.gwt4nb.settings.GWTSettings;
import org.netbeans.modules.web.spi.webmodule.FrameworkConfigurationPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author tomslot
 */
public class GWTConfigPanel implements FrameworkConfigurationPanel, WizardDescriptor.FinishablePanel, WizardDescriptor.ValidatingPanel {
    private GWTConfigPanelVisual pnlVisual;
    private final Set <ChangeListener> listeners = new HashSet(1);
    private WizardDescriptor wizardDescriptor;
    
    /** Creates a new instance of GWTConfigPanel */
    public GWTConfigPanel() {
        pnlVisual = new GWTConfigPanelVisual(this);
        GWTSettings gwtSettings = GWTSettings.getDefault();
        File defaultGWTFolder = gwtSettings.getGWTLocation();
        
        if (defaultGWTFolder != null){
            pnlVisual.setGWTFolder(defaultGWTFolder);
        }
    }
    
    public void enableComponents(boolean enabled) {
        pnlVisual.enableComponents(enabled);
    }
    
    public Component getComponent() {
        return pnlVisual;
    }
    
    public HelpCtx getHelp() {
        return null;
    }
    
    public void readSettings(Object settings) {
        wizardDescriptor = (WizardDescriptor) settings;
    }
    
    public void storeSettings(Object settings) {
        
    }
    
    private void setErrorMessage(String message){
        if (wizardDescriptor != null){
            wizardDescriptor.putProperty("WizardPanel_errorMessage",message);
        }
    }
    
    public boolean isValid() {
        File gwtFolder = getGwtFolder();
        
        if (!(gwtFolder.exists() && gwtFolder.isDirectory()
                && new File(gwtFolder, "gwt-user.jar").exists())){
            
            setErrorMessage(NbBundle.getMessage(GWTConfigPanel.class, "ERROR_Invalid_GWT_Folder"));
            return false;
        }
        
        boolean validModule = true;
        String parts[] = getGWTModule().split("\\.");
        
        if (parts.length < 2 || getGWTModule().endsWith(".")){
            validModule = false;
        } else{
            for (String part : parts){
                if (!Util.isValidJavaIdentifier(part)){
                    validModule = false;
                    break;
                }
            }
        }
        
        if (!validModule){
            setErrorMessage(NbBundle.getMessage(GWTConfigPanel.class, "ERROR_Invalid_GWT_Module_Name"));
            return false;
        }
        
        setErrorMessage(""); //NOI18N
        return true;
    }
       
    public boolean isFinishPanel() {
        return true;
    }
    
    public void validate() throws WizardValidationException {
        
    }
    
    public File getGwtFolder(){
        return pnlVisual.getGWTFolder();
    }
    
    public String getGWTModule(){
        return pnlVisual.getGWTModule();
    }
    
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }
    protected final void fireChangeEvent() {
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }
}
