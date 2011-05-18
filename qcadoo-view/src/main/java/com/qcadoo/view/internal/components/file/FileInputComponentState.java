package com.qcadoo.view.internal.components.file;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

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
    public Object getFieldValue() {
        return "/tmp/files/1/2/342_filename_2010-04-01.pdf"; // TODO please remove me
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = super.renderContent();

        json.put(JSON_VALUE, getFieldValue()); // TODO please remove me

        if (getFieldValue() != null && StringUtils.hasText((String) getFieldValue())) {
            json.put(JSON_FILE_NAME, FielUtils.getName((String) getFieldValue()));
            json.put(JSON_FILE_URL, FielUtils.getUrl((String) getFieldValue()));
            json.put(JSON_FILE_LAST_MODIFICATION_DATE, FielUtils.getLastModificationDate((String) getFieldValue()));
        } else {
            json.put(JSON_FILE_NAME, "");
            json.put(JSON_FILE_URL, "");
            json.put(JSON_FILE_LAST_MODIFICATION_DATE, "");
        }

        return json;
    }

}
