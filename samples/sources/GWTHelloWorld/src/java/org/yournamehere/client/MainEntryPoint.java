/*
 * MainEntryPoint.java
 *
 * Created on December 10, 2007, 7:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author gw152771
 */
public class MainEntryPoint implements EntryPoint {

    /** Creates a new instance of MainEntryPoint */
    public MainEntryPoint() {
    }

    /**
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        final Label quoteText = new Label();

        Timer timer = new Timer() {

            public void run() {
                //create an async callback to handle the result:
                AsyncCallback callback = new AsyncCallback() {

                    public void onFailure(Throwable arg0) {
                        //display error text if we can't get the quote:
                        quoteText.setText("Failed to get a quote");
                    }

                    public void onSuccess(Object result) {
                        //display the retrieved quote in the label:
                        quoteText.setText((String) result);
                        quoteText.setStyleName("quoteLabel");
                    }
                };
                getService().getQuote(callback);
            }
        };
        timer.scheduleRepeating(1000);
        RootPanel.get().add(quoteText);
    }

    public static RandomQuoteServiceAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        RandomQuoteServiceAsync service = (RandomQuoteServiceAsync) GWT.create(RandomQuoteService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "randomquoteservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}
