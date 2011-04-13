package com.qcadoo.view.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.search.SearchResult;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.components.FieldComponentState;
import com.qcadoo.view.components.form.FormComponentState;

@Service
public class NumberGeneratorService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    public void generateAndInsertNumber(final ViewDefinitionState state, final String plugin, final String entityName,
            final String formFieldReferenceName, final String numberFieldReferenceName) {
        if (!checkIfShouldInsertNumber(state, formFieldReferenceName, numberFieldReferenceName)) {
            return;
        }
        FieldComponentState number = (FieldComponentState) state.getComponentByReference(numberFieldReferenceName);
        number.setFieldValue(generateNumber(plugin, entityName));
    }

    public boolean checkIfShouldInsertNumber(final ViewDefinitionState state, final String formFieldReferenceName,
            final String numberFieldReferenceName) {
        FormComponentState form = (FormComponentState) state.getComponentByReference(formFieldReferenceName);
        FieldComponentState number = (FieldComponentState) state.getComponentByReference(numberFieldReferenceName);
        if (form.getEntityId() != null) {
            // form is already saved
            return false;
        }
        if (StringUtils.hasText((String) number.getFieldValue())) {
            // number is already choosen
            return false;
        }
        if (number.isHasError()) {
            // there is a validation message for that field
            return false;
        }
        return true;
    }

    public String generateNumber(final String plugin, final String entityName) {
        return generateNumber(plugin, entityName, 6);
    }

    public String generateNumber(final String plugin, final String entityName, final int digitsNumber) {

        SearchResult results = dataDefinitionService.get(plugin, entityName).find().setMaxResults(1).setOrderDescBy("id").list();

        long longValue = 0;

        if (results.getEntities().isEmpty()) {
            longValue++;
        } else {
            longValue = results.getEntities().get(0).getId() + 1;
        }

        return String.format("%06d", longValue);
    }

}
