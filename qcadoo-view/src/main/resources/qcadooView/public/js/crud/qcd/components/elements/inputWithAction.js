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
QCD.components = QCD.components || {};
QCD.components.elements = QCD.components.elements || {};

QCD.components.elements.InputWithAction = function (_element, _mainController) {
    $.extend(this, new QCD.components.elements.FormComponent(_element, _mainController));
    var textRepresentation = $("#" + this.elementSearchName + "_text");

    var input = this.input;

    var elementPath = this.elementPath;

    var element = _element;

    var hasListeners = (this.options.listeners.length > 0) ? true : false;

    var fireOnChangeListeners = this.fireOnChangeListeners;

    var options = this.options;

    var enabled = this.options.enabled;

	var elements = {
		input : this.input,
		text : $("#" + this.elementSearchName + "_text"),
		label : $("#" + this.elementSearchName + "_labelDiv"),
		actionButton : $("#" + this.elementSearchName + "_actionButton")
	};

    function constructor(_this) {
    console.log(options);
    	elements.actionButton.click(preformActionOnClick);

        input.change(function () {
            inputDataChanged();
        });
    }

    function preformActionOnClick() {
		if (!elements.actionButton.hasClass("enabled")) {
			return;
		}
		mainController.callEvent("onClick", elementPath, null, null, null);
    }

    function inputDataChanged() {
        fireOnChangeListeners("onChange", [input.val()]);
        if (hasListeners) {
            mainController.callEvent("onInputChange", elementPath, null, null, null);
        }
    }

    if (this.options.referenceName) {
        _mainController.registerReferenceName(this.options.referenceName, this);
    }

    this.getComponentData = function () {
        return {
            value: input.val()
        }
    }

    this.setComponentData = function (data) {
        if (data.value) {
            this.input.val(data.value);
            textRepresentation.html(data.value);
        } else {
            this.input.val("");
            textRepresentation.html("-");
        }
    }

    this.setFormComponentEnabled = function (isEnabled) {
        if (this.options.textRepresentationOnDisabled) {
            if (isEnabled) {
                input.show();
                textRepresentation.hide();
            } else {
                input.hide();
                textRepresentation.show();
            }
        }
    }

    this.updateSize = function(_width, _height) {
		var height = _height ? _height - 10 : 40;
		this.input.parent().parent().parent().parent().parent().height(height);
	}

    this.setFormComponentEnabled = function(isEnabled) {
        if(!enabled) {
            input.attr("readonly", "readonly");
            input.attr("disabled", "disabled");
            elements.input.attr('style', 'color: #959595; background-color: #f5f5f5;');
        }

		if (isEnabled) {
			elements.actionButton.addClass("enabled")
		} else {
			elements.actionButton.removeClass("enabled")
		}
	}
    constructor(this);
}
