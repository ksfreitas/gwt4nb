/*
 * RandomQuoteService.java
 *
 * Created on December 10, 2007, 7:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.yournamehere.client;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 *
 * @author gw152771
 */
public interface RandomQuoteService extends RemoteService {
    public String getQuote();
}
