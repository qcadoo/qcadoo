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
QCD.components = QCD.components || {};
QCD.components.containers = QCD.components.containers || {};
QCD.components.containers.layout = QCD.components.containers.layout || {};

QCD.components.containers.layout.FlowGridLayout = function(_element, _mainController) {
	$.extend(this, new QCD.components.containers.layout.Layout(_element, _mainController));
	
	var elementSearchName = this.elementSearchName;
	var rootElement = $("#"+elementSearchName+"_layoutComponents > .gridLayoutTable");
	
	var colsNumber = this.options.colsNumber;

	function constructor(_this) {
		_this.constructChildren(getLayoutChildren());
	}
	
	function getLayoutChildren() {
		var components = rootElement.children().children().children();
		return components;
	}
	
	this.updateSize = function(_width, _height) {

		console.log('update size : '+_width + ' - '+_height);

		var baseWidth = _width/colsNumber;
		var baseHeight = 50;
		
//		var tdElements = rootElement.children().children();
//
//		for (var i=0; i<tdElements.length; i++) {
//			var tdElement = $(tdElements[i]);
//			var colspan = tdElement.attr("colspan") ? tdElement.attr("colspan") : 1;
//			var elementWidth = baseWidth * colspan;
//			tdElement.width(elementWidth);
//		}

		for (var i in this.components) {
			var tdElement = this.components[i].element.parent();
			var rowspan = tdElement.attr("rowspan") ? tdElement.attr("rowspan") : 1;
			var colspan = tdElement.attr("colspan") ? tdElement.attr("colspan") : 1;
			var minHeight = tdElement.attr("minHeight");

			var elementWidth = baseWidth * colspan;

			var elementHeight = baseHeight * rowspan;
			if(minHeight){
				var h = _height ? _height : $(window).height();
				var usedHeight = 230 + getAllFixedElementsFromColumnHeight(this.components, i, baseHeight);
				elementHeight = h - usedHeight;

				console.log('minHeight: '+i+' - '+minHeight + ' - elementHeight: '+elementHeight+' - h: '+h + ', usedHeight: '+usedHeight);
			}

			this.components[i].updateSize(elementWidth, elementHeight);
			var tdElement = this.components[i].element.parent();
			tdElement.width(elementWidth);
			tdElement.height(elementHeight);
		}
	}

	function getAllFixedElementsFromColumnHeight(components, key, baseHeight){
		var sum = 0;
		var elementColumn = getElementColumnIndex(components[key]);

		for (var i in components) {
			if(elementColumn == getElementColumnIndex(components[i])){
				var tdElement = components[i].element.parent();
				var minHeight = tdElement.attr("minHeight");

				if(!minHeight){
					var rowspan = tdElement.attr("rowspan") ? tdElement.attr("rowspan") : 1;
					var elementHeight = baseHeight * rowspan;
					sum += elementHeight;

				}
			}
		}

		return sum;
	}

	function getElementColumnIndex(element){
		var tdElement = element.element.parent();

		return parseInt(tdElement.attr("cellIndex"));
	}
	
	constructor(this);
}