package com.qcadoo.view.api.utils;

import java.util.LinkedList;
import java.util.List;
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
        List<String> code = new LinkedList<String>();
        code.add(category.getStringField("pluginIdentifier") + ".menu." + category.getStringField("name"));
        code.add("core.menu." + category.getStringField("name"));
        return translationService.translate(code, locale);
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
        List<String> code = new LinkedList<String>();
        code.add(item.getStringField("pluginIdentifier") + ".menu." + categoryEntity.getStringField("name") + "."
                + item.getStringField("name"));
        code.add("core.menu." + categoryEntity.getStringField("name") + "." + item.getStringField("name"));
        return translationService.translate(code, locale);
    }

}
