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
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author gw152771
 */
public class GWTDynamicListPanel extends Composite {

    public TextBox searchText = new TextBox();
    public FlexTable liveResultsPanel = new FlexTable();
    
    Grid manufacturers = new Grid(5, 1);
    Grid brands = new Grid(5, 1);
    Grid models = new Grid(5, 1);
    int selectedManufacturer = 0;

    public GWTDynamicListPanel() {

        HorizontalPanel workPanel = new HorizontalPanel();

        manufacturers.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row,
                    int cell) {
                clearSelections(manufacturers, false);
                clearSelections(brands, true);
                clearSelections(models, true);
                selectedManufacturer = row;
                manufacturers.getCellFormatter().setStyleName(row, cell,
                        "dynamicLists-Selected");
                AsyncCallback callback = new AsyncCallback() {

                    public void onSuccess(Object result) {
                        brands.clear();
                        int row = 0;
                        for (Iterator iter = ((ArrayList) result).iterator(); iter.hasNext();) {
                            brands.setText(row++, 0, (String) iter.next());
                        }
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Error calling the Dynamic Lists service to get the brands." + caught.getMessage());
                    }
                };
                getService().getBrands(manufacturers.getText(row, cell),
                        callback);
            }
        });

        brands.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row,
                    int cell) {
                clearSelections(brands, false);
                clearSelections(models, true);
                brands.getCellFormatter().setStyleName(row, cell,
                        "dynamicLists-Selected");
                AsyncCallback callback = new AsyncCallback() {

                    public void onSuccess(Object result) {
                        models.clear();
                        int row = 0;
                        for (Iterator iter = ((ArrayList) result).iterator(); iter.hasNext();) {
                            models.setText(row++, 0, (String) iter.next());
                        }
                    }

                    public void onFailure(Throwable caught) {
                        Window.alert("Error calling the Dynamic Lists service to get the models." + caught.getMessage());
                    }
                };
                getService().getModels(manufacturers.getText(
                        selectedManufacturer, cell), brands.getText(row, cell),
                        callback);

            }
        });

        models.addTableListener(new TableListener() {

            public void onCellClicked(SourcesTableEvents sender, int row,
                    int cell) {
                clearSelections(models, false);
                models.getCellFormatter().setStyleName(row, cell,
                        "dynamicLists-Selected");
            }
        });

        VerticalPanel itemPanel = new VerticalPanel();
        Label itemLabel = new Label("Select Manufacturer");
        itemLabel.setStyleName("dynamicLists-Label");
        itemPanel.add(itemLabel);
        itemPanel.add(manufacturers);
        workPanel.add(itemPanel);

        itemPanel = new VerticalPanel();
        itemLabel = new Label("Select Brand");
        itemLabel.setStyleName("dynamicLists-Label");
        itemPanel.add(itemLabel);
        itemPanel.add(brands);
        workPanel.add(itemPanel);

        itemPanel = new VerticalPanel();
        itemLabel = new Label("Models");
        itemLabel.setStyleName("dynamicLists-Label");
        itemPanel.add(itemLabel);
        itemPanel.add(models);
        workPanel.add(itemPanel);

        manufacturers.setStyleName("dynamicLists-List");
        brands.setStyleName("dynamicLists-List");
        models.setStyleName("dynamicLists-List");

        workPanel.setStyleName("dynamicLists-Panel");
        AsyncCallback callback = new AsyncCallback() {

            public void onSuccess(Object result) {
                int row = 0;
                for (Iterator iter = ((ArrayList) result).iterator(); iter.hasNext();) {
                    manufacturers.setText(row++, 0, (String) iter.next());
                }
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error calling the Dynamic Lists service to get the manufacturers." + caught.getMessage());
            }
        };
        getService().getManufacturers(callback);

        DockPanel workPane = new DockPanel();
        workPane.add(workPanel, DockPanel.CENTER);
        workPane.setCellHeight(workPanel, "100%");
        workPane.setCellWidth(workPanel, "100%");
        initWidget(workPane);
    }

    public void clearSelections(Grid grid, boolean clearData) {
        for (int i = 0; i < grid.getRowCount(); i++) {
            if (clearData) {
                grid.setText(i, 0, " ");
            }
            grid.getCellFormatter().setStyleName(i, 0,
                    "dynamicLists-Unselected");
        }
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
