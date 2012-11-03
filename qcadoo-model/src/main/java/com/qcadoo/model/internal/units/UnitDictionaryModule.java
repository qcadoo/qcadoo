package com.qcadoo.model.internal.units;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.model.constants.QcadooModelConstants;
import com.qcadoo.model.internal.api.InternalDictionaryService;
import com.qcadoo.plugin.api.Module;

@Service
public class UnitDictionaryModule extends Module {

    @Autowired
    private InternalDictionaryService dictionaryService;

    @Override
    public void multiTenantDisable() {
        disable();
    }

    @Override
    public void disable() {
        dictionaryService.disable(QcadooModelConstants.PLUGIN_IDENTIFIER, QcadooModelConstants.DICTIONARY_UNITS);
    }
}
