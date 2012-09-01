/*
 * RandomQuoteServiceAsync.java
 *
 * Created on December 10, 2007, 7:13 PM
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
public interface RandomQuoteServiceAsync {

    public void getQuote(AsyncCallback asyncCallback);
}
