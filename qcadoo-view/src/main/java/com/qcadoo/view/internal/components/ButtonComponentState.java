/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0-SNAPSHOT
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
package com.qcadoo.view.internal.components;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.qcadoo.view.internal.states.AbstractComponentState;

public class ButtonComponentState extends AbstractComponentState {

    private static final String L_INITIALIZE = "initialize";

    public static final String JSON_BELONGS_TO_ENTITY_ID = "belongsToEntityId";

    public static final String JSON_OPEN_IN_MODAL = "openInModal";

    private final String correspondingView;

    private final String correspondingComponent;

    private final boolean correspondingViewInModal;

    private final String correspondingField;

    private final String url;

    private String value;

    private Long belongsToEntityId;

    public ButtonComponentState(final ButtonComponentPattern pattern) {
        super(pattern);
        this.url = pattern.getUrl();
        this.correspondingView = null;
        this.correspondingComponent = null;
        this.correspondingField = null;
        this.correspondingViewInModal = false;
        registerEvents();
    }

    public ButtonComponentState(final String correspondingField, final ButtonComponentPattern pattern) {
        super(pattern);
        this.url = null;
        this.correspondingField = correspondingField;
        this.correspondingView = pattern.getCorrespondingView();
        this.correspondingComponent = pattern.getCorrespondingComponent();
        this.correspondingViewInModal = pattern.isCorrespondingViewInModal();
        registerEvents();
    }

    private void registerEvents() {
        registerEvent(L_INITIALIZE, this, L_INITIALIZE);
        registerEvent("initializeAfterBack", this, L_INITIALIZE);
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {
        // empty
    }

    public void initialize(final String[] args) {
        refreshValue();
        requestRender();
    }

    @Override
    public void onScopeEntityIdChange(final Long scopeEntityId) {
        belongsToEntityId = scopeEntityId;
        refreshValue();
        requestRender();
    }

    @Override
    protected JSONObject renderContent() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_VALUE, value);
        json.put(JSON_OPEN_IN_MODAL, correspondingViewInModal);
        json.put(JSON_BELONGS_TO_ENTITY_ID, belongsToEntityId);
        return json;
    }

    private void refreshValue() {
        value = null;

        if (url == null) {
            value = correspondingView + ".html";
        } else {
            value = url;
        }

        if (correspondingComponent != null && belongsToEntityId != null) {

            value += "?context="
                    + new JSONObject(ImmutableMap.of(correspondingComponent + "." + correspondingField, belongsToEntityId))
                            .toString();
        }
    }

    @Override
    public final void setFieldValue(final Object value) {
        if (value == null) {
            this.value = null;
        } else {
            this.value = value.toString();
        }
    }

    @Override
    public final Object getFieldValue() {
        return value;
    }

}
