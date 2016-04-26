/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;

public final class JsonMapperHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapperHttpMessageConverter.class);

    public static final Charset CHARSET = Charset.forName("UTF-8");

    public static final MediaType MEDIA_TYPE = new MediaType("application", "json", CHARSET);

    public final ObjectMapper mapper = new ObjectMapper();

    public JsonMapperHttpMessageConverter() {
        super(MEDIA_TYPE);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    @Override
    protected boolean supports(final Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(final Class<?> clazz, final HttpInputMessage inputMessage) throws IOException {
        String body = IOUtils.toString(inputMessage.getBody(), CHARSET.name());
        if (LOG.isDebugEnabled()) {
            LOG.debug(body);
        }
        return mapper.readValue(body, clazz);
    }

    @Override
    protected void writeInternal(final Object value, final HttpOutputMessage outputMessage) throws IOException {
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(outputMessage.getBody(), CHARSET);
            String out = mapper.writeValueAsString(value);
            if (LOG.isDebugEnabled()) {
                LOG.debug(out);
            }
            writer.append(out);
            writer.flush();
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }

}
