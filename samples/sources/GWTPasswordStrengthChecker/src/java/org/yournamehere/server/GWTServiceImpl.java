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
import org.yournamehere.client.GWTService;

/**
 *
 * @author gw152771
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {

//    private String[] items = new String[]{"apple", "peach", "orange",
//        "banana", "plum", "avocado",
//        "strawberry", "pear", "watermelon", 
//        "pineapple", "grape",
//        "blueberry", "cantaloupe"
//    };
//
//    public List getCompletionItems(String itemToMatch) {
//        ArrayList completionList = new ArrayList();
//        for (int i = 0; i < items.length; i++) {
//            if (items[i].startsWith(itemToMatch.toLowerCase())) {
//                completionList.add(items[i]);
//            }
//        }
//        return completionList;
//    }
    private int STRONG = 9;
    private int MEDIUM = 6;
    private int WEAK = 3;

    public int checkStrength(String password) {
        if (password.length() <= 4) {
            return WEAK;
        } else if (password.length() < 8) {
            return MEDIUM;
        } else {
            return STRONG;
        }
    }
}
