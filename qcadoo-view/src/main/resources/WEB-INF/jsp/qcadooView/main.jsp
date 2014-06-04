<%--

    ***************************************************************************
    Copyright (c) 2010 Qcadoo Limited
    Project: Qcadoo Framework
    Version: 1.3

    This file is part of Qcadoo.

    Qcadoo is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation; either version 3 of the License,
    or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty
    of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
    ***************************************************************************

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="<c:out value="${languageCode}" />">
<head>

	<title>${applicationDisplayName}</title>
	
	<c:choose>
		<c:when test="${useCompressedStaticResources}">
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/qcadoo-min.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/custom.css?ver=${buildNumber}" type="text/css" />
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/_jquery-1.4.2.min.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jquery-ui-1.8.5.custom.min.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jquery.jqGrid.min.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/qcadoo-min.js?ver=${buildNumber}"></script>
		</c:when>
		<c:otherwise>
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/qcd.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/mainPage.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/menuTopLevel.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/menu/style.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/notification.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/core/jqModal.css?ver=${buildNumber}" type="text/css" />
			<link rel="stylesheet" href="${pageContext.request.contextPath}/qcadooView/public/css/custom.css?ver=${buildNumber}" type="text/css" />
		
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/_jquery-1.4.2.min.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jquery.pnotify.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jquery.blockUI.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jqModal.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/utils/logger.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/utils/modal.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/utils/connector.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/menu/model.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/menu/menuController.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/core/windowController.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/core/messagesController.js?ver=${buildNumber}"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/qcadooView/public/js/crud/qcd/components/elements/utils/loadingIndicator.js?ver=${buildNumber}"></script>
		</c:otherwise>
	</c:choose>
	
	<link rel="shortcut icon" href="/qcadooView/public/img/core/icons/favicon.png">
	
	<script type="text/javascript">

		var menuStructure = ${menuStructure}

		var windowController;
		
		jQuery(document).ready(function(){
			
			windowController = new QCD.WindowController(menuStructure);
			
			$("#mainPageIframe").load(function() {
				try {
					el = $('body', $('iframe').contents());
					el.click(function() {windowController.restoreMenuState()});
				} catch(e) {
				}
			});
		});

		window.goToPage = function(url, serializationObject, isPage) {
			windowController.goToPage(url, serializationObject, isPage);
		}

		window.openModal = function(id, url, serializationObject, onCloseListener) {
			windowController.openModal(id, url, serializationObject, onCloseListener);
		}

		window.changeModalSize = function(width, height) {
			windowController.changeModalSize(width, height);
		}

		window.goBack = function(pageController) {
			windowController.goBack(pageController);
		}

		window.closeThisModalWindow = function(status) {
			windowController.closeThisModalWindow(status);
		}

		window.getLastPageController = function() {
			return windowController.getLastPageController();
		}

		window.goToLastPage = function() {
			windowController.goToLastPage();
		}

		window.onSessionExpired = function(serializationObject, isModal) {
			windowController.onSessionExpired(serializationObject, isModal);
		}

		window.addMessage = function(type, content) {
			windowController.addMessage(type, content);
		}

		window.onLoginSuccess = function() {
			windowController.onLoginSuccess();
		}
		
		window.goToMenuPosition = function(position) {
			windowController.goToMenuPosition(position);
		}

		window.activateMenuPosition = function(position) {
 			windowController.activateMenuPosition(position);
 		}

		window.hasMenuPosition = function(position) {
			return windowController.hasMenuPosition(position);
		}

		window.updateMenu = function() {
			windowController.updateMenu();
		}

		window.getCurrentUserLogin = function() {
			return "${userLogin}";
		}
	
		window.translationsMap = new Object();
		<c:forEach items="${commonTranslations}" var="translation">
			window.translationsMap["${translation.key}"] = "${translation.value}";
		</c:forEach>
	
		
	</script>
<!-- start Mixpanel -->
	<c:if test="${not empty mixpanelToken}">
		
		<script type="text/javascript">(function(e,b){if(!b.__SV){var a,f,i,g;window.mixpanel=b;a=e.createElement("script");a.type="text/javascript";a.async=!0;a.src=("https:"===e.location.protocol?"https:":"http:")+'//cdn.mxpnl.com/libs/mixpanel-2.2.min.js';f=e.getElementsByTagName("script")[0];f.parentNode.insertBefore(a,f);b._i=[];b.init=function(a,e,d){function f(b,h){var a=h.split(".");2==a.length&&(b=b[a[0]],h=a[1]);b[h]=function(){b.push([h].concat(Array.prototype.slice.call(arguments,0)))}}var c=b;"undefined"!==
		typeof d?c=b[d]=[]:d="mixpanel";c.people=c.people||[];c.toString=function(b){var a="mixpanel";"mixpanel"!==d&&(a+="."+d);b||(a+=" (stub)");return a};c.people.toString=function(){return c.toString(1)+".people (stub)"};i="disable track track_pageview track_links track_forms register register_once alias unregister identify name_tag set_config people.set people.set_once people.increment people.append people.track_charge people.clear_charges people.delete_user".split(" ");for(g=0;g<i.length;g++)f(c,i[g]);
		b._i.push([a,e,d])};b.__SV=1.2}})(document,window.mixpanel||[]);
		mixpanel.init("${mixpanelToken}");
		</script>
	</c:if>
<!-- end Mixpanel -->
</head>
<body>

	<div id="mainTopMenu">
		<div id="topLevelMenu">
			<img id="logoImage" src="/qcadooView/public/css/core/images/logo_small.png" alt="qcadoo MES logo" onclick="windowController.goToDashboard()"></img>
			<div id="topRightPanel">
				<span id="userInfo">${userLogin}</span>
				<a href='#' id="profileButton" onclick="windowController.goToMenuPosition('administration.profile')">${commonTranslations["qcadooView.button.userProfile"] }</a>
				<a href='#' onclick="windowController.performLogout()">${commonTranslations["qcadooView.button.logout"] }</a>
			</div>
		</div>
		<div id="firstLevelMenu">
		</div>
		<div id="secondLevelMenuWrapper">
			<div id="secondLevelMenu">
			</div>
			</div>
	</div>
	<div id="mainPageIframeWrapper"><iframe id="mainPageIframe" frameborder="0"></iframe></div>
</body>
</html>