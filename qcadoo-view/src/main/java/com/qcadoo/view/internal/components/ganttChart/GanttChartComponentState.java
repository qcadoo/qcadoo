/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.2
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
package com.qcadoo.view.internal.components.ganttChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qcadoo.model.internal.api.ValueAndError;
import com.qcadoo.model.internal.types.DateType;
import com.qcadoo.view.api.components.ganttChart.GanttChartItem;
import com.qcadoo.view.api.components.ganttChart.GanttChartItemResolver;
import com.qcadoo.view.internal.components.ganttChart.GanttChartScaleImpl.ZoomLevel;
import com.qcadoo.view.internal.states.AbstractComponentState;

public class GanttChartComponentState extends AbstractComponentState {

    public final GanttChartComponentEventPerformer eventPerformer = new GanttChartComponentEventPerformer();

    private GanttChartScaleImpl scale;

    private String dateFromErrorMessage;

    private String dateToErrorMessage;

    private String globalErrorMessage;

    Map<String, List<GanttChartItem>> items;

    Map<String, List<GanttChartItem>> collisionItems;

    protected static final DateType dateType = new DateType();

    private final GanttChartItemResolver itemResolver;

    private final int defaultStartDay;

    private final int defaultEndDay;

    private final ZoomLevel defaultZoomLevel;

    private Long selectedEntityId;

    public GanttChartComponentState(final GanttChartItemResolver itemResolver, final ZoomLevel defaultZoomLevel,
            final int defaultStartDay, final int defaultEndDay) {
        this.itemResolver = itemResolver;
        this.defaultZoomLevel = defaultZoomLevel;
        this.defaultStartDay = defaultStartDay;
        this.defaultEndDay = defaultEndDay;
        registerEvent("refresh", eventPerformer, "refresh");
        registerEvent("initialize", eventPerformer, "initialize");
        registerEvent("select", eventPerformer, "selectEntity");
    }

    @Override
    public Object getFieldValue() {
        return selectedEntityId;
    }

    @Override
    protected void initializeContent(final JSONObject json) throws JSONException {

        JSONObject headerDataObject = json.getJSONObject("headerParameters");

        ZoomLevel zoomLevel = ZoomLevel.valueOf(headerDataObject.getString("scale"));

        String dateFromString = headerDataObject.getString("dateFrom");
        String dateToString = headerDataObject.getString("dateTo");

        DateTime now = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);

        Date dateFrom = now.plusDays(defaultStartDay).toDate();
        Date dateTo = now.plusDays(defaultEndDay).toDate();

        if (dateFromString == null || "".equals(dateFromString)) {
            dateFromErrorMessage = translate("errorMessage.emptyDate");
        } else {
            ValueAndError dateFromVaE = dateType.toObject(null, dateFromString);
            if (dateFromVaE.getMessage() == null) {
                dateFrom = (Date) dateFromVaE.getValue();
            } else {
                dateFromErrorMessage = translate("errorMessage.dateNotValid");
            }
        }

        if (dateToString == null || "".equals(dateToString)) {
            dateToErrorMessage = translate("errorMessage.emptyDate");
        } else {
            ValueAndError dateToVaE = dateType.toObject(null, dateToString);
            if (dateToVaE.getMessage() == null) {
                dateTo = (Date) dateToVaE.getValue();
            } else {
                dateToErrorMessage = translate("errorMessage.dateNotValid");
            }
        }

        scale = new GanttChartScaleImpl(this, zoomLevel, dateFrom, dateTo);

        if (dateFromErrorMessage == null && globalErrorMessage == null) {
            if (scale.isFromLargerThanTo()) {
                globalErrorMessage = translate("errorMessage.fromLargerThanTo");
            } else if (scale.isTooLargeRange()) {
                globalErrorMessage = translate("errorMessage.tooLargeRange", "" + scale.getMaxRangeInMonths());
            }
        }

