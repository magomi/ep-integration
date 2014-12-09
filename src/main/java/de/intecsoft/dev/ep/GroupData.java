package de.intecsoft.dev.ep;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public class GroupData {
    private String groupID;

    protected GroupData() {
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("groupID", groupID)
                .toString();
    }
}
