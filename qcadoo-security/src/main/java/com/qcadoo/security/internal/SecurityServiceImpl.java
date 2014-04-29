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
package com.qcadoo.security.internal;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.qcadoo.security.constants.QcadooSecurityConstants.MODEL_PERSISTENT_TOKEN;
import static com.qcadoo.security.constants.QcadooSecurityConstants.MODEL_USER;
import static com.qcadoo.security.constants.QcadooSecurityConstants.PLUGIN_IDENTIFIER;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.SearchOrders;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.security.api.SecurityRole;
import com.qcadoo.security.api.SecurityRolesService;
import com.qcadoo.security.constants.GroupFields;
import com.qcadoo.security.constants.RoleFields;
import com.qcadoo.security.constants.UserFields;
import com.qcadoo.security.internal.api.InternalSecurityService;
import com.qcadoo.security.internal.api.QcadooUser;

@Service("userDetailsService")
public class SecurityServiceImpl implements InternalSecurityService, UserDetailsService, PersistentTokenRepository,
        ApplicationListener<AuthorizedEvent> {

    private static final String L_USER_NAME = "userName";

    private static final String L_TARGET_ROLE_IDENTIFIER_MUST_BE_GIVEN = "targetRoleIdetifier must be given";

    private static final String L_USER_ENTITY_MUST_BE_GIVEN = "User entity must be given";;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private SecurityRolesService securityRolesService;

    @Override
    public void onApplicationEvent(final AuthorizedEvent event) {
        UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
        Entity entity = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_USER).find()
                .add(SearchRestrictions.eq(L_USER_NAME, userDetails.getUsername())).uniqueResult();
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -1);
        if (entity.getField("lastActivity") == null || now.getTime().after((Date) entity.getField("lastActivity"))) {
            entity.setField("lastActivity", new Date());

            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_USER).save(entity);
        }
    }

    @Override
    @Monitorable
    public String getCurrentUserName() {
        if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null
                || SecurityContextHolder.getContext().getAuthentication().getName() == null) {
            return null;
        }
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Entity entity = getUserEntity(SecurityContextHolder.getContext().getAuthentication().getName());
        checkNotNull(entity, "Current user with login %s cannot be found", login);
        return entity.getStringField(L_USER_NAME);
    }

    @Override
    public List<QcadooUser> getUsers() {
        List<Entity> userEntities = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_USER).find()
                .addOrder(SearchOrders.asc(L_USER_NAME)).list().getEntities();
        List<QcadooUser> users = new LinkedList<QcadooUser>();
        for (Entity userEntity : userEntities) {
            users.add(new QcadooUser(userEntity));
        }
        return users;
    }

    @Override
    public Entity getUserEntity(final String login) {
        return dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_USER).find().add(SearchRestrictions.eq(L_USER_NAME, login))
                .setMaxResults(1).uniqueResult();
    }

    @Override
    @Monitorable
    public Long getCurrentUserId() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Entity entity = getUserEntity(login);
        checkNotNull(entity, "Current user with login %s cannot be found", login);
        return entity.getId();
    }

    @Override
    @Monitorable
    public UserDetails loadUserByUsername(final String username) {
        Entity entity = getUserEntity(username);

        if (entity == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return convertEntityToUserDetails(entity);
    }

    protected UserDetails convertEntityToUserDetails(final Entity entity) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Entity entityRole : entity.getBelongsToField(UserFields.GROUP).getManyToManyField(GroupFields.ROLES_FIELD)) {
            SecurityRole role = securityRolesService.getRoleByIdentifier(entityRole.getStringField("identifier"));

            checkState(role != null, "Role '%s' not defined", entityRole.getStringField("identifier"));

            authorities.add(new GrantedAuthorityImpl(role.getRoleIdentifier()));
        }

        checkState(!authorities.isEmpty(), "Current user with login %s cannot be found", entity.getStringField(L_USER_NAME));

        return new User(entity.getStringField(L_USER_NAME), entity.getStringField(UserFields.PASSWORD), true, true, true, true,
                authorities);
    }

    @Override
    public void createNewToken(final PersistentRememberMeToken token) {
        Entity entity = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).create();
        entity.setField(L_USER_NAME, token.getUsername());
        entity.setField("series", token.getSeries());
        entity.setField("token", token.getTokenValue());
        entity.setField("lastUsed", token.getDate());
        dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).save(entity);
    }

    @Override
    public void updateToken(final String series, final String tokenValue, final Date lastUsed) {
        Entity entity = getPersistentToken(series);
        if (entity != null) {
            entity.setField("token", tokenValue);
            entity.setField("lastUsed", lastUsed);
            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).save(entity);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(final String series) {
        Entity entity = getPersistentToken(series);

        if (entity == null || getUserEntity(entity.getStringField(L_USER_NAME)) == null) {
            return null;
        }

        return new PersistentRememberMeToken(entity.getStringField(L_USER_NAME), entity.getStringField("series"),
                entity.getStringField("token"), (Date) entity.getField("lastUsed"));
    }

    @Override
    public void removeUserTokens(final String username) {
        List<Entity> entities = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).find()
                .add(SearchRestrictions.eq(L_USER_NAME, username)).list().getEntities();

        for (Entity entity : entities) {
            dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).delete(entity.getId());
        }
    }

    private Entity getPersistentToken(final String series) {
        List<Entity> entities = dataDefinitionService.get(PLUGIN_IDENTIFIER, MODEL_PERSISTENT_TOKEN).find()
                .add(SearchRestrictions.eq("series", series)).list().getEntities();

        if (entities.size() == 1) {
            return entities.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasRole(Entity userEntity, String targetRoleIdetifier) {
        checkNotNull(targetRoleIdetifier, L_TARGET_ROLE_IDENTIFIER_MUST_BE_GIVEN);
        checkNotNull(userEntity, L_USER_ENTITY_MUST_BE_GIVEN);

        List<Entity> roles = userEntity.getBelongsToField(UserFields.GROUP).getManyToManyField(GroupFields.ROLES_FIELD);
        for (Entity role : roles) {
            if (targetRoleIdetifier.equals(role.getStringField(RoleFields.IDENTIFIER_FIELD))) {
                return true;
            }
        }
        return false;
    }

}
