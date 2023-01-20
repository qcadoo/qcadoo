package com.qcadoo.security.internal.password;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class ShaPasswordEncoderAdapter implements PasswordEncoder {

    private final ShaPasswordEncoder shaPasswordEncoder;

    ShaPasswordEncoderAdapter(final ShaPasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }

    @Override
    public String encode(final CharSequence rawPassword) {
        return shaPasswordEncoder.encodePassword(String.valueOf(rawPassword), null);
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return shaPasswordEncoder.isPasswordValid(encodedPassword, String.valueOf(rawPassword), null);
    }

}
