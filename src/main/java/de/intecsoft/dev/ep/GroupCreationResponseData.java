package de.intecsoft.dev.ep;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
@XmlRootElement
public class GroupCreationResponseData extends EpApiResponseData {

    private GroupData data;

    public GroupCreationResponseData() {
    }

    public GroupData getData() {
        return data;
    }

    public void setData(GroupData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        GroupCreationResponseData rhs = (GroupCreationResponseData) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.data, rhs.data)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(data)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("data", data)
                .toString();
    }
}
