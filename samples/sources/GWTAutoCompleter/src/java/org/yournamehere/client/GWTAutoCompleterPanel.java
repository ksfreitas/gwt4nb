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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
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
public class GWTAutoCompleterPanel extends Composite {

    public TextBox searchText = new TextBox();
    public FlexTable liveResultsPanel = new FlexTable();

    public GWTAutoCompleterPanel() {

        VerticalPanel workPanel = new VerticalPanel();
        searchText.setStyleName("liveSearch-TextBox");
        searchText.addKeyboardListener(new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
                for (int i = 0; i < liveResultsPanel.getRowCount(); i++) {
                    liveResultsPanel.removeRow(i);
                }
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {

                for (int i = 0; i < liveResultsPanel.getRowCount(); i++) {
                    liveResultsPanel.removeRow(i);
                }

                if (searchText.getText().length() > 0) {
                    AsyncCallback callback = new AsyncCallback() {

                        public void onSuccess(Object result) {
                            ArrayList resultItems = (ArrayList) result;
                            int row = 0;
                            for (Iterator iter = resultItems.iterator(); iter.hasNext();) {
                                liveResultsPanel.setText(row++, 0,
                                        (String) iter.next());
                            }
                        }

                        public void onFailure(Throwable caught) {
                            Window.alert("Error invoking the live search service." + caught.getMessage());
                        }
                    };
                   getService().getCompletionItems(searchText.getText(), callback);
                }
            }
        });

        liveResultsPanel.setStyleName("liveSearch-Results");
        workPanel.add(searchText);
        workPanel.add(liveResultsPanel);
        DockPanel workPane = new DockPanel();
        workPane.add(workPanel, DockPanel.WEST);
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
        String moduleRelativeURL = GWT.getModuleBaseURL() + "gwtservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
