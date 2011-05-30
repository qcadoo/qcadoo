package com.qcadoo.view.internal.components.file;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.qcadoo.model.api.file.FileUtils;
import com.qcadoo.view.internal.components.FieldComponentPattern;
import com.qcadoo.view.internal.components.FieldComponentState;

public class FileInputComponentState extends FieldComponentState {

    public static final String JSON_FILE_NAME = "fileName";

    public static final String JSON_FILE_URL = "fileUrl";

    public static final String JSON_FILE_LAST_MODIFICATION_DATE = "fileLastModificationDate";

    public FileInputComponentState(final FieldComponentPattern pattern) {
        super(pattern);
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = super.renderContent();

        if (getFieldValue() != null && StringUtils.hasText((String) getFieldValue())) {
            json.put(JSON_FILE_NAME, FileUtils.getInstance().getName((String) getFieldValue()));
            json.put(JSON_FILE_URL, FileUtils.getInstance().getUrl((String) getFieldValue()));
            json.put(JSON_FILE_LAST_MODIFICATION_DATE, FileUtils.getInstance().getLastModificationDate((String) getFieldValue()));
        } else {
            json.put(JSON_FILE_NAME, "");
            json.put(JSON_FILE_URL, "");
            json.put(JSON_FILE_LAST_MODIFICATION_DATE, "");
        }

        return json;
    }

}
