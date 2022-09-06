package com.qcadoo.view.internal.alerts.utils;

import com.google.common.collect.Lists;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.DataDefinitionService;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.security.api.UserService;
import com.qcadoo.view.constants.AlertFields;
import com.qcadoo.view.constants.QcadooViewConstants;
import com.qcadoo.view.constants.ViewedAlertFields;
import com.qcadoo.view.internal.alerts.model.AlertDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AlertsDbHelper {

    @Autowired
    private DataDefinitionService dataDefinitionService;

    @Autowired
    private UserService userService;

    public void createViewedAlerts(final List<AlertDto> alertDtos) {
        Entity user = userService.getCurrentUserEntity();
        
        alertDtos.forEach(alertDto -> createViewedAlert(alertDto.getId(), user));
    }

    private void createViewedAlert(final Long alertId, final Entity user) {
        Entity alert = getAlertDD().get(alertId);

        Entity viewedAlert = getViewedAlertDD().create();

        viewedAlert.setField(ViewedAlertFields.USER, user);
        viewedAlert.setField(ViewedAlertFields.ALERT, alert);

        viewedAlert.getDataDefinition().save(viewedAlert);
    }

    public List<AlertDto> getAlerts() {
        List<AlertDto> alertDtos = Lists.newArrayList();

        getAlertsForUser().forEach(alert -> mapToDTO(alert, alertDtos));

        return alertDtos;
    }

    private List<Entity> getAlertsForUser() {
        Entity user = userService.getCurrentUserEntity();

        List<Entity> result = Lists.newArrayList();

        DateTime currentDate = DateTime.now();

        if (Objects.nonNull(user)) {
            List<Entity> alerts = getAlertDD().find()
                    .add(SearchRestrictions.ge(AlertFields.EXPIRATION_DATE, currentDate.toDate())).list().getEntities();
            List<Entity> viewedAlerts = getViewedAlertDD().find()
                    .add(SearchRestrictions.belongsTo(ViewedAlertFields.USER, user))
                    .list().getEntities();

            result.addAll(alerts.stream().filter(alert -> viewedAlerts.stream()
                            .noneMatch(viewedAlert -> viewedAlert.getBelongsToField(ViewedAlertFields.ALERT).getId().equals(alert.getId())))
                    .collect(Collectors.toList()));
        }

        return result;
    }

    private void mapToDTO(final Entity alert, List<AlertDto> alertDtos) {
        AlertDto alertDto = new AlertDto();

        alertDto.setId(alert.getId());
        alertDto.setType(alert.getStringField(AlertFields.TYPE));
        alertDto.setMessage(alert.getStringField(AlertFields.MESSAGE));
        alertDto.setSound(alert.getBooleanField(AlertFields.SOUND));

        alertDtos.add(alertDto);
    }

    void registerAlert(final AlertDto alertDto) {
        Entity alert = getAlertDD().create();

        alert.setField(AlertFields.TYPE, alertDto.getType());
        alert.setField(AlertFields.MESSAGE, alertDto.getMessage());
        alert.setField(AlertFields.EXPIRATION_DATE, alertDto.getExpirationDate());
        alert.setField(AlertFields.SOUND,alertDto.isSound());

        alert.getDataDefinition().save(alert);
    }

    private DataDefinition getAlertDD() {
        return dataDefinitionService.get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_ALERT);
    }

    private DataDefinition getViewedAlertDD() {
        return dataDefinitionService.get(QcadooViewConstants.PLUGIN_IDENTIFIER, QcadooViewConstants.MODEL_VIEWED_ALERT);
    }

}
