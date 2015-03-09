package com.qcadoo.model.internal.dictionaries.hooks;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.model.constants.DictionaryItemFields;

@Service
public class DictionaryItemValidators {

    public boolean onValidate(final DataDefinition dictionaryItemDD, final Entity dictionaryItem) {
        boolean isValid = true;

        isValid = checkIfIsUnique(dictionaryItemDD, dictionaryItem) && isValid;
        isValid = disallowNameChange(dictionaryItemDD, dictionaryItem) && isValid;

        return isValid;
    }

    private boolean checkIfIsUnique(final DataDefinition dictionaryItemDD, final Entity dictionaryItem) {
        if (!checkIfDictionaryItemIsUnique(dictionaryItemDD, dictionaryItem)) {
            dictionaryItem.addError(dictionaryItemDD.getField(DictionaryItemFields.NAME),
                    "qcadooView.validate.field.error.duplicated");

            return false;
        }

        return true;
    }

    private boolean checkIfDictionaryItemIsUnique(final DataDefinition dictionaryItemDD, final Entity dictionaryItem) {
        Entity dictionary = dictionaryItem.getBelongsToField(DictionaryItemFields.DICTIONARY);
        String name = dictionaryItem.getStringField(DictionaryItemFields.NAME);

        SearchCriteriaBuilder searchCriteriaBuilder = dictionaryItemDD.find()
                .add(SearchRestrictions.belongsTo(DictionaryItemFields.DICTIONARY, dictionary))
                .add(SearchRestrictions.eq(DictionaryItemFields.NAME, name));

        if (dictionaryItem.getId() != null) {
            searchCriteriaBuilder.add(SearchRestrictions.idNe(dictionaryItem.getId()));
        }

        SearchResult searchResult = searchCriteriaBuilder.list();

        return searchResult.getEntities().isEmpty();
    }

    private boolean disallowNameChange(final DataDefinition dictionaryItemDD, final Entity dictionaryItem) {
        if (dictionaryItem.getId() != null) {
            Entity existingEntity = dictionaryItemDD.get(dictionaryItem.getId());

            if (!Objects.equals(dictionaryItem.getStringField(DictionaryItemFields.NAME),
                    existingEntity.getStringField(DictionaryItemFields.NAME))) {
                dictionaryItem.addError(dictionaryItemDD.getField(DictionaryItemFields.NAME),
                        "qcadooDictionaries.dictionaryItem.validateError.nameChange");
                return false;
            }
        }

        return true;
    }

}
