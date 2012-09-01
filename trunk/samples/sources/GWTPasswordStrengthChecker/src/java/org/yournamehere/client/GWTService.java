/*
 * GWTService.java
 *
 * Created on December 11, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.client;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 *
 * @author gw152771
 */
public interface GWTService extends RemoteService {

   // public List getCompletionItems(String itemToMatch);
    	public int checkStrength(String password);
}
