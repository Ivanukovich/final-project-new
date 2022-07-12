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
<fmt:setLocale value="${locale}" scope="session" />

<fmt:setBundle basename="context.language" />

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style><%@include file="../CSS/style.css"%></style>
    <title>User Page</title>
</head>
<body>
<header>
    <c:import url="header/header.jsp"/>
</header>
<div class="container">
    <br/>
    <c:if test="${not empty sessionScope.user_id}">
        <c:if test="${is_blocked}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="user.block_text"/>
            </div>
            <br/>
        </c:if>
        <div class="d-grid gap-2 d-md-block">
            <c:choose>
                <c:when test="${user_status eq UserStatus.INACTIVE}">
                    <h1><fmt:message key="user.inactive_text"/></h1>
                    <br/>
                    <c:choose>
                        <c:when test="${is_blocked}">
                            <a style="width: 250px" class="btn btn-primary btn-lg disabled" role="button" aria-disabled="true">
                                <fmt:message key="user.select_rental_point"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a style="width: 250px" class="btn btn-primary" role="button" href="${absolutePath}/controller?command=find_available_rental_points">
                                <fmt:message key="user.select_rental_point"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <h1><fmt:message key="user.active_text"/></h1>
                    <br/>
                    <a style="width: 250px" class="btn btn-primary" role="button" href="${absolutePath}/controller?command=finish_rent&user_id=${user_id}">
                        <fmt:message key="user.return_bicycle"/>
                    </a>
                </c:otherwise>
            </c:choose>
            <c:if test="${user_role eq UserRole.ADMINISTRATOR}">
                <a style="width: 250px" class="btn btn-primary" role="button" href="${pageContext.request.contextPath}/jsp/database_control.jsp">
                    <fmt:message key="administrator.database_control"/>
                </a>
            </c:if>
        </div>
    </c:if>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
