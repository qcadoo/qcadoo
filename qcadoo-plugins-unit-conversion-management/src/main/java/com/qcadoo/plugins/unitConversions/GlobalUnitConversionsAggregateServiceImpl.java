package com.qcadoo.plugins.unitConversions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.constants.QcadooModelConstants;

@Service
public final class GlobalUnitConversionsAggregateServiceImpl implements GlobalUnitConversionsAggregateService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    @Transactional
    public Long getAggregateId() {
        Entity existingAggregate = getAggregateDataDefinition().find().setMaxResults(1).uniqueResult();
        if (existingAggregate == null) {
            existingAggregate = getAggregateDataDefinition().create();
            existingAggregate = existingAggregate.getDataDefinition().save(existingAggregate);
            Preconditions.checkState(existingAggregate.isValid());
        }
        return existingAggregate.getId();
    }

    private DataDefinition getAggregateDataDefinition() {
        return dataDefinitionService.get(QcadooModelConstants.PLUGIN_IDENTIFIER,
                QcadooModelConstants.MODEL_GLOBAL_UNIT_CONVERSIONS_AGGREGATE);
    }

}
