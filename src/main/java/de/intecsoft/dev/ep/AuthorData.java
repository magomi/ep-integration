package de.intecsoft.dev.ep;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public class AuthorData {
    private String authorID;

    protected AuthorData() {
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("authorID", authorID)
                .toString();
    }
}
