package com.qcadoo.security.internal.api;

import java.util.Date;

import com.qcadoo.model.api.Entity;

public class QcadooUser {

    private String login;

    private String email;

    private String firstName;

    private String lastName;

    private String groupName;

    private Date lastActivity;

    public QcadooUser() {
        // nothing
    }

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

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public void setLastActivity(final Date lastActivity) {
        this.lastActivity = lastActivity;
    }

}
