package org.yournamehere.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.yournamehere.client.GWTService;

/**
 * Service implementation using JDO.
 */
public class GWTServiceImpl extends RemoteServiceServlet implements GWTService {
    private PersistenceManagerFactory pmfInstance = JDOHelper.
            getPersistenceManagerFactory("transactions-optional");

    public void addMessage(String message) {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            pm.makePersistent(new Message(message));
        } finally {
            pm.close();
        }
    }

    public String[] getMessages() {
        PersistenceManager pm = pmfInstance.getPersistenceManager();
        try {
            List<String> messages = new ArrayList<String>();
            Extent<Message> extent = pm.getExtent(Message.class, false);
            for (Message message : extent) {
                messages.add(message.getText());
            }
            extent.closeAll();

            return messages.toArray(new String[0]);
        } finally {
            pm.close();
        }
    }
}
