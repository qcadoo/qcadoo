package com.qcadoo.report.api.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Helper for XLS style.
 * 
 * @since 1.1.3
 * 
 */
public interface XlsHelper {

    /**
     * Set header style for the given cell.
     * 
     * @param sheet
     *            cell worksheet
     * @param cell
     */
    void setCellStyle(HSSFSheet sheet, HSSFCell cell);

}