package com.qcadoo.view.internal;

import org.springframework.context.ApplicationContext;
import org.w3c.dom.Node;

import com.qcadoo.model.api.search.SearchCriteriaBuilder;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;

/**
 * Search criteria modifier
 * 
 * @author marcinkubala
 * @since 1.2.0
 */
public class CriteriaModifier {

    public static final String NODE_NAME = "criteriaModifier";

    private final CustomMethodHolder customMethodHolder;

    public CriteriaModifier(final Node holderNode, final ViewDefinitionParser parser, final ApplicationContext applicationContext) {
        customMethodHolder = new CustomMethodHolder(holderNode, parser, applicationContext, Void.TYPE,
                new Class[] { SearchCriteriaBuilder.class });
    }

    public void modifyCriteria(final SearchCriteriaBuilder searchCriteriaBuilder) {
        customMethodHolder.invoke(searchCriteriaBuilder);
    }

}
