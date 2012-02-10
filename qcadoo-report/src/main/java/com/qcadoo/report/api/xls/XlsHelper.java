package com.qcadoo.report.api.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface XlsHelper {

    void setCellStyle(HSSFSheet sheet, HSSFCell cell);

}