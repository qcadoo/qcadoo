package com.qcadoo.view.api.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.model.api.Entity;

/**
 * Helper service for translations
 * 
 * @since 0.4.0
 */
@Service
public class TranslationUtilsService {

    @Autowired
    private TranslationService translationService;

    /**
     * Returns menu category translation
     * 
     * @param category
     *            category entity
     * @param locale
     *            localization
     * @return category translation
     */
    public String getCategoryTranslation(final Entity category, final Locale locale) {
        return translationService.translate(
                category.getStringField("pluginIdentifier") + ".menu." + category.getStringField("name"),
                "core.menu." + category.getStringField("name"), locale);
    }

    /**
     * Returns menu item translation
     * 
     * @param item
     *            item entity
     * @param locale
     *            localization
     * @return item translation
     */
    public String getItemTranslation(final Entity item, final Locale locale) {
        Entity categoryEntity = item.getBelongsToField("category");
        return translationService.translate(
                item.getStringField("pluginIdentifier") + ".menu." + categoryEntity.getStringField("name") + "."
                        + item.getStringField("name"),
                "core.menu." + categoryEntity.getStringField("name") + "." + item.getStringField("name"), locale);
    }

}
