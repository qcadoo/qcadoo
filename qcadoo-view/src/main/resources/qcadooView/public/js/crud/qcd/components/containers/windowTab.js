/*
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.1.1
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
QCD.components.containers = QCD.components.containers || {};

QCD.components.containers.WindowTab = function(_element, _mainController) {
	$.extend(this, new QCD.components.Container(_element, _mainController));
	
	var mainController = _mainController;
	
	var ribbon;
	var ribbonElement;
	
	function constructor(_this) {
		var childrenElement = $("#"+_this.elementSearchName+" > div");
		_this.constructChildren(childrenElement.children());
		
		if (_this.options.ribbon) {
			ribbon = new QCD.components.Ribbon(_this.options.ribbon, _this.elementName, mainController, _this.options.translations);
			ribbonElement = ribbon.constructElementContent();
			//var ribbonDiv = $("#"+_this.elementPath+"_windowContainerRibbon");
			//ribbonDiv.append(ribbonElement);
		}
		
		if (_this.options.referenceName) {
			mainController.registerReferenceName(_this.options.referenceName, _this);
		}
	}
	
	this.getRibbonElement = function() {
		return ribbonElement;
	}
	
	this.getRibbonItem = function(ribbonItemPath) {
		return ribbon.getRibbonItem(ribbonItemPath);
	}
	
	this.performComponentScript = function() {
		if (ribbon) {
			ribbon.performScripts();
		}
	}
	
	this.getComponentValue = function() {
		return {};
	}
	this.setComponentValue = function(value) {
	}
	
	this.setComponentState = function(state) {
	}
	
	this.setMessages = function(messages) {
	}
	
	this.setComponentEnabled = function(isEnabled) {
	}
	
	this.setComponentLoading = function() {
	}
	
	this.setVisible = function(isVisible) {
		// do nothing
	}
	
	this.blockButtons = function() {
		if (ribbon) {
			ribbon.blockButtons();
		}
	}
	
	this.unblockButtons = function() {
		if (ribbon) {
			ribbon.unblockButtons();
		}
	}
	
	this.updateSize = function(_width, _height) {
		var componentsHeight = _height ? _height-20 : null;
		for (var i in this.components) {
			this.components[i].updateSize(_width-20, componentsHeight);
		}
	}
	
	constructor(this);
}
