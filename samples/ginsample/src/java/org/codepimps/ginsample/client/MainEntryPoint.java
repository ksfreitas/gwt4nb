package org.codepimps.ginsample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.ButtonBase;
import org.codepimps.ginsample.client.gin.AppGinjector;

public class MainEntryPoint implements EntryPoint {
	private final AppGinjector injector = GWT.create(AppGinjector.class);
	
	public void onModuleLoad() {
		final Label label = new Label("Hello, GWT!!!");
		final ButtonBase button = injector.getButtonBase();

		button.setText("Click Me!");
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				label.setVisible(!label.isVisible());
			}
		});

		RootPanel.get().add(button);
		RootPanel.get().add(label);
	}
}
