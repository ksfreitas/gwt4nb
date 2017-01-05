package org.yournamehere.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Main entry point.
 */
public class MainEntryPoint implements EntryPoint {
    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        final TextBox text = new TextBox();
        final VerticalPanel messages = new VerticalPanel();
        final Button button = new Button("Add Message");
        
        final GWTServiceAsync s = GWT.create(GWTService.class);
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                messages.add(new Label(text.getText()));
                s.addMessage(text.getText(), new AsyncCallback<Void>() {
                    public void onFailure(Throwable caught) {
                        Window.alert("GWT service call error");
                    }
                    public void onSuccess(Void result) {
                    }
                });
            }
        });

        HorizontalPanel hp = new HorizontalPanel();
        hp.add(new Label("Message:"));
        hp.add(text);
        hp.add(button);
        RootPanel.get().add(hp);
        RootPanel.get().add(messages);

        s.getMessages(new AsyncCallback<String[]>() {
            public void onFailure(Throwable caught) {
                Window.alert("GWT service call error");
            }

            public void onSuccess(String[] result) {
                messages.clear();
                for (String s: result)
                    messages.add(new Label(s));
            }
        });
    }
}
