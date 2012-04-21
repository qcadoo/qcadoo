package com.qcadoo.security.internal.password;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.qcadoo.security.api.PasswordGeneratorService;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    private static final String PASSWORD_GENERATOR_CHARS = "123456789wertyuipasdfghjkzxcvbnmQWERTYUASDFGHJKZXCVBNM!*&%$#@";

    @Override
    public String generatePassword() {
        return generatePassword(DEFAULT_PASSWORD_LENGTH);
    }

    @Override
    public String generatePassword(final int length) {
        return RandomStringUtils.random(length, PASSWORD_GENERATOR_CHARS);
    }

}
