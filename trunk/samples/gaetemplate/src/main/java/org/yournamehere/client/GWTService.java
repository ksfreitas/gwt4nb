package org.yournamehere.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A service for managing messages.
 */
@RemoteServiceRelativePath("gwtservice")
public interface GWTService extends RemoteService {
    /**
     * Adds a new message
     *
     * @param s the message
     */
    public void addMessage(String s);

    /**
     * @return all available messages
     */
    public String[] getMessages();
}
