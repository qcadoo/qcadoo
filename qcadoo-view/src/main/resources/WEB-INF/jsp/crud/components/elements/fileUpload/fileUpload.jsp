<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String ctx = request.getContextPath();
%>

<script type="text/JavaScript">
	var buttonActive = false;
	var selectRibbonItem = null;

	jQuery(document).ready(function(){
		window.mainController.setWindowHeader("${headerLabel}");
		$('#form').ajaxForm(function(response) {
			window.mainController.getComponentByReferenceName("window").closeThisModalWindow(null, response);
	    }); 
	});
	
	function onInputChange(fileName) {
		if (!selectRibbonItem) {
			selectRibbonItem = window.mainController.getComponentByReferenceName("window").getRibbonItem("navigation.select");
			selectRibbonItem.addOnChangeListener({
				onClick: performSubmit
			});
		}
		if (fileName && fileName != "") {
			buttonActive = true;
			$("#submit").addClass("activeButton");
			selectRibbonItem.enable();
			return true;
		} else {
			buttonActive = false;
			$("#submit").removeClass("activeButton");
			selectRibbonItem.disable();
	        return false;
		}
	}

	function performSubmit() {
		if (buttonActive) {
			QCD.components.elements.utils.LoadingIndicator.blockElement($("body"));
			$('#form').submit();
		}
	}

</script>


<form method="post" action="<%=ctx%>/fileUpload.html" enctype="multipart/form-data" id="form" style="text-align: left">
		        	
	<div style="margin-left: 10px; margin-top: 10px; font: 11px arial; font-weight: bold;">
		${chooseFileLabel}
	</div>
	
	<div style="margin-top: 5px; margin-bottom: 20px; margin-left: 10px;">
		<input type="file" name="file" size="50" onChange="onInputChange(this.value);"/>
	</div>
		            
	<div class="linkButton" style="width: 200px; margin-left: 10px; margin-bottom: 5px;" id="submit">
		<a href="#" onclick="performSubmit()">
			<span>
				<div id="labelDiv">${buttonLabel}</div>
			</span>
		</a>
	</div>
</form>
