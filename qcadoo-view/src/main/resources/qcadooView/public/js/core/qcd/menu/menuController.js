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
var QCD = QCD || {};
QCD.menu = QCD.menu || {};

QCD.menu.MenuController = function(menuStructure, windowController) {

	if (!(this instanceof QCD.menu.MenuController)) {
		return new QCD.menu.MenuController(menuStructure, windowController);
	}

	var that = this;

	var firstLevelElement = $(".headerMenuRowMain");
	var secondLevelElement = $(".headerMenuRowSub");

	var previousActive = {};
	previousActive.first = null;
	previousActive.second = null;

	var currentActive = {};
	currentActive.first = null;
	currentActive.second = null;

	var model = {};

	function constructor() {

		createMenu(menuStructure);

		if (model.selectedItem) {
			previousActive.first = model.selectedItem;
			model.selectedItem.element.addClass("path");
			previousActive.second = model.selectedItem.selectedItem;
		}
		//
		//        updateState();
		//        updateTopButtons();
		if (model.selectedItem && model.selectedItem.selectedItem) {
			fillCurrentPage();
			that.setPageTitle();
			changePage(model.selectedItem.selectedItem.page);
		} else {
			changePage("noDashboard.html");
		}
	}

	this.updateMenu = function() {
		var selectedFirstName = model.selectedItem.name;
		var selectedSecondName = model.selectedItem.selectedItem.name;
		$
				.ajax({
					url : "/menu.html",
					type : 'GET',
					dataType : 'text',
					contentType : 'plain/text; charset=utf-8',
					complete : function(XMLHttpRequest, textStatus) {
						if (XMLHttpRequest.status == 200) {
							var responseText = $
									.trim(XMLHttpRequest.responseText);
							if (responseText == "sessionExpired") {
								return;
							}
							if (responseText.substring(0, 20) == "<![CDATA[ERROR PAGE:") {
								return;
							}
							if (responseText != "") {
								var response = JSON.parse(responseText);
								//$(".mainMenu").children().remove();
								//secondLevelElement.children().remove();
								//createMenu(response);
								model.selectedItem = model.itemsMap[selectedFirstName];
								model.selectedItem.selectedItem = model.selectedItem.itemsMap[selectedSecondName];
								//                        updateState();
							}
							//                    updateTopButtons();
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

		var menuContentElement = $(".mainMenu");//.addClass("q_row1");

		var itemsLen = model.items.length, i, item, firstLevelButton;

		for (i = 0; i < itemsLen; i++) {
			item = model.items[i];

			firstLevelButton = $("<li>").html(
					"<a class='not-active' href='menu-" + item.name + "'>"
							+ item.label + "</a>").attr("id",
					"firstLevelButton_" + item.name);
			if (item.description) {
				firstLevelButton.attr("title", item.description);
			}
			if (item.type == QCD.menu.MenuModel.HOME_CATEGORY) {
				firstLevelButton.hide();
			}
			menuContentElement.append(firstLevelButton);

			var secondItemsLen = item.items.length, j, secondLevelItem, secondLevelButton;
			var secondMenuContentElement = $("<div>").attr("id",
					"menu-" + item.name);
			secondMenuContentElement.addClass("subMenuBox");
			secondMenuContentElement.addClass("subMenuBoxLiveSearch");

			var secondLevelContent = $("<ul>").addClass("subMenu").attr("id",
					"list-" + item.name);

			for (j = 0; j < secondItemsLen; j++) {
				secondLevelItem = item.items[j];
				secondLevelButton = $("<li>").html(
						"<a href='#'><span>" + secondLevelItem.label
								+ "</span></a>").attr("id",
						"secondLevelButton_" + secondLevelItem.name);
				if (secondLevelItem.description) {
					secondLevelButton
							.attr("title", secondLevelItem.description);
				}
				secondLevelButton.appendTo(secondLevelContent);
				secondLevelItem.element = secondLevelButton;

				secondLevelButton.click(function() {
					onBottomItemClick($(this));
				});

				if (item.type == QCD.menu.MenuModel.HOME_CATEGORY) {
					secondLevelButton.hide();
				}
				secondLevelContent.appendTo(secondMenuContentElement);
				secondMenuContentElement.appendTo(secondLevelElement);
			}

			item.element = firstLevelButton;
		}

		var searchBoxEmpty = $("<div>")
				.html(
						"<i class='icon iconInfo'></i><div>Brak elementów pasujących<br />do zapytania</div>")
				.attr("id", "emptySearchResult");
		searchBoxEmpty.addClass("subMenuBox");
		searchBoxEmpty.appendTo(secondLevelElement);

		var searchBoxTooMany = $("<div>")
				.html(
						"<i class='icon iconInfo'></i><div>Znaleziono zbyt wiele<br />wyników</div>")
				.attr("id", "tooManySearchResult");
		searchBoxTooMany.addClass("subMenuBox");
		searchBoxTooMany.appendTo(secondLevelElement);

		var searchBoxResult = $("<div>").html("<ul class='subMenu'></ul>")
				.attr("id", "searchResult");
		searchBoxResult.addClass("subMenuBox");
		searchBoxResult.appendTo(secondLevelElement);

		menuContentElement.menuAim({
			activate : activateSubmenu,
			deactivate : deactivateSubmenu
		});
		// ************ lazy menu show item
		function activateSubmenu(row) {
			deactivateSubmenu($('.maintainHover', menuContentElement).parent());

			var $row = $(row);
			$row.find("a").addClass("maintainHover");
			var target = $row.find("a").attr('href');
			$("#" + target).show();

			onTopItemClick(row);
		}

		// ************ lazy menu hide item
		function deactivateSubmenu(row) {
			$('.subMenu .maintainHover').removeClass('maintainHover');

			var $row = $(row);
			$row.find("a").removeClass("maintainHover");
			var target = $row.find("a").attr('href');
			$("#" + target).hide();
		}
	}

	function onTopItemClick(itemElement) {
		//        itemElement.children().blur();
		itemElement = $(itemElement);

		var buttonName = itemElement.attr("id").substring(17);

		model.selectedItem = model.itemsMap[buttonName];
		if (model.selectedItem.selectedItem) {
			currentActive.second = model.selectedItem.selectedItem
		}

		//        updateState();
	}

	function onBottomItemClick(itemElement) {
		//        itemElement.children().blur();

		if (!canChangePage()) {
			return;
		}

		var buttonName = itemElement.attr("id").substring(18);

		model.selectedItem.selectedItem = model.selectedItem.itemsMap[buttonName];

		previousActive.first.element.removeClass("path");
		previousActive.first = model.selectedItem;
		previousActive.second = model.selectedItem.selectedItem;
		previousActive.first.element.addClass("path");

		//updateState();
		$('.subMenu .currentActive').removeClass('currentActive');
		itemElement.find('a').addClass('currentActive');

		$('.mainMenu .currentMainActive').removeClass('currentMainActive');
		$('.mainMenu .maintainHover').addClass('currentMainActive');
		$('.userMenuBackdoor').click();

		changeTitle(itemElement);
		fillCurrentPage();
		changePage(model.selectedItem.selectedItem.page);

	}

	this.activateMenuPosition = function(position) {
		var menuParts = position.split(".");

		var topItem = model.itemsMap[menuParts[0]];
		var bottomItem;
		if (topItem) {
			if (menuParts[1] && menuParts[1].indexOf(menuParts[0] + "_") != 0) {
				bottomItem = topItem.itemsMap[menuParts[0] + "_" + menuParts[1]];
			} else if (menuParts[1]) {
				bottomItem = topItem.itemsMap[menuParts[1]];
			}
		}

		if (bottomItem) {
			$('.subMenu .currentActive').removeClass('currentActive');
			bottomItem.element.find('a').addClass('currentActive');
		}
		if (topItem) {
			$('.mainMenu .maintainHover').removeClass('maintainHover');
			$('.mainMenu .currentMainActive').removeClass('currentMainActive');
			topItem.element.find("a").addClass("maintainHover");
			topItem.element.find("a").addClass("currentMainActive");
		}
		if (topItem && bottomItem) {
			model.selectedItem.element.removeClass("path");

			topItem.selectedItem = bottomItem;

			previousActive.first = topItem;
			previousActive.second = bottomItem;
			model.selectedItem = topItem;

			model.selectedItem.selectedItem = bottomItem;
			if (model.selectedItem && model.selectedItem.selectedItem) {
				changeTitle(model.selectedItem.selectedItem.element);
			}
			fillCurrentPage();
			updateState();
		}
	};

	this.getCurrentActive = function() {
		return currentActive;
	};

	this.goToMenuPosition = function(position) {
		this.activateMenuPosition(position);
		if (model.selectedItem && model.selectedItem.selectedItem) {
			changePage(model.selectedItem.selectedItem.page);
		}
	};

	this.hasMenuPosition = function(position) {
		var menuParts = position.split(".");
		var topItem = model.itemsMap[menuParts[0]];
		if (topItem == null) {
			return false;
		}
		var bottomItem = topItem.itemsMap[menuParts[0] + "_" + menuParts[1]];
		return bottomItem != null;
	};

	this.restoreState = function() {
		model.selectedItem = previousActive.first;
		if (previousActive.second) {
			model.selectedItem.selectedItem = previousActive.second;
		}
		//updateState();
	};

	function updateState() {
		if (currentActive.first != model.selectedItem) {
			if (currentActive.first) {
				currentActive.first.element.removeClass("activ");
				currentActive.first.selectedItem = null;
			}
			currentActive.first = model.selectedItem;
			currentActive.first.element.addClass("activ");

			//updateSecondLevel();

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

	function changePage(page) {
		windowController.onMenuClicked(page);
	}

	function canChangePage() {
		return windowController.canChangePage();
	}

	function changeTitle(itemElement) {
		var indexOfDash = window.parent.document.title.indexOf("-");
		var actualTitle = window.parent.document.title;
		var title;
		if (itemElement.context) {
			title = itemElement.context.textContent;
		} else if (model.selectedItem && model.selectedItem.selectedItem) {
			title = model.selectedItem.selectedItem.label;
		}
		if (title !== undefined) {
			window.parent.document.title = (indexOfDash == -1
					? actualTitle
					: actualTitle.substr(0, indexOfDash - 1))
					+ " - " + title;
		} else {
			window.parent.document.title = (indexOfDash == -1
					? actualTitle
					: actualTitle.substr(0, indexOfDash - 1));
		}
	}

	function fillCurrentPage() {
		var container = $(".pageTitle");
		if (model.selectedItem && model.selectedItem.selectedItem) {
			container.children().remove();
			var category = $("<span>").html(model.selectedItem.label).addClass(
					"category");
			category.appendTo(container);

			var currentPage = $("<h1>" + model.selectedItem.selectedItem.label
					+ "</h1>");
			currentPage.appendTo(container);
		}

	};
	this.fillBreadCrumbs = function() {
		fillCurrentPage();
	}
	this.setPageTitle = function() {
		var indexOfDash = window.parent.document.title.indexOf("-");
		var actualTitle = window.parent.document.title;
		var title;
		if (model.selectedItem && model.selectedItem.selectedItem) {
			title = model.selectedItem.selectedItem.label;
		}
		if (title !== undefined) {
			window.parent.document.title = (indexOfDash == -1
					? actualTitle
					: actualTitle.substr(0, indexOfDash - 1))
					+ " - " + title;
		} else {
			window.parent.document.title = (indexOfDash == -1
					? actualTitle
					: actualTitle.substr(0, indexOfDash - 1));
		}
	}

	constructor();

};