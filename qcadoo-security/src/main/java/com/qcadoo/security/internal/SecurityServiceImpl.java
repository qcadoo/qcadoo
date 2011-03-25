/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
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

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.search.Restrictions;
import com.qcadoo.security.api.SecurityService;

@Service("userDetailsService")
public final class SecurityServiceImpl implements SecurityService, UserDetailsService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Override
    @Monitorable
    public String getCurrentUserName() {
        return getUserEntity(SecurityContextHolder.getContext().getAuthentication().getName()).getStringField("userName");
    }

    private Entity getUserEntity(final String login) {
        List<Entity> users = dataDefinitionService.get("qcadooSecurity", "user").find()
                .restrictedWith(Restrictions.eq("userName", login)).withMaxResults(1).list().getEntities();
        checkState(users.size() > 0, "Current user with login %s cannot be found", login);
        return users.get(0);
    }

    @Override
    @Monitorable
    public Long getCurrentUserId() {
        return getUserEntity(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

    @Override
    @Monitorable
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, DataAccessException {
        Entity entity = getUserEntity(username);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new GrantedAuthorityImpl(entity.getBelongsToField("userGroup").getStringField("role")));

        checkState(authorities.size() > 0, "Current user with login %s cannot be found", username);

        return new User(username, entity.getStringField("password"), true, true, true, true, authorities);
    }

}
