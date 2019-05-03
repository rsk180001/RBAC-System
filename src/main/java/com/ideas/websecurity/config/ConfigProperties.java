package com.ideas.websecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {
    @Value("${admin.user.name}")
    private String adminUserName;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${read.only.user.name}")
    private String readOnlyUserName;

    @Value("${read.only.password}")
    private String readOnlyPassword;

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getReadOnlyUserName() {
        return readOnlyUserName;
    }

    public void setReadOnlyUserName(String readOnlyUserName) {
        this.readOnlyUserName = readOnlyUserName;
    }

    public String getReadOnlyPassword() {
        return readOnlyPassword;
    }

    public void setReadOnlyPassword(String readOnlyPassword) {
        this.readOnlyPassword = readOnlyPassword;
    }
}
