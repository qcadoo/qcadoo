/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.7
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
package com.qcadoo.customTranslation.internal;

import static com.qcadoo.customTranslation.constants.CustomTranslationFields.ACTIVE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.KEY;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.LOCALE;
import static com.qcadoo.customTranslation.constants.CustomTranslationFields.PLUGIN_IDENTIFIER;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.MethodUtils;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qcadoo.customTranslation.api.CustomTranslationManagementService;
import com.qcadoo.customTranslation.constants.CustomTranslationContants;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;

@Service
public class CustomTranslationManagementServiceImpl implements CustomTranslationManagementService {

    @Autowired
    private DataDefinitionService dataDefinitionService;


	@Override
    @Transactional
	@SuppressWarnings("unchecked")
	public void addCustomTranslation(final String pluginIdentifier, final String locale, final Set<String> keys) {
		DataDefinition dataDefinition = getCustomTranslationDD();
		Session currentSession = getCurrentSession(dataDefinition);

		List<String> existingKeys = currentSession
				.createQuery("select key from com.qcadoo.model.beans.qcadooCustomTranslation.QcadooCustomTranslationCustomTranslation where pluginIdentifier = ? and locale = ?")
				.setString(0, pluginIdentifier).setString(1, locale).list();

		for (String key : keys) {
			if (existingKeys.contains(key)) {
				continue;
			}
			Object entity = getInstanceForEntity(dataDefinition);
			FieldUtils.setProtectedFieldValue(PLUGIN_IDENTIFIER, entity, pluginIdentifier);
			FieldUtils.setProtectedFieldValue(KEY, entity, key);
			FieldUtils.setProtectedFieldValue(LOCALE, entity, locale);
			FieldUtils.setProtectedFieldValue(ACTIVE, entity, false);
			currentSession.save(entity);
		}

		currentSession
				.createQuery(
						"update com.qcadoo.model.beans.qcadooCustomTranslation.QcadooCustomTranslationCustomTranslation set active = true where pluginIdentifier = ? and active = false and customTranslation is not null and customTranslation != ''")
				.setString(0, pluginIdentifier).executeUpdate();
    }

	public Object getInstanceForEntity(DataDefinition dataDefinition) {
		try {
			return MethodUtils.invokeExactMethod(dataDefinition, "getInstanceForEntity", new Object[0]);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public Session getCurrentSession(DataDefinition dataDefinition) {
		Object dataAccessService = FieldUtils.getProtectedFieldValue("dataAccessService", dataDefinition);
		Object hibernateService = FieldUtils.getProtectedFieldValue("hibernateService", dataAccessService);
		try {
			return (Session) MethodUtils.invokeExactMethod(hibernateService, "getCurrentSession", new Object[0]);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

    @Override
	@Transactional
	public void removeCustomTranslation(final String pluginIdentifier) {
		Session currentSession = getCurrentSession(getCustomTranslationDD());
		currentSession
				.createQuery(
						"update com.qcadoo.model.beans.qcadooCustomTranslation.QcadooCustomTranslationCustomTranslation set active = false where pluginIdentifier = ? and active = true")
				.setString(0, pluginIdentifier).executeUpdate();
    }

    @Override
    public Entity getCustomTranslation(final String pluginIdentifier, final String key, final String locale) {
        Entity customTranslation = getCustomTranslationDD().find()
                .add(SearchRestrictions.eq(PLUGIN_IDENTIFIER, pluginIdentifier)).add(SearchRestrictions.eq(KEY, key))
                .add(SearchRestrictions.eq(LOCALE, locale)).setMaxResults(1).uniqueResult();

        return customTranslation;
    }

    @Override
    public List<Entity> getCustomTranslations(final String locale) {
        List<Entity> customTranslations = getCustomTranslationDD().find().add(SearchRestrictions.eq(LOCALE, locale)).list()
                .getEntities();

        return customTranslations;
    }

    @Override
    public DataDefinition getCustomTranslationDD() {
        return dataDefinitionService.get(CustomTranslationContants.PLUGIN_IDENTIFIER,
                CustomTranslationContants.MODEL_CUSTOM_TRANSLATION);
    }

}