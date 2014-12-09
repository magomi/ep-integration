package de.intecsoft.dev.ep;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public abstract class EpApiResponseData {
    private Long code;
    private String message;

    protected EpApiResponseData() {
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        EpApiResponseData rhs = (EpApiResponseData) obj;
        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(this.code, rhs.code)
                .append(this.message, rhs.message)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder()
                .append(code)
                .append(message)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }
}
