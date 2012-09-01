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
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;

/**
 *
 * @author gw152771
 */
public class GWTAutoFormFillerPanel extends Composite {

    public TextBox searchText = new TextBox();
    public FlexTable liveResultsPanel = new FlexTable();
    private TextBox custID = new TextBox();
    private TextBox firstName = new TextBox();
    private TextBox lastName = new TextBox();
    private TextBox address = new TextBox();
    private TextBox zip = new TextBox();
    private TextBox phone = new TextBox();
    private TextBox city = new TextBox();
    private TextBox state = new TextBox();
    private Label custIDLbl = new Label("Customer ID : ");
    private Label firstNameLbl = new Label("First Name : ");
    private Label lastNameLbl = new Label("Last Name : ");
    private Label addressLbl = new Label("Address : ");
    private Label zipLbl = new Label("Zip Code : ");
    private Label phoneLbl = new Label("Phone Number : ");
    private Label cityLbl = new Label("City : ");
    private Label stateLbl = new Label("State : ");

    public GWTAutoFormFillerPanel() {

        VerticalPanel workPanel = new VerticalPanel();

        custID.addKeyboardListener(new KeyboardListener() {

            public void onKeyDown(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyPress(Widget sender, char keyCode, int modifiers) {
            }

            public void onKeyUp(Widget sender, char keyCode, int modifiers) {
                if (custID.getText().length() > 0) {
                    AsyncCallback callback = new AsyncCallback() {

                        public void onSuccess(Object result) {
                            setValues((HashMap) result);
                        }

                        public void onFailure(Throwable caught) {
                            Window.alert("Error while calling the Auto Form Fill service." + caught.getMessage());
                        }
                    };

                    getService().getFormInfo(custID.getText(), callback);
                } else {
                    clearValues();
                }
            }
        });

        custID.addChangeListener(new ChangeListener() {

            public void onChange(Widget sender) {
                if (custID.getText().length() > 0) {
                    AsyncCallback callback = new AsyncCallback() {

                        public void onSuccess(Object result) {
                            setValues((HashMap) result);
                        }

                        public void onFailure(Throwable caught) {
                            Window.alert("Error while calling the auto form fill service." + caught.getMessage());
                        }
                    };
                    getService().getFormInfo(custID.getText(), callback);
                } else {
                    clearValues();
                }

            }
        });

        HorizontalPanel itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        custIDLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(custIDLbl);
        custID.setStyleName("autoFormItem-Textbox");
        itemPanel.add(custID);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        firstNameLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(firstNameLbl);
        firstName.setStyleName("autoFormItem-Textbox");
        itemPanel.add(firstName);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        lastNameLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(lastNameLbl);
        lastName.setStyleName("autoFormItem-Textbox");
        itemPanel.add(lastName);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        addressLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(addressLbl);
        address.setStyleName("autoFormItem-Textbox");
        itemPanel.add(address);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        cityLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(cityLbl);
        city.setStyleName("autoFormItem-Textbox");
        itemPanel.add(city);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        stateLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(stateLbl);
        state.setStyleName("autoFormItem-Textbox");
        itemPanel.add(state);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        zipLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(zipLbl);
        zip.setStyleName("autoFormItem-Textbox");
        itemPanel.add(zip);
        workPanel.add(itemPanel);
        itemPanel = new HorizontalPanel();
        itemPanel.setStyleName("autoFormItem-Panel");
        phoneLbl.setStyleName("autoFormItem-Label");
        itemPanel.add(phoneLbl);
        phone.setStyleName("autoFormItem-Textbox");
        itemPanel.add(phone);
        workPanel.add(itemPanel);

        DockPanel workPane = new DockPanel();
        workPane.add(workPanel, DockPanel.CENTER);
        workPane.setCellHeight(workPanel, "100%");
        workPane.setCellWidth(workPanel, "100%");

        initWidget(workPane);
    }

    private void setValues(HashMap values) {
        if (values.size() > 0) {
            firstName.setText((String) values.get("first name"));
            lastName.setText((String) values.get("last name"));
            address.setText((String) values.get("address"));
            city.setText((String) values.get("city"));
            state.setText((String) values.get("state"));
            zip.setText((String) values.get("zip"));
            phone.setText((String) values.get("phone"));
        } else {
            clearValues();
        }
    }

    private void clearValues() {
        firstName.setText(" ");
        lastName.setText(" ");
        address.setText(" ");
        city.setText(" ");
        state.setText(" ");
        zip.setText(" ");
        phone.setText(" ");
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