        if (json.has("selectedEntityId")) {
            selectedEntityId = json.getLong("selectedEntityId");
        }

    }

    @Override
    protected JSONObject renderContent() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("zoomLevel", scale.getZoomLevel().toString());

        json.put("dateFromErrorMessage", dateFromErrorMessage);
        json.put("dateToErrorMessage", dateToErrorMessage);
        if (dateFromErrorMessage == null) {
            json.put("dateFrom", dateType.toString(scale.getDateFrom(), getLocale()));
        }
        if (dateToErrorMessage == null) {
            json.put("dateTo", dateType.toString(scale.getDateTo(), getLocale()));
        }

        json.put("globalErrorMessage", globalErrorMessage);

        if (globalErrorMessage == null) {
            json.put("scale", scale.getAsJson());

            JSONArray rowsArray = new JSONArray();
            JSONArray itemsArray = new JSONArray();

            for (Map.Entry<String, List<GanttChartItem>> entry : items.entrySet()) {
                rowsArray.put(entry.getKey());
                for (GanttChartItem item : entry.getValue()) {
                    if (item != null) {
                        itemsArray.put(convertItemToJson(item));
                    }
                }
            }

            json.put("rows", rowsArray);
            json.put("items", itemsArray);

            JSONArray collisionItemsArray = new JSONArray();
            for (Map.Entry<String, List<GanttChartItem>> entry : collisionItems.entrySet()) {
                for (GanttChartItem item : entry.getValue()) {
                    if (item != null) {
                        collisionItemsArray.put(convertItemToJson(item));
                    }
                }
            }
            json.put("collisions", collisionItemsArray);
        }

        return json;
    }

    private JSONObject convertItemToJson(final GanttChartItem item) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("row", item.getRowName());
        json.put("id", item.getEntityId());
        json.put("from", item.getFrom());
        json.put("to", item.getTo());

        // type: 'disabled',

        JSONObject info = new JSONObject();
        info.put("name", item.getName());
        info.put("dateFrom", item.getDateFrom());
        info.put("dateTo", item.getDateTo());
        json.put("info", info);

        return json;
    }

    protected String translate(final String suffix, final String... args) {
        return getTranslationService().translate(getTranslationPath() + "." + suffix, "qcadooView.gantt." + suffix, getLocale(),
                args);
    }

    protected class GanttChartComponentEventPerformer {

        public void initialize(final String[] args) {
            DateTime now = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            scale = new GanttChartScaleImpl(GanttChartComponentState.this, defaultZoomLevel, now.plusDays(defaultStartDay)
                    .toDate(), now.plusDays(defaultEndDay).toDate());
            dateFromErrorMessage = null;
            dateToErrorMessage = null;
            globalErrorMessage = null;
            refresh(args);
        }

        public void refresh(final String[] args) {
            if (globalErrorMessage != null) {
                return;
            }
            items = itemResolver.resolve(scale);
            updateCollisionItems();
            requestRender();
            requestUpdateState();
        }

        public void selectEntity(final String[] args) {
            notifyEntityIdChangeListeners(selectedEntityId);
        }

        private void updateCollisionItems() {
            collisionItems = new HashMap<String, List<GanttChartItem>>();
            for (Entry<String, List<GanttChartItem>> rowEntry : items.entrySet()) {

                List<GanttChartItem> sortedItems = new ArrayList<GanttChartItem>(rowEntry.getValue());
                Collections.sort(sortedItems, new Comparator<GanttChartItem>() {

                    @Override
                    public int compare(GanttChartItem arg0, GanttChartItem arg1) {
                        return Double.valueOf(arg0.getFrom()).compareTo(Double.valueOf(arg1.getFrom()));
                    }
                });

                List<GanttChartItem> collisionRow = getCollisionsList(sortedItems, rowEntry.getKey());
                if (!collisionRow.isEmpty()) {
                    collisionItems.put(rowEntry.getKey(), collisionRow);
                }

            }
        }

        private List<GanttChartItem> getCollisionsList(final List<GanttChartItem> sortedItems, final String row) {

            List<GanttChartItem> collisionRow = new ArrayList<GanttChartItem>();

            GanttChartModifiableItem collisionItem = null;
            GanttChartItem previousItem = null;

            for (GanttChartItem item : sortedItems) {

                if (previousItem != null && item.getFrom() < previousItem.getTo()) {

                    if (collisionItem != null && item.getFrom() < collisionItem.getTo()) { // same collision

                        if (item.getTo() > collisionItem.getTo()) { // not entirely included in existing collision
                            collisionItem.setTo(item.getTo());
                            collisionItem.setDateTo(item.getDateTo());
                        }

                        collisionItem.setName(collisionItem.getName() + "<div>- " + item.getName() + "</div>");

                    } else { // different collision

                        if (collisionItem != null) {
                            collisionRow.add(collisionItem);
                        }

                        String collisionName = translate("collisionItemHeader") + "<div>- " + previousItem.getName()
                                + "</div><div>- " + item.getName() + "</div>";

                        if (item.getTo() < previousItem.getTo()) { // entirely included in previous item
                            collisionItem = new GanttChartItemImpl(row, collisionName, null, item.getDateFrom(),
                                    item.getDateTo(), item.getFrom(), item.getTo());
                        } else {
                            collisionItem = new GanttChartItemImpl(row, collisionName, null, item.getDateFrom(),
                                    previousItem.getDateTo(), item.getFrom(), previousItem.getTo());
                        }

                    }

                }

                previousItem = item;
            }

            if (collisionItem != null) {
                collisionRow.add(collisionItem);
            }

            return collisionRow;
        }
    }

}
