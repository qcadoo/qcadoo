/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.5
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
package com.qcadoo.model.internal;

import static java.math.RoundingMode.HALF_EVEN;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.qcadoo.model.api.NumberService;

@Component
public class NumberServiceImpl implements NumberService {

    private static DecimalFormat decimalFormat = null;

    @PostConstruct
    public void init() {
        initialise();
    }

    private static void initialise() {
        decimalFormat = (DecimalFormat) DecimalFormat.getInstance(getLocale());
        decimalFormat.setMaximumFractionDigits(3);
        decimalFormat.setMinimumFractionDigits(3);
        decimalFormat.setRoundingMode(HALF_EVEN);
    }

    @Override
    public MathContext getMathContext() {
        return MathContext.DECIMAL64;
    }

    @Override
    public String format(Object obj) {
        return (obj == null) ? null : decimalFormat.format(obj);
    }

    @Override
    public BigDecimal setScale(BigDecimal decimal) {
        return decimal.setScale(3, HALF_EVEN);
    }
}
