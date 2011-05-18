/*
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
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

QCD.components.elements.File = function(_element, _mainController) {
	$.extend(this, new QCD.components.elements.FormComponent(_element, _mainController));
	
	var input = this.input;
	
	var link = $("#"+this.elementSearchName+"_fileList");
	
	var modificationDate = $("#"+this.elementSearchName+"_fileLastModificationDate");
	
	var fileButton = $("#"+this.elementSearchName+"_fileButton");
	
	var elementPath = this.elementPath;
	
	var hasListeners = (this.options.listeners.length > 0) ? true : false;
	
	var fileWindow;
	
	var uploder;
	
	var _this = this;
	
	function constructor(_this) {
		input.change(function() {
			inputDataChanged();
		});
		
		fileButton.click(openFileWindow);
	}
	
	function inputDataChanged() {
		if (hasListeners) {
			mainController.callEvent("onInputChange", elementPath, null, null, null);
		}
	}
	
	if (this.options.referenceName) {
		_mainController.registerReferenceName(this.options.referenceName, this);
	}
	
	this.getComponentData = function() {
		return {
			value : input.val()
		}
	}
	
	this.setComponentData = function(data) {
		if (data.value) {
			this.input.val(data.value);
			modificationDate.text("(" + data.fileLastModificationDate + ")");
			link.attr("href", data.fileUrl);
			link.text(data.fileName);
		} else {
			this.input.val("");
			modificationDate.val("");
			link.attr("href", "#");
			link.val("");
		}
	}
	
	this.setFormComponentEnabled = function(isEnabled) {
		if (isEnabled) {
			fileButton.addClass("enabled")
		} else {
			fileButton.removeClass("enabled")
		}
	}
	
	this.updateSize = function(_width, _height) {
		var height = _height ? _height-10 : 40;
		this.input.parent().parent().parent().height(height);
		this.input.parent().parent().height(height);
	}
	
	function openFileWindow() {
		if (! fileButton.hasClass("enabled")) {
			return;
		}

		fileWindow = mainController.openPopup("/fileUpload.html", _this, "file", 400, 200);
	}
	
	this.onPopupInit = function() {
		// var file = fileWindow.getComponent("file");
	}
	
	this.onPopupClose = function() {
		fileWindow = null;
	}
	
	constructor(this);
}