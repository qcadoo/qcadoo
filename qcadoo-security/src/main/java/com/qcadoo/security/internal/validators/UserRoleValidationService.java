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
package com.qcadoo.security.internal.validators;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.FieldDefinition;
import com.qcadoo.security.api.SecurityService;
import com.qcadoo.security.constants.QcadooSecurityConstants;
import com.qcadoo.security.constants.UserFields;

@Service
public class UserRoleValidationService {

	// TODO KRNA check adding role super to group, eventually delete super user or group

	@Autowired
	private SecurityService securityService;

	public boolean checkUserCreatingSuperadmin(final DataDefinition dataDefinition, final Entity entity) {
		final Object newValue = entity.getBelongsToField(UserFields.GROUP);
		final Object oldValue = getOldValue(dataDefinition, entity);
		return checkUserCreatingSuperadmin(dataDefinition, dataDefinition.getField(UserFields.GROUP), entity, oldValue, newValue);
	}

	private Object getOldValue(final DataDefinition dataDefinition, final Entity entity) {
		if (entity.getId() == null) {
			return null;
		} else {
			final Entity existingEntity = dataDefinition.get(entity.getId());
			return existingEntity.getBelongsToField(UserFields.GROUP);
		}
	}

	private boolean checkUserCreatingSuperadmin(final DataDefinition dataDefinition, final FieldDefinition fieldDefinition,
	        final Entity entity, final Object oldValue, final Object newValue) {
		if (Objects.equal(oldValue, newValue) || isCurrentUserShopOrSuperAdmin(dataDefinition)
		        || !isSuperAdmin(entity.getBelongsToField(UserFields.GROUP).getManyToManyField("roles"))) {
			return true;
		}
		entity.addError(fieldDefinition, "qcadooUsers.validate.global.error.forbiddenRole");
		return false;
	}

	private boolean isCurrentUserShopOrSuperAdmin(final DataDefinition userDataDefinition) {
		if (isCalledFromShop()) {
			return true;
		}
		final Long currentUserId = securityService.getCurrentUserId();
		final Entity currentUserEntity = userDataDefinition.get(currentUserId);
		return isSuperAdmin(currentUserEntity.getBelongsToField(UserFields.GROUP).getManyToManyField("roles"));
	}

	private boolean isCalledFromShop() {
		return SecurityContextHolder.getContext().getAuthentication() == null;
	}

	private boolean isSuperAdmin(final List<Entity> roles) {
		for (Entity role : roles) {
			if (QcadooSecurityConstants.ROLE_SUPERADMIN.equals(role.getStringField("identifier"))) {
				return true;
			}
		}
		return false;
	}

}
