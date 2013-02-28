package com.qcadoo.model.api.search;

import org.hibernate.criterion.CriteriaSpecification;

/**
 * Supported join types
 * 
 * @author Marcin Kubala
 * @since 1.2.1
 */
public enum JoinType {
    /**
     * Specifies joining to an entity based on a full join.
     */
    FULL(CriteriaSpecification.FULL_JOIN),

    /**
     * Specifies joining to an entity based on an inner join.
     */
    INNER(CriteriaSpecification.INNER_JOIN),

    /**
     * Specifies joining to an entity based on a left outer join.
     */
    LEFT(CriteriaSpecification.LEFT_JOIN);

    private final int intValue;

    private JoinType(final int intValue) {
        this.intValue = intValue;
    }

    /**
     * Get corresponding Hibernate's integer constant value for this join type.
     * 
     * @return corresponding Hibernate's integer constant value for this join type.
     */
    public int getIntValue() {
        return intValue;
    }
}
