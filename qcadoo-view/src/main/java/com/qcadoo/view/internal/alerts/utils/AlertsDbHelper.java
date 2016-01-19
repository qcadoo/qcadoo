package com.qcadoo.view.internal.alerts.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.security.api.UserService;
import com.qcadoo.view.constants.QcadooViewConstants;

@Service
public class AlertsDbHelper {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private UserService userService;

    public void createViewedAlert(final Long id) {
        Entity viewedAlert = dataDefinitionService
                .get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_VIEWED_ALERT).create();
        Entity alert = dataDefinitionService.get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_ALERT).get(id);
        Entity user = userService.getCurrentUserEntity();
        viewedAlert.setField("user", user);
        viewedAlert.setField("alert", alert);
        viewedAlert.getDataDefinition().save(viewedAlert);
    }

    public List<Entity> getAlertsForUser() {
        Entity user = userService.getCurrentUserEntity();

        List<Entity> result = Lists.newArrayList();
        if (user != null) {
            List<Entity> alerts = dataDefinitionService
                    .get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_ALERT).find().list().getEntities();
            List<Entity> viewedAlerts = dataDefinitionService
                    .get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_VIEWED_ALERT).find()
                    .add(SearchRestrictions.belongsTo("user", user)).list().getEntities();
            result.addAll(alerts.stream()
                    .filter(alert -> viewedAlerts.stream()
                            .noneMatch(va -> va.getBelongsToField("alert").getId() == alert.getId()))
                    .collect(Collectors.toList()));
        }
        return result;
    }
}
