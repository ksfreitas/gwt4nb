/*
 * MainEntryPoint.java
 *
 * Created on December 11, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gw152771
 */
public class GWTPasswordStrengthCheckerPanel extends Composite {

    public TextBox passwordText = new TextBox();
    public ArrayList strength = new ArrayList();

    private void clearStrengthPanel() {
        for (Iterator iter = strength.iterator(); iter.hasNext();) {
            ((CheckBox) iter.next()).setStyleName(getPasswordStrengthStyle(0));
        }
    }

    private String getPasswordStrengthStyle(int passwordStrength) {
        if (passwordStrength == 3) {
            return "pwStrength-Weak";
        } else if (passwordStrength == 6) {
            return "pwStrength-Medium";
        } else if (passwordStrength == 9) {
            return "pwStrength-Strong";
        } else {
            return "";
        }
    }

    public GWTPasswordStrengthCheckerPanel() {

        HorizontalPanel strengthPanel = new HorizontalPanel();
        strengthPanel.setStyleName("pwStrength-Panel");
        for (int i = 0; i < 9; i++) {
            CheckBox singleBox = new CheckBox();
            strengthPanel.add(singleBox);
            strength.add(singleBox);
        }
        VerticalPanel workPanel = new VerticalPanel();
        passwordText.setStyleName("pwStrength-Textbox");
        passwordText.addKeyboardListener(new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {

                if (passwordText.getText().length() > 0) {
                    AsyncCallback callback = new AsyncCallback() {

                        public void onSuccess(Object result) {
                            clearStrengthPanel();
                            int checkedStrength = ((Integer) result).intValue();
                            for (int i = 0; i < checkedStrength; i++) {
                                ((CheckBox) strength.get(i)).setStyleName(getPasswordStrengthStyle(checkedStrength));
                            }
                        }

                        public void onFailure(Throwable caught) {
                            Window.alert("Error invoking the live search service." + caught.getMessage());
                        }
                    };
                    //  getService().getCompletionItems(passwordText.getText(), callback);
                    getService().checkStrength(passwordText.getText(), callback);
                }
                else {
                    clearStrengthPanel();
                }
            }
        });
        
        workPanel.add(passwordText);
        workPanel.add(strengthPanel);
        
        DockPanel workPane = new DockPanel();
        workPane.add(workPanel, DockPanel.CENTER);
        workPane.setCellHeight(workPanel, "100%");
        workPane.setCellWidth(workPanel, "100%");

        initWidget(workPane);
    }

    public static GWTServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        GWTServiceAsync service = (GWTServiceAsync) GWT.create(GWTService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "sampleservice/gwtservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
