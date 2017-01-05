/*
 * GWTServiceImpl.java
 *
 * Created on December 11, 2007, 5:05 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.yournamehere.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashMap;
import org.yournamehere.client.GWTService;

/**
 *
 * @author gw152771
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

    private HashMap formInfo = new HashMap();

    private void loadCustomerData() {
        
        HashMap customer1 = new HashMap();
        customer1.put("first name", "Joe");
        customer1.put("last name", "Customer");
        customer1.put("address", "123 Peach Street");
        customer1.put("city", "Atlanta");
        customer1.put("state", "GA");
        customer1.put("zip", "30339");
        customer1.put("phone", "770-123-4567");
        formInfo.put("1111", customer1);
        
        HashMap customer2 = new HashMap();
        customer2.put("first name", "Jane");
        customer2.put("last name", "Customer");
        customer2.put("address", "456 Elm Street");
        customer2.put("city", "Miami");
        customer2.put("state", "FL");
        customer2.put("zip", "24156");
        customer2.put("phone", "817-123-4567");
        formInfo.put("2222", customer2);
        
    }

    public HashMap getFormInfo(String formKey) {
        loadCustomerData();
        if (formInfo.containsKey(formKey)) {
            return (HashMap) formInfo.get(formKey);

        } else {
            return new HashMap();
        }
    }
}
