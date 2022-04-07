<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 26.02.2022
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %>
<%@ page import="by.epam.bicyclerental.model.entity.UserStatus" %>


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>

<fmt:setBundle basename="context.language" />

<html>
<head>
    <title>User Page</title>
</head>
<body>
    <c:choose>
        <c:when test="${is_blocked}">

        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${user_status eq UserStatus.INACTIVE}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${absolutePath}/controller?command=find_all_rental_points"><fmt:message key="user.select_rental_point"/>
                        </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${absolutePath}/controller?command=finish_rent&user_id=${user_id}"><fmt:message key="user.return_bicycle"/>
                        </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

    <c:if test="${user_role eq UserRole.ADMINISTRATOR}">
        <li class="nav-item">
            <a class="nav-link"
               href="${absolutePath}/controller?command=find_all_users"><fmt:message key="administrator.user_list"/>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link"
               href="${absolutePath}/controller?command=edit_rental_points"><fmt:message key="administrator.rental_point_list"/>
            </a>
        </li>
    </c:if>
</body>
</html>
