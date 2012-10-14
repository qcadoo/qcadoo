package com.qcadoo.model.internal.units;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.CustomRestriction;
import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.model.api.search.SearchDisjunction;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.units.UnitConversionModelService;
import com.qcadoo.model.constants.QcadooModelConstants;
import com.qcadoo.model.constants.UnitConversionItemFields;

@Service
public class UnitConversionModelServiceImpl implements UnitConversionModelService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    private static final CustomRestriction RESTRICTION_ONLY_GLOBAL = new CustomRestriction() {

        @Override
        public void addRestriction(final SearchCriteriaBuilder searchCriteriaBuilder) {
            searchCriteriaBuilder.add(SearchRestrictions.isNotNull(UnitConversionItemFields.GLOBAL_UNIT_CONVERSIONS_AGGREGATE));
        }
    };

    @Override
    public List<Entity> find(final String unit) {
        return find(unit, RESTRICTION_ONLY_GLOBAL);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Entity> find(final String unit, final CustomRestriction customRestriction) {
        final CustomRestriction unitMatchingRestriction = new CustomRestriction() {

            @Override
            public void addRestriction(final SearchCriteriaBuilder searchCriteriaBuilder) {
                customRestriction.addRestriction(searchCriteriaBuilder);
                final SearchDisjunction disjunction = SearchRestrictions.disjunction();
                disjunction.add(SearchRestrictions.eq(UnitConversionItemFields.UNIT_FROM, unit));
                disjunction.add(SearchRestrictions.eq(UnitConversionItemFields.UNIT_TO, unit));
                searchCriteriaBuilder.add(disjunction);
            }
        };

        final SearchCriteriaBuilder searchCriteriaBuilder = getDataDefinition().find();
        unitMatchingRestriction.addRestriction(searchCriteriaBuilder);
        return searchCriteriaBuilder.list().getEntities();
    }

    @Override
    public DataDefinition getDataDefinition() {
        return dataDefinitionService.get(QcadooModelConstants.PLUGIN_IDENTIFIER, QcadooModelConstants.MODEL_UNIT_CONVERSION_ITEM);
    }

}
