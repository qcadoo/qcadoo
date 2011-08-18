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

QCD.components.elements.TimeInput = function(_element, _mainController) {
	$.extend(this, new QCD.components.elements.FormComponent(_element, _mainController));
	
	var input = this.input;
	
	var elementPath = this.elementPath;
	
	var noHours = this.options.noHours;
	
	var hasListeners = (this.options.listeners.length > 0) ? true : false;
	
	function constructor(_this) {
		$.mask.definitions['6']='[0-5]';
		input.change(function() {
			inputDataChanged();
		});
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
			value : convertToLong(input.val())
		}
	}
	
	this.setComponentData = function(data) {
		if (data.value) {
			this.input.val(convertToString(data.value+""));
		} else {
			this.input.val("");
		}
	}
	
	function convertToLong(value) {
		part = value.split(":");
		
		if(part.length != 3) {
			return 0;
		}
		
		return (part[2] * 1) + (part[1] * 60) + (part[0] * 3600);
	}
	
	function convertToString(value) {
		value = value.replace(/\s/g, "").split(",")[0];
		
		h = Math.floor(value / 3600) + "";
		while(h.length < noHours) {
			h = "0" + h;
		}

		m = Math.floor((value % 3600) / 60);
		if(m < 10) {
			m = "0" + m;
		}
		
		s = (value % 3600) % 60;
		if(s < 10) {
			s = "0" + s;
		}
		
		return h + ":" + m + ":" + s;
	}
	
	this.updateSize = function(_width, _height) {
		var height = _height ? _height-10 : 40;
		this.input.parent().parent().parent().height(height);
		this.input.parent().parent().height(height);
	}
	
	this.setFormComponentEnabled = function(isEnabled) {
		if (isEnabled) {
			input.mask((new Array(noHours * 1 + 1)).join("9") + ":69:69");
		} else {
			input.unmask();
		}
	}
	
	constructor(this);
}