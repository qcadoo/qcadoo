package com.qcadoo.view.internal.api;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.view.api.ComponentState;

public interface InternalComponentState extends ComponentState {

    /**
     * Initialize this component state using data from client. <b>For internal usage only</b>
     * 
     * @param json
     *            data from client
     * @param locale
     *            current localization
     * @throws JSONException
     *             when data from client contains errors
     */
    void initialize(JSONObject json, Locale locale) throws JSONException;

    /**
     * Renders this component state back to client. <b>For internal usage only</b>
     * 
     * @return data to client
     * @throws JSONException
     *             when data for client contains errors
     */
    JSONObject render() throws JSONException;
}
