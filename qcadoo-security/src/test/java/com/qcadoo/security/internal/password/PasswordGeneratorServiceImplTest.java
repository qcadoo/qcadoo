package com.qcadoo.security.internal.password;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PasswordGeneratorServiceImplTest {

    private PasswordGeneratorServiceImpl passwordGeneratorServiceImpl;

    @Before
    public final void init() {
        passwordGeneratorServiceImpl = new PasswordGeneratorServiceImpl();
    }

    @Test
    public final void shouldReturnValidPassword() throws Exception {
        // when
        String generatedPassword = passwordGeneratorServiceImpl.generatePassword();

        // then
        Assert.assertEquals(10, generatedPassword.length());
        Assert.assertFalse(generatedPassword.contains(" "));
    }

    @Test
    public final void shouldReturnValidPasswordWithSpecifiedLenght() throws Exception {
        // given
        int length = 8;

        // when
        String generatedPassword = passwordGeneratorServiceImpl.generatePassword(length);

        // then
        Assert.assertEquals(length, generatedPassword.length());
        Assert.assertFalse(generatedPassword.contains(" "));
    }

}
