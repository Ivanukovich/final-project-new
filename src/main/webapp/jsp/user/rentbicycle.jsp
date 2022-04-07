<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 17.03.2022
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>

<fmt:setBundle basename="context.language" />

<html>
<head>
    <title>Title</title>
</head>
<body>
    <td><c:out value="${bicycle.model}"/></td>
    <a href="${absolutePath}/controller?command=rent_start&bicycle_id=${bicycle.bicycleId}&user_id=${user_id}">
        <fmt:message key="user.select"/>
    </a>
</body>
</html>
