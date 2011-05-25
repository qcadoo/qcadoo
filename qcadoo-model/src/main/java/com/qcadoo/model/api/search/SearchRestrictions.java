package com.qcadoo.model.api.search;

import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.DataAccessService;
import com.qcadoo.model.internal.api.InternalDataDefinition;

/**
 * @since 0.4.1
 */
@Component
public class SearchRestrictions {

    private static DataAccessService dataAccessService;

    @Autowired
    public SearchRestrictions(final DataAccessService dataAccessService) {
        SearchRestrictions.dataAccessService = dataAccessService;
    }

    public static enum SearchMatchMode {

        ANYWHERE(MatchMode.ANYWHERE), END(MatchMode.END), EXACT(MatchMode.EXACT), START(MatchMode.START);

        private final MatchMode matchMode;

        private SearchMatchMode(final MatchMode matchMode) {
            this.matchMode = matchMode;
        }

        public MatchMode getHibernateMatchMode() {
            return matchMode;
        }

    }

    public static SearchCriterion or(final SearchCriterion firstCriterion, final SearchCriterion secondCriterion,
            final SearchCriterion... otherCriterions) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(firstCriterion.getHibernateCriterion());
        disjunction.add(secondCriterion.getHibernateCriterion());

        for (SearchCriterion criterion : otherCriterions) {
            disjunction.add(criterion.getHibernateCriterion());
        }

        return new SearchCriterionImpl(disjunction);
    }

    public static SearchCriterion and(final SearchCriterion firstCriterion, final SearchCriterion secondCriterion,
            final SearchCriterion... otherCriterions) {
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(firstCriterion.getHibernateCriterion());
        conjunction.add(secondCriterion.getHibernateCriterion());

        for (SearchCriterion criterion : otherCriterions) {
            conjunction.add(criterion.getHibernateCriterion());
        }

        return new SearchCriterionImpl(conjunction);
    }

    public static SearchCriterion not(final SearchCriterion criterion) {
        return new SearchCriterionImpl(Restrictions.not(criterion.getHibernateCriterion()));
    }

    public static SearchCriterion idEq(final long value) {
        return new SearchCriterionImpl(Restrictions.idEq(value));
    }

    public static SearchCriterion idNe(final long value) {
        return new SearchCriterionImpl(Restrictions.not(Restrictions.idEq(value)));
    }

    public static SearchCriterion allEq(final Map<String, Object> values) {
        return new SearchCriterionImpl(Restrictions.allEq(values));
    }

    public static SearchCriterion like(final String field, final String value) {
        return new SearchCriterionImpl(Restrictions.like(field, value.replace('*', '%').replace('?', '_')));
    }

    public static SearchCriterion like(final String field, final String value, final SearchMatchMode matchMode) {
        return new SearchCriterionImpl(Restrictions.like(field, value.replace('*', '%').replace('?', '_'),
                matchMode.getHibernateMatchMode()));
    }

    public static SearchCriterion ilike(final String field, final String value) {
        return new SearchCriterionImpl(Restrictions.ilike(field, value.replace('*', '%').replace('?', '_')));
    }

    public static SearchCriterion ilike(final String field, final String value, final SearchMatchMode matchMode) {
        return new SearchCriterionImpl(Restrictions.ilike(field, value.replace('*', '%').replace('?', '_'),
                matchMode.getHibernateMatchMode()));
    }

    public static SearchCriterion le(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.le(field, value));
    }

    public static SearchCriterion lt(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.lt(field, value));
    }

    public static SearchCriterion ge(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.ge(field, value));
    }

    public static SearchCriterion gt(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.gt(field, value));
    }

    public static SearchCriterion ne(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.ne(field, value));
    }

    public static SearchCriterion eq(final String field, final Object value) {
        return new SearchCriterionImpl(Restrictions.eq(field, value));
    }

    public static SearchCriterion leField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.leProperty(field, otherField));
    }

    public static SearchCriterion ltField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.ltProperty(field, otherField));
    }

    public static SearchCriterion geField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.geProperty(field, otherField));
    }

    public static SearchCriterion gtField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.gtProperty(field, otherField));
    }

    public static SearchCriterion neField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.neProperty(field, otherField));
    }

    public static SearchCriterion eqField(final String field, final String otherField) {
        return new SearchCriterionImpl(Restrictions.eqProperty(field, otherField));
    }

    public static SearchCriterion isNotNull(final String field) {
        return new SearchCriterionImpl(Restrictions.isNotNull(field));
    }

    public static SearchCriterion isNull(final String field) {
        return new SearchCriterionImpl(Restrictions.isNull(field));
    }

    public static SearchCriterion sizeEq(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeEq(field, size));
    }

    public static SearchCriterion sizeLe(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeLe(field, size));
    }

    public static SearchCriterion sizeLt(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeLt(field, size));
    }

    public static SearchCriterion sizeGe(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeGe(field, size));
    }

    public static SearchCriterion sizeGt(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeGt(field, size));
    }

    public static SearchCriterion sizeNe(final String field, final int size) {
        return new SearchCriterionImpl(Restrictions.sizeNe(field, size));
    }

    public static SearchCriterion isEmpty(final String field) {
        return new SearchCriterionImpl(Restrictions.isEmpty(field));
    }

    public static SearchCriterion isNotEmpty(final String field) {
        return new SearchCriterionImpl(Restrictions.isNotEmpty(field));
    }

    public static SearchCriterion between(final String field, final Object lo, final Object hi) {
        return new SearchCriterionImpl(Restrictions.between(field, lo, hi));
    }

    public static SearchCriterion in(final String field, final Collection<?> collection) {
        return new SearchCriterionImpl(Restrictions.in(field, collection));
    }

    public static SearchCriterion in(final String field, final Object... values) {
        return new SearchCriterionImpl(Restrictions.in(field, values));
    }

    public static SearchCriterion belongsTo(final String field, final String pluginIdentifier, final String modelName,
            final long entityId) {
        return belongsTo(field, dataAccessService.getDataDefinition(pluginIdentifier, modelName), entityId);
    }

    public static SearchCriterion belongsTo(final String field, final DataDefinition dataDefinition, final long entityId) {
        return belongsTo(field, dataAccessService.get((InternalDataDefinition) dataDefinition, entityId));
    }

    public static SearchCriterion belongsTo(final String field, final Entity entity) {
        Object databaseEntity = null;

        if (entity != null) {
            databaseEntity = dataAccessService.convertToDatabaseEntity(entity);
        }

        if (databaseEntity == null) {
            return isNull(field);
        } else {
            return eq(field, databaseEntity);
        }
    }

    public static SearchDisjunction disjunction() {
        return new SearchDisjunction();
    }

    public static SearchConjunction conjunction() {
        return new SearchConjunction();
    }

}
