/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.10
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
package com.qcadoo.report.api;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.lowagie.text.DocumentException;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.localization.api.utils.DateUtils;
import com.qcadoo.model.api.Entity;

public abstract class DocumentService {

    private DecimalFormat decimalFormat;

    @Value("${reportPath}")
    private String path;

    @Autowired
    private TranslationService translationService;

    public abstract void generateDocument(final Entity entity, final Entity company, final Locale locale) throws IOException,
            DocumentException;

    protected abstract String getSuffix();

    protected final TranslationService getTranslationService() {
        return translationService;
    }

    protected abstract String getReportTitle(final Locale locale);

    public final DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    protected void setDecimalFormat(final DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

    protected void ensureReportDirectoryExist() {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public Entity updateFileName(final Entity entity) {
        entity.setField("fileName", getFullFileName((Date) entity.getField("date"), entity.getStringField("name")));
        return entity.getDataDefinition().save(entity);
    }

    public Entity updateFileName(final Entity entity, final String name) {
        entity.setField("fileName", getFullFileName((Date) entity.getField("date"), name));
        return entity.getDataDefinition().save(entity);
    }

    public Entity updateFileName(final Entity entity, final String dateFieldName, final String name) {
        entity.setField("fileName", getFullFileName((Date) entity.getField(dateFieldName), name));
        return entity.getDataDefinition().save(entity);
    }

    private String getFullFileName(final Date date, final String name) {
        return getProperReportPath() + name + "_" + new SimpleDateFormat(DateUtils.REPORT_DATE_TIME_FORMAT).format(date);
    }

    private String getProperReportPath() {
        return (path.endsWith("/") ? path : path + "/");
    }
}
