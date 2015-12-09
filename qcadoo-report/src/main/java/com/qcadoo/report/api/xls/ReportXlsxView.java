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
package com.qcadoo.report.api.xls;

import com.qcadoo.report.api.xls.abstractview.AbstractXLSXView;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Convenient superclass for report Excel document views. Compatible with Apache POI 3.0 as well as 3.5, as of Spring 3.0.
 * 
 * This class is similar to the ReportPdfView class in usage style.
 * 
 */
public abstract class ReportXlsxView extends AbstractXLSXView {


    @Override
    protected void buildExcelDocument (final Map<String, Object> model, final Workbook workbook, final HttpServletRequest request, final HttpServletResponse response){
        String fileName = addContent(model, workbook, LocaleContextHolder.getLocale());
    }

    protected abstract String addContent(final Map<String, Object> model, final Workbook workbook, final Locale locale);

}
