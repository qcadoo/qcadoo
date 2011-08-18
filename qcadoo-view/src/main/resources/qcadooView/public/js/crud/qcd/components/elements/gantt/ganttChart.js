/*
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.6
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
QCD.components = QCD.components || {};
QCD.components.elements = QCD.components.elements || {};

QCD.components.elements.GanttChart = function(_element, _mainController) {
	$.extend(this, new QCD.components.elements.FormComponent(_element, _mainController));
	
	var element = _element;
	
	var mainController = _mainController;
	
	var htmlElements = {};
	
	var _this = this;
	
	var constants = {
		CELL_WIDTH: 25,
		CELL_HEIGHT: 30,
		HEADER_HEIGHT: 60,
		ROW_NAMES_WIDTH: 200,
		RIGHT_SCROLL_WIDTH: 17,
		BOTTOM_SCROLL_HEIGHT: 16
	};
	
	var currentCellSettings;
	
	var rowsByName = {};
	var rowsByIndex = [];
	
	var currentWidth;
	var isScrollVisible = false;
	
	var header;
	
	var selectedItem;
	
	var collisionInfoBoxContent;
	var collisionInfoBoxOverlay;
	
	function constructor() {
		createGrantt();
		QCD.components.elements.utils.LoadingIndicator.blockElement(element);
		header.init();
	}
	
	this.getComponentValue = function() {
		var headerParameters = header.getCurrentParameters();
		var data = {
			headerParameters: headerParameters
		};
		if (selectedItem && selectedItem[0].entityId) {
			data.selectedEntityId = selectedItem[0].entityId;
		}
		return data;
	}
	
	this.setComponentValue = function(value) {
		applySettings(value);
		header.enableButtons();
		header.setDateFromValue(value.dateFrom, value.dateFromErrorMessage);
		header.setDateToValue(value.dateTo, value.dateToErrorMessage);
		header.setDateToValue(value.dateTo, value.dateToErrorMessage);
		header.setGlobalErrorMessage(value.globalErrorMessage);
		if (value.selectedEntityId) {
			if (selectedItem) {
				selectedItem.removeClass("ganttItemSelected");	
			}
			var newSelectedItem = $("#"+_this.elementSearchName+"_item_"+value.selectedEntityId);
			selectedItem = newSelectedItem;
			newSelectedItem.addClass("ganttItemSelected");
		}
		collisionInfoBoxOverlay.hide();
		QCD.components.elements.utils.LoadingIndicator.unblockElement(element);
	}
	
	this.performInitialize = function() {
		refreshContent();
	}
	
	this.setComponentLoading = function(isLoadingVisible) {
		if (isLoadingVisible) {
			QCD.components.elements.utils.LoadingIndicator.blockElement(element);
			header.disableButtons();
		} else {
			QCD.components.elements.utils.LoadingIndicator.unblockElement(element);
			header.enableButtons();
		}
	}
	
	this.onScaleChanged = function(newScale) {
		header.setCurrentScale(newScale);
		refreshContent();
	}
	
	this.onDateChanged = function() {
		refreshContent();
	}
	
	function onSelectChange() {
		if (_this.options.listeners.length > 0) {
			mainController.callEvent("select", _this.elementPath, null);
		}
	}
	
	function refreshContent() {
		QCD.components.elements.utils.LoadingIndicator.blockElement(element);
		mainController.callEvent("refresh", _this.elementPath);
	}
	
	
	function applySettings(cellSettings) {
		if (cellSettings.globalErrorMessage) {
			return;
		}
		if (! cellSettings.scale) {
			return;
		}
		// TODO mina remove old items when scale or rows wasn't changed 
		//if (currentCellSettings && hasSameScaleAndRows(currentCellSettings, cellSettings)) {
		//	updateItems(cellSettings.items);
		//	return;
		//}
		updateHeader(cellSettings);
		
		htmlElements.rowNamesConteiner.children().remove();
		htmlElements.rowsContainer.children().remove();
		
		htmlElements.centerContentWrapper.width(constants.CELL_WIDTH * getTotalNumberOfCells(cellSettings));
		
		rowsByName = {};
		rowsByIndex = [];
		for (var i=0; i<cellSettings.rows.length; i++) {
			rowsByIndex[i] = addRow(cellSettings, cellSettings.rows[i]);
		}
		htmlElements.scrollElement.height(cellSettings.rows.length * constants.CELL_HEIGHT);
		updateScroll();
		
		updateItems(cellSettings.items, cellSettings.collisions);
		
		currentCellSettings = cellSettings;
	}
	
	function updateItems(items, collisions) {
		for (var itemIndex in items) {
			var item = items[itemIndex];
			addItem(item, false);
		}
		for (var itemIndex in collisions) {
			var item = collisions[itemIndex];
			addItem(item, true);
		}
	}
	
	function hasSameScaleAndRows(cellSettings1, cellSettings2) {
		if (cellSettings1.scale.elementsInCategory != cellSettings2.scale.elementsInCategory ||
			cellSettings1.scale.elementLabelsInterval != cellSettings2.scale.elementLabelsInterval ||
			cellSettings1.scale.elementLabelInitialNumber != cellSettings2.scale.elementLabelInitialNumber ||
			cellSettings1.scale.categories.length != cellSettings2.scale.categories.length ||
			cellSettings1.scale.firstCategoryFirstElement != cellSettings2.scale.firstCategoryFirstElement ||
			cellSettings1.scale.lastCategoryLastElement != cellSettings2.scale.lastCategoryLastElement ||
			cellSettings1.rows.length != cellSettings2.rows.length) {
			return false;
		}
		for (var i=0; i<cellSettings1.scale.categories.length; i++) {
			if (cellSettings1.scale.categories[i] != cellSettings2.scale.categories[i]) {
				return false;
			}
		}
		for (var i=0; i<cellSettings1.rows.length; i++) {
			if (cellSettings1.rows[i] != cellSettings2.rows[i]) {
				return false;
			}
		}
		return true;
	}
	
	function showCollisionBox(ganttItem) {
		collisionInfoBoxContent.children().remove();
		for (var i=0; i<ganttItem.items.length; i++) {
			var collisionItem = ganttItem.items[i];
			
			var collisionItemElement = $("<div>").addClass("collisionInfoBoxItem").html(collisionItem.info.name);
			collisionItemElement.attr("id", _this.elementPath+"_collisionItem_"+collisionItem.id);
			collisionItemElement[0].entityId = collisionItem.id;
			
			collisionItemElement.click(function() {
				var itemElement = $(this);
				var itemId = this.entityId;
				if (selectedItem) {
					selectedItem.removeClass("ganttItemSelected");
					$("#"+_this.elementSearchName+"_collisionItem_"+selectedItem[0].entityId).removeClass("ganttItemSelected");
				}
				var ganttElement = $("#"+_this.elementSearchName+"_item_"+itemId);
				selectedItem = ganttElement;
				itemElement.addClass("ganttItemSelected");
				ganttElement.addClass("ganttItemSelected");
				onSelectChange();
			});
			
			collisionInfoBoxContent.append(collisionItemElement);
		}
		collisionInfoBoxOverlay.show();
		if (selectedItem) {
			$("#"+_this.elementSearchName+"_collisionItem_"+selectedItem[0].entityId).addClass("ganttItemSelected");
		}
	}
	
	function createGrantt() {
		header = new QCD.components.elements.GanttChartHeader(_this, _this.elementPath+"_header", _this.options.translations, _this.options);
		element.append(header.getHeaderElement());
		
		htmlElements.wrapper = $("<div>").addClass("ganttContainer");
		element.append(htmlElements.wrapper);
		
		htmlElements.rowNamesWrapper = $("<div>").addClass("ganttRowNamesWrapper");
		htmlElements.rowNamesWrapper.width(constants.ROW_NAMES_WIDTH-1);
		htmlElements.rowNamesWrapper.height("100%");	
		htmlElements.wrapper.append(htmlElements.rowNamesWrapper);
		
		htmlElements.centerContainer = $("<div>").addClass("ganttCenterConteiner");
		htmlElements.centerContainer.css("left",constants.ROW_NAMES_WIDTH+"px");
		htmlElements.wrapper.append(htmlElements.centerContainer);
		
		htmlElements.scrollWrapper = $("<div>").addClass("ganttScrollWrapper").hide();
		htmlElements.scrollWrapper.width(50);
		htmlElements.wrapper.append(htmlElements.scrollWrapper);
		
		// NAMES
		htmlElements.rowNamesButtonsConteiner = $("<div>").addClass("ganttRowNamesButtonsConteiner");
		htmlElements.rowNamesButtonsConteiner.height(constants.HEADER_HEIGHT-1);
		htmlElements.rowNamesWrapper.append(htmlElements.rowNamesButtonsConteiner);		
		
		htmlElements.rowNamesConteiner = $("<div>").addClass("ganttRowNamesConteiner");
		htmlElements.rowNamesWrapper.append(htmlElements.rowNamesConteiner);
		
		// CENTER
		htmlElements.centerContentWrapper = $("<div>").addClass("ganttCenterContentWrapper");
		htmlElements.centerContainer.append(htmlElements.centerContentWrapper);
		
		htmlElements.topRow = $("<div>").addClass("ganttTopRow");
		htmlElements.topRow.height(constants.HEADER_HEIGHT-1);
		htmlElements.centerContentWrapper.append(htmlElements.topRow);
		
		htmlElements.topRow1 = $("<div>").addClass("ganttTopRow1");
		htmlElements.topRow1.height((constants.HEADER_HEIGHT/2)-1);
		htmlElements.topRow1.css("line-height",((constants.HEADER_HEIGHT/2)-1) + "px");
		htmlElements.topRow.append(htmlElements.topRow1);
		htmlElements.topRow2 = $("<div>").addClass("ganttTopRow2");
		htmlElements.topRow2.height((constants.HEADER_HEIGHT/2));
		htmlElements.topRow2.css("line-height",((constants.HEADER_HEIGHT/2)-1) + "px");
		htmlElements.topRow.append(htmlElements.topRow2);
		
		htmlElements.rowsContainerWrapper = $("<div>").addClass("rowsContainerWrapper");
		htmlElements.centerContentWrapper.append(htmlElements.rowsContainerWrapper);
		
		htmlElements.rowsContainer = $("<div>").addClass("rowsContainer");
		htmlElements.rowsContainerWrapper.append(htmlElements.rowsContainer);
	
		// SCROLL
		htmlElements.scrollContainer = $("<div>").addClass("ganttScrollConteiner");
		htmlElements.scrollContainer.css("margin-top",(constants.HEADER_HEIGHT-1)+"px");
		htmlElements.scrollWrapper.append(htmlElements.scrollContainer);
		
		htmlElements.scrollElement = $("<div>");
		htmlElements.scrollElement.width(1);
		htmlElements.scrollElement.height(0);
		htmlElements.scrollContainer.append(htmlElements.scrollElement);
		
		htmlElements.scrollContainer.scroll(function(eventObject) {
			var scrollTop = htmlElements.scrollContainer.scrollTop();
			htmlElements.rowsContainerWrapper.scrollTop(scrollTop);
			htmlElements.rowNamesConteiner.scrollTop(scrollTop);
		});
		
		var collisionInfoBox = $("<div>").addClass("collisionInfoBox").click(function(){return false;});
		var collisionInfoBoxWrapper = $("<div>").addClass("collisionInfoBoxWrapper");
		collisionInfoBoxOverlay = $("<div>").addClass("collisionInfoBoxOverlay").click(function(){$(this).hide()});
		collisionInfoBoxOverlay.append(collisionInfoBoxWrapper);
		collisionInfoBoxWrapper.append(collisionInfoBox);
		element.css("position", "relative");
		element.append(collisionInfoBoxOverlay);
		
		var collisionInfoBoxHeader = $("<div>").addClass("collisionInfoBoxHeader").html(_this.options.translations["colisionBox.header"]);
		var closeButton = $("<div>").addClass("collisionInfoBoxHeaderCloseButton").attr("title", _this.options.translations["colisionBox.closeButton"]);
		closeButton.click(function() {
			collisionInfoBoxOverlay.hide();
		});
		collisionInfoBoxHeader.append(closeButton);
		
		collisionInfoBoxContent = $("<div>").addClass("collisionInfoBoxContent");
		collisionInfoBox.append(collisionInfoBoxHeader);
		collisionInfoBox.append(collisionInfoBoxContent);
	}
	
	function showLeftScroll() {
		isScrollVisible = true;
		htmlElements.scrollWrapper.show();
		htmlElements.centerContainer.width(currentWidth - constants.ROW_NAMES_WIDTH - constants.RIGHT_SCROLL_WIDTH - 1);
	}
	
	function hideLeftScroll() {
		isScrollVisible = false;
		htmlElements.scrollWrapper.hide();
		htmlElements.centerContainer.width(currentWidth - constants.ROW_NAMES_WIDTH -1);
	}
	
	function updateHeader(cellSettings) {
		header.setCurrentScale(cellSettings.zoomLevel);
		htmlElements.topRow1.children().remove();
		htmlElements.topRow2.children().remove();
		for (var i=0; i<cellSettings.scale.categories.length; i++) {
			var categoryCellsNumber = getCategoryCellsNumber(cellSettings, i);
		
			var cellElement = $("<div>").height("100%").width((categoryCellsNumber*constants.CELL_WIDTH)-1).addClass("ganttTopRowElement").addClass("ganttTopRowElementEnd");
			cellElement.html(cellSettings.scale.categories[i]);
			
			htmlElements.topRow1.append(cellElement);
			
			var labelNumber = cellSettings.scale.elementLabelInitialNumber ? cellSettings.scale.elementLabelInitialNumber : 0;
			var bottomElement = null;
			for (var bottomI=0; bottomI < categoryCellsNumber; bottomI++) {
				bottomElement = $("<div>").height(htmlElements.topRow2.height()-1).width(constants.CELL_WIDTH-1).addClass("ganttTopRowElement");
				var bottomElementContent = null;
				var moveLabelLeft = false;
				if (cellSettings.scale.elementLabelsValues) {
					if (i==0 && cellSettings.scale.firstCategoryFirstElement) {
						bottomElementContent = cellSettings.scale.elementLabelsValues[bottomI + cellSettings.scale.firstCategoryFirstElement - 1];
					} else {
						bottomElementContent = cellSettings.scale.elementLabelsValues[bottomI];
					}
				} else {
					bottomElementContent = labelNumber;
					moveLabelLeft = true;
				}
				if (moveLabelLeft) {
					bottomElement.html("<div>"+bottomElementContent+"</div>");
				} else {
					bottomElement.html(bottomElementContent);
				}
				htmlElements.topRow2.append(bottomElement);
				labelNumber += cellSettings.scale.elementLabelsInterval;
			}
			bottomElement.addClass("ganttTopRowElementEnd");
		}
	}
	
	function getCategoryCellsNumber(cellSettings, i) {
		if (cellSettings.scale.elementsInCategory instanceof Array) {
			return cellSettings.scale.elementsInCategory[i];
		} else {
			if (cellSettings.scale.categories.length == 1 && cellSettings.scale.firstCategoryFirstElement && cellSettings.scale.lastCategoryLastElement) {
				return cellSettings.scale.lastCategoryLastElement - cellSettings.scale.firstCategoryFirstElement + 1;
			} else if (i==0 && cellSettings.scale.firstCategoryFirstElement) {
				return cellSettings.scale.elementsInCategory - cellSettings.scale.firstCategoryFirstElement + 1;
			} else if (i==cellSettings.scale.categories.length-1 && cellSettings.scale.lastCategoryLastElement) {
				return cellSettings.scale.lastCategoryLastElement;
			} else {
				return cellSettings.scale.elementsInCategory;
			}
		}
	}
	
	function getTotalNumberOfCells(cellSettings) {
		var totalCellsNumber = 0;
		for (var i=0; i<cellSettings.scale.categories.length; i++) {
			totalCellsNumber += getCategoryCellsNumber(cellSettings, i);
		}
		return totalCellsNumber;
	}
	
	function addRow(cellSettings, rowName) {
		var rowNameElement = $("<div>").height(constants.CELL_HEIGHT-1).addClass("ganttRowNameElement");
		rowNameElement.css("line-height",(constants.CELL_HEIGHT-1) + "px");
		rowNameElement.html(rowName);
		htmlElements.rowNamesConteiner.append(rowNameElement);
	
		var rowElement = $("<div>").height(constants.CELL_HEIGHT-1).addClass("ganttRowElement");
		htmlElements.rowsContainer.append(rowElement);
		
		for (var i=0; i<cellSettings.scale.categories.length; i++) {
			var categoryCellsNumber = getCategoryCellsNumber(cellSettings, i);
			var cellElement;
			for (var bottomI=0; bottomI < categoryCellsNumber; bottomI++) {
				cellElement = $("<div>").height(constants.CELL_HEIGHT-1).width(constants.CELL_WIDTH-1).addClass("ganttCellElement");
				cellElement.attr("id","ganttCellElement_"+i+"_"+bottomI);
				rowElement.append(cellElement);
			}
			cellElement.addClass("ganttTopRowElementEnd");
		}
		rowsByName[rowName] = rowElement;
		return rowElement;
	}
	
	function addItem(item, isCollision) {
		var row = rowsByName[item.row];
		var itemElement = $("<div>").addClass("ganttItem");
		itemElement.css("line-height", (constants.CELL_HEIGHT - 5)+"px");
		var left = (constants.CELL_WIDTH * item.from);
		var right = (constants.CELL_WIDTH * item.to);
		var width = right-left - 10;
		itemElement.width(width - 1);
		itemElement.height(constants.CELL_HEIGHT - 5);
		itemElement.css("top","1px");
		itemElement.css("left",(left-1)+"px");
		row.append(itemElement);
		if (item.type) {
			itemElement.addClass("ganttItemType_"+item.type);
		}	
		
		if (isCollision) {
			itemElement.addClass("ganttCollisionItem");
			if (width > 30) {
				itemElement.addClass("withIcon");
				itemElement.html(_this.options.translations["colisionElementName"]);
				itemElement.shorten({width: width, tail: "...", tooltip: false});
			} else if (width > 15) {
				itemElement.addClass("withIcon");
			}
			itemElement[0].isCollision = true; // add isCollision to DOM element
			itemElement[0].ganttItem = item; // add item element to DOM
		} else {
			if (width > 30) {
				itemElement.html(item.info.name);
				itemElement.shorten({width: width, tail: "...", tooltip: false});
			}			
		}

		if (_this.options.hasPopupInfo) {
			var description = "<div class='ganttItemDescriptionName'>"+item.info.name+"</div>";
			description += "<div class='ganttItemDescriptionInfo'>";
			description += "<div class='ganttItemDescriptionLabel'>"+_this.options.translations["description.dateFrom"]+"</div>";
			description += "<div class='ganttItemDescriptionValue'>"+item.info.dateFrom+"</div></div>";
			description += "<div class='ganttItemDescriptionInfo'>";
			description += "<div class='ganttItemDescriptionLabel'>"+_this.options.translations["description.dateTo"]+"</div>";
			description += "<div class='ganttItemDescriptionValue'>"+item.info.dateTo+"</div></div>";
			itemElement.CreateBubblePopup({ innerHtml: description, themePath: "/qcadooView/public/css/core/lib/jquerybubblepopup-theme" });
		}
		
		if (item.id || isCollision) {
			itemElement.attr("id", _this.elementPath+"_item_"+item.id);
			itemElement[0].entityId = item.id; // add entityId to DOM element
			itemElement.css("cursor", "pointer");
			itemElement.click(function() {
				if (this.isCollision) {
					showCollisionBox(this.ganttItem);
					return;
				}
				var itemElement = $(this)
				if (selectedItem) {
					selectedItem.removeClass("ganttItemSelected");	
				}
				selectedItem = itemElement;
				itemElement.addClass("ganttItemSelected");
				onSelectChange();
			});
		}
		
		itemElement.mouseover(function() {
			$(this).addClass("ganttItemHovered");
		}).mouseout(function() {
			$(this).removeClass("ganttItemHovered");
		});
	}
	
	this.updateSize = function(_width, _height) {
		_width = _width - 22;
		_height = _height - 50;
		currentWidth = _width;
		htmlElements.wrapper.width(_width);
		htmlElements.wrapper.height(_height);
		if (isScrollVisible) {
			htmlElements.centerContainer.width(_width - constants.ROW_NAMES_WIDTH - constants.RIGHT_SCROLL_WIDTH-1);
		} else {
			htmlElements.centerContainer.width(_width - constants.ROW_NAMES_WIDTH);
		}
		htmlElements.centerContainer.height(_height);
		htmlElements.scrollWrapper.height(_height);
		htmlElements.rowNamesConteiner.height(_height - constants.HEADER_HEIGHT - constants.BOTTOM_SCROLL_HEIGHT);
		htmlElements.centerContentWrapper.height(_height - constants.BOTTOM_SCROLL_HEIGHT - 2);	
		htmlElements.rowsContainerWrapper.height(_height - constants.HEADER_HEIGHT - constants.BOTTOM_SCROLL_HEIGHT - 2);
		htmlElements.scrollContainer.height(_height - constants.HEADER_HEIGHT - constants.BOTTOM_SCROLL_HEIGHT+1);
		updateScroll();
	}
	
	function updateScroll() {
		if (rowsByIndex.length * constants.CELL_HEIGHT > htmlElements.rowsContainerWrapper.height()+3) {
			showLeftScroll();
		} else {
			hideLeftScroll();
		}
	}
	
	constructor();
}