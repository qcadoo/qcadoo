/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.4
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

import com.google.common.base.Preconditions;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.constants.QcadooViewConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class SystemInfoService {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    public Long getSystemInfoId() {
        return getSystemInfo().getId();
    }

    @Transactional
    public Entity getSystemInfo() {
        Entity systemInfo = null;

        DataDefinition systemInfoDD = getSystemInfoDD();

        if (Objects.nonNull(systemInfoDD)) {
            systemInfo = systemInfoDD.find("SELECT s FROM #qcadooView_systemInfo s").setMaxResults(1).uniqueResult();

            if (Objects.isNull(systemInfo)) {
                systemInfo = createSystemInfo(systemInfoDD);
            }
        }

        return systemInfo;
    }

    private Entity createSystemInfo(final DataDefinition systemInfoDD) {
        Entity systemInfo = systemInfoDD.create();

        systemInfo = systemInfoDD.save(systemInfo);

        Preconditions.checkState(systemInfo.isValid(), "SystemInfo entity has validation errors! " + systemInfo);

        return systemInfo;
    }

    private DataDefinition getSystemInfoDD() {
        return dataDefinitionService.get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_SYSTEM_INFO);
    }

}
