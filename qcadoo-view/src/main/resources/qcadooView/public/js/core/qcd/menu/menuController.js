/*
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.3
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
var QCD = QCD || {};
QCD.menu = QCD.menu || {};

QCD.menu.MenuController = function (menuStructure, windowController) {

    if (!(this instanceof QCD.menu.MenuController)) {
        return new QCD.menu.MenuController(menuStructure, windowController);
    }

    var that = this;

    var firstLevelElement;
    var secondLevelElement = $("#secondLevelMenu");

    var previousActive = {};
    previousActive.first = null;
    previousActive.second = null;

    var currentActive = {};
    currentActive.first = null;
    currentActive.second = null;

    var model = {};

    function constructor() {

        firstLevelElement = $("<div>").attr("id", "q_menu_row1");
        var q_row1 = $("<div>").attr("id", "q_row1");
        q_row1.append(firstLevelElement);
        var q_row1_out = $("<div>").attr("id", "q_row1_out");
        q_row1_out.append(q_row1);
        $("#firstLevelMenu").append(q_row1_out);

        createMenu(menuStructure);

        if (model.selectedItem) {
            previousActive.first = model.selectedItem;
            model.selectedItem.element.addClass("path");
            previousActive.second = model.selectedItem.selectedItem;
        }

        updateState();
        updateTopButtons();
        if (model.selectedItem && model.selectedItem.selectedItem) {
            changePage(model.selectedItem.selectedItem.page);
        } else {
            changePage("noDashboard.html");
        }
    }

    this.updateMenu = function () {
        var selectedFirstName = model.selectedItem.name;
        var selectedSecondName = model.selectedItem.selectedItem.name;
        $.ajax({
            url: "/menu.html",
            type: 'GET',
            dataType: 'text',
            contentType: 'plain/text; charset=utf-8',
            complete: function (XMLHttpRequest, textStatus) {
                if (XMLHttpRequest.status == 200) {
                    var responseText = $.trim(XMLHttpRequest.responseText);
                    if (responseText == "sessionExpired") {
                        return;
                    }
                    if (responseText.substring(0, 20) == "<![CDATA[ERROR PAGE:") {
                        return;
                    }
                    if (responseText != "") {
                        var response = JSON.parse(responseText);
                        firstLevelElement.children().remove();
                        createMenu(response);
                        model.selectedItem = model.itemsMap[selectedFirstName];
                        model.selectedItem.selectedItem = model.selectedItem.itemsMap[selectedSecondName];
                        updateState();
                    }
                    updateTopButtons();
                }
            }
        });
    };

    function updateTopButtons() {
        if (that.hasMenuPosition("administration.profile")) {
            $("#profileButton").show();
        } else {
            $("#profileButton").hide();
        }
    }

    function createMenu(menuStructure) {
        model = new QCD.menu.MenuModel(menuStructure);

        var menuContentElement = $("<ul>").addClass("q_row1");
        var menuHomeContentElement = $("<ul>").addClass("q_row1").addClass("q_row1_home");
        var menuAdministrationContentElement = $("<ul>").addClass("q_row1").addClass("q_row1_administration");
        firstLevelElement.append(menuHomeContentElement);
        firstLevelElement.append(menuContentElement);
        firstLevelElement.append(menuAdministrationContentElement);

        var itemsLen = model.items.length,
            i,
            item,
            firstLevelButton;

        for (i = 0; i < itemsLen; i++) {
            item = model.items[i];

            if (item.type == QCD.menu.MenuModel.ADMINISTRATION_CATEGORY) {
                firstLevelButton = $("<li>").html("<a href='#'><span><div class='administrationMenuItem' title='" + item.label + "'></div></span></a>").attr("id", "firstLevelButton_" + item.name);
                menuAdministrationContentElement.append(firstLevelButton);
            } else if (item.type == QCD.menu.MenuModel.HOME_CATEGORY) {
                firstLevelButton = $("<li>").html("<a href='#'><span><div class='homeMenuItem' title='" + item.label + "'></div></span></a>").attr("id", "firstLevelButton_" + item.name);
                menuHomeContentElement.append(firstLevelButton);
            } else {
                firstLevelButton = $("<li>").html("<a href='#'><span>" + item.label + "</span></a>").attr("id", "firstLevelButton_" + item.name);
                if (item.description) {
                    firstLevelButton.attr("title", item.description);
                }
                menuContentElement.append(firstLevelButton);
            }

            item.element = firstLevelButton;

            firstLevelButton.click(function (ignored) {
                onTopItemClick($(this));
            });
        }
    }

    function onTopItemClick(itemElement) {
        itemElement.children().blur();

        var buttonName = itemElement.attr("id").substring(17);

        model.selectedItem = model.itemsMap[buttonName];
        if (model.selectedItem.selectedItem) {
            currentActive.second = model.selectedItem.selectedItem
        }

        updateState();
    }

    function onBottomItemClick(itemElement) {
        itemElement.children().blur();

        if (!canChangePage()) {
            return;
        }

        changeTitle(itemElement);

        var buttonName = itemElement.attr("id").substring(18);

        model.selectedItem.selectedItem = model.selectedItem.itemsMap[buttonName];

        previousActive.first.element.removeClass("path");
        previousActive.first = model.selectedItem;
        previousActive.second = model.selectedItem.selectedItem;
        previousActive.first.element.addClass("path");

        updateState();

        changePage(model.selectedItem.selectedItem.page);
    }

    this.activateMenuPosition = function (position) {
        var menuParts = position.split(".");

        var topItem = model.itemsMap[menuParts[0]];
        var bottomItem;
        if (menuParts[1] && menuParts[1].indexOf(menuParts[0] + "_") != 0) {
            bottomItem = topItem.itemsMap[menuParts[0] + "_" + menuParts[1]];
        } else if (menuParts[1]) {
            bottomItem = topItem.itemsMap[menuParts[1]];
        }

        model.selectedItem.element.removeClass("path");

        topItem.selectedItem = bottomItem;
        previousActive.first = topItem;
        previousActive.second = bottomItem;
        model.selectedItem = topItem;

        updateState();

    };

    this.getCurrentActive = function () {
        return currentActive;
    };

    this.goToMenuPosition = function (position) {
        this.activateMenuPosition(position);
        if (model.selectedItem && model.selectedItem.selectedItem) {
            changePage(model.selectedItem.selectedItem.page);
        }
    };

    this.hasMenuPosition = function (position) {
        var menuParts = position.split(".");
        var topItem = model.itemsMap[menuParts[0]];
        if (topItem == null) {
            return false;
        }
        var bottomItem = topItem.itemsMap[menuParts[0] + "_" + menuParts[1]];
        return bottomItem != null;
    };

    this.restoreState = function () {
        model.selectedItem = previousActive.first;
        if (previousActive.second) {
            model.selectedItem.selectedItem = previousActive.second;
        }
        updateState();
    };

    function updateState() {
        if (currentActive.first != model.selectedItem) {
            if (currentActive.first) {
                currentActive.first.element.removeClass("activ");
                currentActive.first.selectedItem = null;
            }
            currentActive.first = model.selectedItem;
            currentActive.first.element.addClass("activ");

            updateSecondLevel();

        } else if (model.selectedItem) {
            if (currentActive.second != model.selectedItem.selectedItem) {
                if (currentActive.second) {
                    currentActive.second.element.removeClass("activ");
                }
                currentActive.second = model.selectedItem.selectedItem;
                if (currentActive.second) {
                    currentActive.second.element.addClass("activ");
                }
            }
        }
    }

    function updateSecondLevel() {
        secondLevelElement.children().remove();

        var menuContentElement = $("<ul>").addClass("q_row2");
        var q_menu_row2 = $("<div>").attr("id", "q_menu_row2");
        q_menu_row2.append(menuContentElement);
        var q_row2_out = $("<div>").attr("id", "q_row2_out");
        q_row2_out.append(q_menu_row2);
        secondLevelElement.append(q_row2_out);

        var selectedItemSubItemsLen = model.selectedItem.items.length,
            i,
            secondLevelItem,
            secondLevelButton;

        for (i = 0; i < selectedItemSubItemsLen; i++) {
            secondLevelItem = model.selectedItem.items[i];
            secondLevelButton = $("<li>").html(
                    "<a href='#'><span>" + secondLevelItem.label
                        + "</span></a>").attr("id",
                    "secondLevelButton_" + secondLevelItem.name);
            if (secondLevelItem.description) {
                secondLevelButton.attr("title", secondLevelItem.description);
            }
            menuContentElement.append(secondLevelButton);
            secondLevelItem.element = secondLevelButton;

            secondLevelButton.click(function () {
                onBottomItemClick($(this));
            });

            if (previousActive.second
                && previousActive.second.name == secondLevelItem.name) {
                secondLevelItem.element.addClass("activ");
                currentActive.second = secondLevelItem;
            }

        }
    }

    function changePage(page) {
        windowController.onMenuClicked(page);
    }

    function canChangePage() {
        return windowController.canChangePage();
    }

    function changeTitle(itemElement){
        var indexOfDash = window.parent.document.title.indexOf("-");
        var actualTitle = window.parent.document.title;
        window.parent.document.title = (indexOfDash == -1 ? actualTitle : actualTitle.substr(0,indexOfDash - 1)) + " - " + itemElement.context.textContent;
    }

    constructor();
};
