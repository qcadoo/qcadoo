package com.qcadoo.model.internal;

import java.math.MathContext;

import org.springframework.stereotype.Component;

import com.qcadoo.model.api.NumberService;

@Component
public class NumberServiceImpl implements NumberService {

    @Override
    public MathContext getMathContext() {
        return MathContext.DECIMAL64;
    }

}
