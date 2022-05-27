package com.qcadoo.security.api;

import com.qcadoo.model.api.Entity;

public interface PasswordResetTokenService {

    Entity createPasswordResetTokenForUser(Entity userEntity);

    Entity processPasswordChange(String token, String password, String passwordConfirmation);

}
