/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 0.3.0
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

package com.qcadoo.view.api;

/**
 * ViewDefinitionState is instance of single view. It is generated using {@link com.qcadoo.view.internal.api.ViewDefinition} in request
 * scope.
 * <p>
 * It contains all {@link com.qcadoo.view.api.ComponentState ComponentStates} of this view and other data necessary display this
 * view to client. Changing its data will also change state displayed to system user.
 * 
 * @since 0.4.0
 * 
 * @see com.qcadoo.view.internal.api.ViewDefinition
 * @see com.qcadoo.view.api.ComponentState
 */
public interface ViewDefinitionState extends ContainerState {

    /**
     * Performs event on this view. <b>For internal usage only</b>
     * 
     * @param path
     *            dotted separated path and name of component that send this event. If null than this event will be executed on
     *            all components inside this view.
     * @param event
     *            event name
     * @param args
     *            event additional arguments
     */
    void performEvent(String path, String event, String... args);

    /**
     * Returns component state with specified reference name or null if no such component found
     * 
     * @param reference
     *            reference name of component
     * @return component state with specified reference name
     */
    ComponentState getComponentByReference(String reference);

    /**
     * Informs client that should redirect to some url.
     * 
     * @param redirectToUrl
     *            target url of redirection
     * @param openInNewWindow
     *            true if client should open given url in new window
     * @param shouldSerialize
     *            true if before this redirection client should save window state
     */
    void redirectTo(String redirectToUrl, boolean openInNewWindow, boolean shouldSerialize);

    /**
     * Informs client that should open new modal window.
     * 
     * @param url
     *            target url of opened window
     */
    void openModal(String url);

    /**
     * Registers new component into this view.
     * 
     * @param reference
     *            reference name of newly registered component
     * @param state
     *            component state to register
     */
    void registerComponent(String reference, ComponentState state);

}
