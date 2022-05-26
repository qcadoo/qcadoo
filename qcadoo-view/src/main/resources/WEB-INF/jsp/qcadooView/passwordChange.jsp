<%--

    ***************************************************************************
    Copyright (c) 2010 Qcadoo Limited
    Project: Qcadoo Framework
    Version: 1.4

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

<!DOCTYPE html>

<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="shortcut icon" href="/qcadooView/public/img/core/icons/favicon.png">

        <title>${applicationDisplayName} :: change password</title>

        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/qcadooView/public/css/core/lib/bootstrap.min.css?ver=${buildNumber}"
            type="text/css"/>
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/qcadooView/public/css/core/lib/languages.min.css?ver=${buildNumber}"
            type="text/css"/>
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/qcadooView/public/css/core/passwordChange-min.css?ver=${buildNumber}"
            type="text/css"/>

        <script type="text/javascript"
            src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/jquery-3.2.1.min.js?ver=${buildNumber}"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/popper.min.js?ver=${buildNumber}"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/qcadooView/public/js/core/lib/bootstrap.min.js?ver=${buildNumber}"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/qcadooView/public/js/core/qcd/utils/serializator.js?ver=${buildNumber}"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/qcadooView/public/js/core/passwordChange-min.js?ver=${buildNumber}"></script>
    </head>

    <body class="text-center" role="document">
        <div class="container" role="main">
            <div id="messagePanel" class="alert" role="alert">
                <h6 class="alert-heading" id="messageHeader"></h5>
                <p id="messageContent"></p>
            </div>

            <div class="passwordChangeContainer">
                <c:if test="${! iframe && ! popup}">
                    <div class="text-right">
                        <div class="btn-group dropup">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <span class="lang-sm" lang="${currentLanguage}"></span> <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <c:forEach items="${locales}" var="localesEntry">
                                    <li><span class="lang-sm lang-lbl" lang="${localesEntry.key}"></span></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="mt-3"></div>
                </c:if>

                <form id="passwordChangeForm" name="passwordChangeForm" method="POST">
                <input type="hidden" name="token" value="${token}">
                <img class="logo mb-4" src="${logoPath}" alt="Logo"/>
                <h1 class="h3 mb-4 font-weight-normal">${translation["security.form.header.passwordChange"]}</h1>

                <div class="input-group">
                    <label for="password" class="sr-only">${translation["security.form.label.password"]}</label>
                    <input type="password" id="passwordInput" name="password" class="form-control" placeHolder="${translation["security.form.label.password"]}" autocomplete="off" required autofocus/>
                    <div class="invalid-feedback" style="margin-top: -65px;">
                        ${translation["security.message.wrongPassword"]}
                    </div>
                </div>

                <div class="input-group">
                    <label for="usernameInput" class="sr-only">${translation["security.form.label.login"]}</label>
                    <input type="password" id="passwordConfirmationInput" name="passwordConfirmation" class="form-control" placeHolder="${translation["security.form.label.passwordConfirmation"]}" autocomplete="off" required/>
                    <div class="invalid-feedback" style="margin-top: -25px;">
                        ${translation["security.message.wrongPasswordConfirmation"]}
                    </div>
                </div>

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-lg btn-info" id="cancelButton"><span>${translation['security.form.button.cancel']}</button>
                    <button type="button" class="btn btn-lg btn-primary" id="passwordChangeButton"><span>${translation['security.form.button.passwordChange']}</button>
                </div>

                <p class="mt-3 mb-3">

                </p>
                </form>
            </div>
        </div>

        <script type="text/javascript" charset="utf-8">
            var successHeaderText = '${translation["security.message.passwordChange.successHeader"]}';
		    var successContentText = '${translation["security.message.passwordChange.successContent"]}';

            var errorHeaderText = '${translation["security.message.errorHeader"]}';
            var errorContentText = '${translation["security.message.errorContent"]}';

            var wrongPasswordText = '${translation["security.message.wrongPassword"]}';
            var wrongPasswordText = '${translation["security.message.wrongPasswordConfirmation"]}';

            jQuery(document).ready(function() {
                QCD.passwordChange.init();
            });
        </script>
    </body>

</html>
