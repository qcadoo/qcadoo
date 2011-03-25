/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
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

package com.qcadoo.model.internal.resolver;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.qcadoo.model.internal.api.ModelXmlResolver;
import com.qcadoo.model.internal.module.ModelXmlHolder;
import com.qcadoo.model.internal.utils.JdomUtils;

@Component
public final class ModelXmlResolverImpl implements ModelXmlResolver, ModelXmlHolder {

    private static final Logger LOG = LoggerFactory.getLogger(ModelXmlResolverImpl.class);

    private final Map<String, Document> documents = new HashMap<String, Document>();

    @Override
    public Resource[] getResources() {
        List<Resource> resources = new ArrayList<Resource>();

        for (Document document : documents.values()) {
            byte[] out = JdomUtils.documentToByteArray(document);
            if (LOG.isDebugEnabled()) {
                LOG.debug(new String(out));
            }
            resources.add(new ByteArrayResource(out));
        }

        documents.clear();

        return resources.toArray(new Resource[resources.size()]);
    }

    @Override
    public void put(final String pluginIdentifier, final String modelName, final InputStream stream) {
        LOG.info(" -----------> " + pluginIdentifier + "." + modelName);
        Document document = JdomUtils.inputStreamToDocument(stream);
        document.getRootElement().setAttribute("plugin", pluginIdentifier);
        documents.put(pluginIdentifier + "." + modelName, document);
    }

    @Override
    public Document get(final String pluginIdentifier, final String modelName) {
        Document document = documents.get(pluginIdentifier + "." + modelName);
        checkNotNull(document, "Cannot find model for " + pluginIdentifier + "." + modelName);
        return document;
    }
}
