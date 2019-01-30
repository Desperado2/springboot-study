package com.desperado.chapter14.framework.swagger.properties;

import java.io.Serializable;

public class DocketProperties implements Serializable {

    private String groupName;

    private String basePackage;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
}
