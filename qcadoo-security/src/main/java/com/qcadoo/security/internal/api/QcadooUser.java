package com.qcadoo.security.internal.api;

import java.util.Date;

import com.qcadoo.model.api.Entity;

public class QcadooUser {

    private final String login;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String groupName;

    private final Date lastActivity;

    public QcadooUser(final String login, final String email, final String firstName, final String lastName,
            final String groupName, final Date lastActivity) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupName = groupName;
        this.lastActivity = lastActivity;
    }

    public QcadooUser(final Entity userEntity) {
        this.login = userEntity.getStringField("userName");
        this.email = userEntity.getStringField("email");
        this.firstName = userEntity.getStringField("firstName");
        this.lastName = userEntity.getStringField("lastName");
        this.groupName = userEntity.getStringField("role");
        this.lastActivity = (Date) userEntity.getField("lastActivity");
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

}
