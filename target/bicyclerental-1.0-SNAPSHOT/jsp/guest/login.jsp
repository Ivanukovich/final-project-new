<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 26.02.2022
  Time: 21:09
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
<fmt:message key="login.login" var="login"></fmt:message>
<fmt:message key="login.enter_login" var="e_login"></fmt:message>
<fmt:message key="login.password" var="password"></fmt:message>
<fmt:message key="login.enter_password" var="e_password"></fmt:message>

<html>
<head>
    <title>Title</title>
</head>
<body>
    <form name="LoginForm" method="post" action="${absolutePath}/controller" novalidate>
        <input type="hidden" name="command" value="login"/>
        <div class="form-group">
            <label class="form-label">${login}</label>
            <input type="text" name="login" class="form-control form-control-sm">
        </div>
        <div class="form-group">
            <label class="form-label">${password}</label>
            <input type="password" name="password" class="form-control form-control-sm">
        </div>

        <div class="text-center mb-3">
            <button type="submit" class="btn btn-success"><fmt:message key="login.submit"/></button>
        </div>

        <div class="text-center">
            <a class="btn btn-info" href="${absolutePath}/jsp/guest/registration.jsp" role="button"><fmt:message key="common.registration"/></a>
        </div>
    </form>


</body>
</html>
