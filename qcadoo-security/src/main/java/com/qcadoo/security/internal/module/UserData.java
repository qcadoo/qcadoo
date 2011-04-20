package com.qcadoo.security.internal.module;

public class UserData {

    private final String userName;

    private final String password;

    private final String role;

    private final String firstName;

    private final String lastName;

    private final String email;

    public UserData(final String userName, final String password, final String role, final String firstName,
            final String lastName, final String email) {
        super();
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
