package com.qcadoo.report.internal.controller;

import com.qcadoo.report.api.ReportException;
import com.qcadoo.view.api.exception.ExceptionInfo;
import com.qcadoo.view.api.exception.ExceptionInfoResolver;

public class ReportExceptionInfoResolver implements ExceptionInfoResolver<ReportException> {

    @Override
    public ExceptionInfo getExceptionInfo(ReportException e) {

        String header = "qcadooReport.errorMessage." + e.getCode() + ".header";
        String message = "qcadooReport.errorMessage." + e.getCode() + ".explanation";

        return new ExceptionInfo(header, message, e.getArgs());
    }
}
