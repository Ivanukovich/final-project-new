<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 25.02.2022
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>

<fmt:setBundle basename="context.language" />
<fmt:message key="registration.first_name" var="first_name_label"></fmt:message>
<fmt:message key="registration.last_name" var="last_name_label"></fmt:message>
<fmt:message key="registration.login" var="login_label"></fmt:message>
<fmt:message key="registration.email" var="email_label"></fmt:message>
<fmt:message key="registration.password" var="password_label"></fmt:message>

<html>
<head>
    <title>Registration</title>
</head>
<body>
    <form name="RegistrationForm" method="post" action="${absolutePath}/controller" novalidate>
        <input type="hidden" name="command" value="registration"/>

        <div class="form-group">
            <label class="form-label">${first_name_label}</label>
            <input type="text" name="first_name" class="form-control form-control-sm">
        </div>

        <div class="form-group">
            <label class="form-label">${last_name_label}</label>
            <input type="text" name="last_name" class="form-control form-control-sm">
        </div>

        <div class="form-group">
            <label class="form-label">${login_label}</label>
            <input type="text" name="login" class="form-control form-control-sm">
        </div>

        <div class="form-group">
            <label class="form-label">${email_label}</label>
            <input type="text" name="email" class="form-control form-control-sm">
        </div>

        <div class="form-group">
            <label class="form-label">${password_label}</label>
            <input type="text" name="password" class="form-control form-control-sm">
        </div>

        <div class="text-center mb-3">
            <button type="submit" class="btn btn-success"><fmt:message key="registration.submit"/></button>
        </div>
    </form>

</body>
</html>
