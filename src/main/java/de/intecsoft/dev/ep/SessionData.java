package de.intecsoft.dev.ep;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public class SessionData {
    private String sessionID;

    protected SessionData() {
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sessionID", sessionID)
                .toString();
    }
}
