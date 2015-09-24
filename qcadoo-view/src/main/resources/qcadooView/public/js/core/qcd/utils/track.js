/*
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.4
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
var QCDTrack = QCDTrack || {};
 
QCDTrack.track = function (component, eventName, trackEvent) {
 
    function resolveEvent() {
        if (typeof trackEvent === 'function') {
            return trackEvent();
        } else if (typeof trackEvent === 'string') {
            return trackEvent;
        } else {
            throw "Unsupported event object: " + trackEvent;
        }
    }

    if (typeof component === 'undefined' || component === null) {
        QCD.error("Can't assign mixpanel's listener to an empty component ");
        return;
    }

    if (parent.mixpanel) {
        var listener = {};
        listener[eventName] = function () {
 
            var hostname = parent.window.location.hostname;
 
            try {
                parent.mixpanel.track(resolveEvent(),
                    {
                        'username' : parent.window.getCurrentUserLogin(),
                        'url' : hostname
                    }
                );
            } catch (e) {
                console.error("Tracking for event '" + eventName + "' aborted. Cause: " + e);
            }
        };
        component.addOnChangeListener(listener);
 
    }
};


