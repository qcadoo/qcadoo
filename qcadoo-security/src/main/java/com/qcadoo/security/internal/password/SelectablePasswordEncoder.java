package com.qcadoo.security.internal.password;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public final class SelectablePasswordEncoder implements PasswordEncoder, InitializingBean {

    private String encoder;

    private PasswordEncoder passwordEncoder;

    private PasswordEncoder bcryptPasswordEncoder;

    private PasswordEncoder shaPasswordEncoder;

    public PasswordEncoder getBcryptPasswordEncoder() {
        return bcryptPasswordEncoder;
    }

    public void setBcryptPasswordEncoder(final PasswordEncoder bcryptPasswordEncoder) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    public PasswordEncoder getShaPasswordEncoder() {
        return shaPasswordEncoder;
    }

    public void setShaPasswordEncoder(final PasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }

    public void setEncoder(final String encoder) {
        this.encoder = encoder;
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(bcryptPasswordEncoder);
        Assert.notNull(shaPasswordEncoder);

        if ("sha".equals(encoder)) {
            passwordEncoder = shaPasswordEncoder;
        } else if ("bcrypt".equals(encoder)) {
            passwordEncoder = bcryptPasswordEncoder;
        } else {
            throw new IllegalArgumentException("Unknown encoder value");
        }
    }

}
