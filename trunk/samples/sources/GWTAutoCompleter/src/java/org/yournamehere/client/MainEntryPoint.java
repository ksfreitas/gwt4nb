package org.yournamehere.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainEntryPoint implements EntryPoint {

    private DockPanel panel = new DockPanel();
    private DockPanel panelContainer;

    public void onModuleLoad() {
        panelContainer = new DockPanel();
        panelContainer.setStyleName("ks-Panel");
        
        //Create vertical panel:
        VerticalPanel vp = new VerticalPanel();
        vp.setWidth("100%");
        //Add the panel container to the vertical panel:
        vp.add(panelContainer);
        
        //Add the vertical panel to the dock panel:
        panel.add(vp, DockPanel.WEST);

        //Add the dock panel to the root panel:
        RootPanel.get().add(panel);
        
        
        //My own panel:
        GWTAutoCompleterPanel info = new GWTAutoCompleterPanel();
        //Add it to the panel conatiner:
        panelContainer.add(info, DockPanel.WEST);
    }
}
