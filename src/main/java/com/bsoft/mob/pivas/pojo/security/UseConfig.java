package com.bsoft.mob.pivas.pojo.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 读取到的系统参数
 * Created by huangy on 2015-04-20.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UseConfig {

    /**
     * 摆药扫描自动确认 , 1为自动；0为手动
     */
    @JsonProperty("BYZDQR")
    public String BYZDQR = "1";

    /**
     * 摆药核对扫描自动确认 , 1为自动；0为手动
     */
    @JsonProperty("BYHDZDQR")
    public String BYHDZDQR = "1";


    /**
     * 成品核对扫描自动确认 , 1为自动；0为手动
     */
    @JsonProperty("CPHDZDQR")
    public String CPHDZDQR = "1";

    /**
     * 摆药模式 , 1为简易；2为精确模式
     */
    @JsonProperty("BYMS")
    public String BYMS = "1";

    /**
     * 病区签收模式 , 1为简易；2为精确模式
     */
    @JsonProperty("BQQSMS")
    public String BQQSMS = "1";

    /**
     * 所有参数属于的静配药房IDL
     */
    @JsonProperty("JPBH")
    public String JPBH;

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
        UseConfig rhs = (UseConfig) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(JPBH, rhs.JPBH)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().
                append(JPBH).
                toHashCode();
    }
}
