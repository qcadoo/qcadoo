/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
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
