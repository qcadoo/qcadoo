package com.qcadoo.plugins.exportToPDF.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.qcadoo.localization.api.TranslationService;
import com.qcadoo.mes.basic.constants.BasicConstants;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.aop.Monitorable;
import com.qcadoo.model.api.file.FileService;
import com.qcadoo.report.api.pdf.PdfPageNumbering;
import com.qcadoo.report.api.pdf.PdfUtil;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.GridComponent;
import com.qcadoo.view.api.crud.CrudService;

@Controller
public class ExportToPDFController {

    private static final String VIEW_NAME_VARIABLE = "viewName";

    private static final String PLUGIN_IDENTIFIER_VARIABLE = "pluginIdentifier";

    private static final String CONTROLLER_PATH = "exportToPdf/{" + PLUGIN_IDENTIFIER_VARIABLE + "}/{" + VIEW_NAME_VARIABLE + "}";

    @Autowired
    private CrudService crudService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private TranslationService translationService;

    @Monitorable(threshold = 500)
    @RequestMapping(value = { CONTROLLER_PATH }, method = RequestMethod.POST)
    @ResponseBody
    public Object generatePdf(@PathVariable(PLUGIN_IDENTIFIER_VARIABLE) final String pluginIdentifier,
            @PathVariable(VIEW_NAME_VARIABLE) final String viewName, @RequestBody final JSONObject body, final Locale locale) {
        try {
            changeMaxResults(body);
            ViewDefinitionState state = crudService.invokeEvent(pluginIdentifier, viewName, body, locale);
            GridComponent grid = (GridComponent) state.getComponentByReference("grid");

            Document document = new Document(PageSize.A4.rotate());
            File file = new File(fileService.create("export.pdf"));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            writer.setPageEvent(new PdfPageNumbering(translationService.translate("qcadooReport.commons.page.label", locale),
                    translationService.translate("qcadooReport.commons.of.label", locale), translationService.translate(
                            "basic.company.tax.label", locale),
                    translationService.translate("basic.company.phone.label", locale), dataDefinitionService
                            .get(BasicConstants.PLUGIN_IDENTIFIER, BasicConstants.MODEL_COMPANY).find().uniqueResult()));
            document.setMargins(40, 40, 60, 60);
            document.addTitle("export.pdf");
            PdfUtil.addMetaData(document);
            writer.createXmpMetadata();
            document.open();

            int columns = 0;
            List<String> exportToPDFTableHeader = new ArrayList<String>();
            for (String name : grid.getColumnNames().values()) {
                exportToPDFTableHeader.add(name);
                columns++;
            }
            PdfPTable table = PdfUtil.createTableWithHeader(columns, exportToPDFTableHeader, false);

            for (Map<String, String> row : grid.getColumnValues()) {
                for (String value : row.values()) {
                    table.addCell(new Phrase(value, PdfUtil.getArialRegular9Dark()));
                }
            }
            document.add(table);

            PdfUtil.addEndOfDocument(document, writer,
                    translationService.translate("qcadooReport.commons.endOfPrint.label", locale));
            document.close();

            state.redirectTo(fileService.getUrl(file.getAbsolutePath()) + "?clean", true, false);
            return crudService.renderView(state);
        } catch (JSONException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (DocumentException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void changeMaxResults(final JSONObject json) throws JSONException {
        JSONObject component = getComponent(json, getComponentName(json));
        component.getJSONObject("content").put("firstEntity", 0);
        component.getJSONObject("content").put("maxEntities", Integer.MAX_VALUE);
    }

    private JSONObject getComponent(final JSONObject json, final String componentName) throws JSONException {
        String[] path = componentName.split("\\.");

        JSONObject component = json;

        for (String p : path) {
            component = component.getJSONObject("components").getJSONObject(p);
        }

        return component;
    }

    private String getComponentName(final JSONObject body) throws JSONException {
        return body.getJSONObject("event").getString("component");
    }

}
