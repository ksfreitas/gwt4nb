/*
 * GWTServiceAsync.java
 *
 * Created on December 11, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author gw152771
 */
public interface GWTServiceAsync {

    public void getManufacturers(AsyncCallback callback);

    public void getBrands(String manufacturer, AsyncCallback callback);

    public void getModels(String manufacturer, String brand,
            AsyncCallback callback);
}
