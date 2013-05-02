package com.qcadoo.plugins.dictionaries.internal.hooks;

import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.DictionaryItemFields;

@Service
public class DictionaryItemHooks {

    public void onCopy(final DataDefinition dictionaryItemDD, final Entity dictionaryItem) {
        dictionaryItem.setField(DictionaryItemFields.EXTERNAL_NUMBER, null);
    }
}
