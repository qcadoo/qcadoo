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

package com.qcadoo.localization.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public final class TranslationServiceImpl implements InternalTranslationService {

    private static final Logger LOG = LoggerFactory.getLogger(TranslationServiceImpl.class);

    private static final Logger TRANSLATION_LOG = LoggerFactory.getLogger("TRANSLATION");

    private static final Map<String, Set<String>> PREFIX_MESSAGES = new HashMap<String, Set<String>>();

    private static final String DEFAULT_MISSING_MESSAGE = "-";

    @Value("${ignoreMissingTranslations}")
    private boolean ignoreMissingTranslations;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String translate(final String messageCode, final Locale locale, final Object... args) {
        String message = translateWithError(messageCode, locale, args);

        if (message != null) {
            return message.trim();
        }

        TRANSLATION_LOG.warn("Missing translation " + messageCode + " for locale " + locale);

        if (ignoreMissingTranslations) {
            return DEFAULT_MISSING_MESSAGE;
        } else {
            return messageCode;
        }
    }

    @Override
    public String translate(final List<String> messageCodes, final Locale locale, final Object... args) {
        for (String messageCode : messageCodes) {
            String message = translateWithError(messageCode, locale, args);
            if (message != null) {
                return message.trim();
            }
        }

        TRANSLATION_LOG.warn("Missing translation " + messageCodes + " for locale " + locale);

        if (ignoreMissingTranslations) {
            return DEFAULT_MISSING_MESSAGE;
        } else {
            return messageCodes.toString();
        }
    }

    private String translateWithError(final String messageCode, final Locale locale, final Object[] args) {
        return messageSource.getMessage(messageCode, args, null, locale);
    }

    @Override
    public Map<String, String> getMessagesForPrefix(final String prefix, final Locale locale) {
        if (!PREFIX_MESSAGES.containsKey(prefix)) {
            return Collections.emptyMap();
        }

        Map<String, String> commonsTranslations = new HashMap<String, String>();

        for (String commonMessage : PREFIX_MESSAGES.get(prefix)) {
            commonsTranslations.put(commonMessage, translate(commonMessage, locale));
        }

        return commonsTranslations;
    }

    // @Override
    // public String getEntityFieldBaseMessageCode(final DataDefinition dataDefinition, final String fieldName) {
    // return dataDefinition.getPluginIdentifier() + "." + dataDefinition.getName() + "." + fieldName;
    // }

    @Override
    public void prepareMessagesForPrefix(final String prefix) {
        Set<String> messages = new HashSet<String>();
        PREFIX_MESSAGES.put(prefix, messages);
        getMessagesByPrefix(prefix, messages);
    }

    private void getMessagesByPrefix(final String prefix, final Set<String> messages) {
        try {
            Resource[] resources = applicationContext.getResources("classpath*:locales/*.properties");
            for (Resource resource : resources) {
                getMessagesByPrefix(prefix, messages, resource.getInputStream());
            }
            resources = applicationContext.getResources("WEB-INF/locales/*.properties");
            for (Resource resource : resources) {
                getMessagesByPrefix(prefix, messages, resource.getInputStream());
            }
        } catch (IOException e) {
            LOG.error("Cannot read messages file", e);
        }

        LOG.info("Messages for " + prefix + ": " + messages);
    }

    private void getMessagesByPrefix(final String prefix, final Set<String> messages, final InputStream inputStream)
            throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        for (Object property : properties.keySet()) {
            if (String.valueOf(property).startsWith(prefix)) {
                messages.add((String) property);
            }
        }
    }

    // @Override
    // public String translateErrorMessage(final ErrorMessage errorMessage, final Locale locale) {
    // return translate(errorMessage.getMessage(), locale, (Object[]) errorMessage.getVars());
    // }

}
