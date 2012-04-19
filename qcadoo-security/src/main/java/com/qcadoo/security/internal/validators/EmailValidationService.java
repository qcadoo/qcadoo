package com.qcadoo.security.internal.validators;

import org.springframework.stereotype.Service;

import com.qcadoo.mail.internal.MailServiceImpl;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;

@Service
public class EmailValidationService {

    public boolean checkEmail(final DataDefinition dataDefinition, final FieldDefinition fieldDefinition, final Entity entity,
            final Object oldValue, final Object newValue) {
        if (newValue == null || newValue.toString().isEmpty()) {
            return true;
        }
        return MailServiceImpl.isValidEmail(newValue.toString());
    }
}
