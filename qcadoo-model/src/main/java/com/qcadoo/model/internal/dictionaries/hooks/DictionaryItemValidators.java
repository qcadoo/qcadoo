package com.qcadoo.model.internal.dictionaries.hooks;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.DictionaryItemFields;

@Service
public class DictionaryItemValidators {

    public boolean disallowNameChange(final DataDefinition dataDefinition, final Entity entity) {

        if(entity.getId() != null) {
            Entity existingEntity = dataDefinition.get(entity.getId());

            if (!Objects.equals(entity.getStringField(DictionaryItemFields.NAME),
                    existingEntity.getStringField(DictionaryItemFields.NAME))) {
                entity.addError(dataDefinition.getField(DictionaryItemFields.NAME),
                        "qcadooDictionaries.dictionaryItem.validateError.nameChange");
                return false;
            }
        }

        return true;
    }
}
