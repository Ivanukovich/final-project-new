<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 28.06.2022
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %>
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
    <title>Database Control</title>
</head>
<body>
<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <h1><fmt:message key="user.inactive_text"/></h1>
    <br/>
        <c:if test="${user_role eq UserRole.ADMINISTRATOR}">
            <div class="d-grid gap-2 d-md-block">
                <a style="width: 250px" class="btn btn-primary" role="button"
                   href="${absolutePath}/controller?command=find_all_users"><fmt:message key="administrator.user_list"/>
                </a>

                <a style="width: 250px" class="btn btn-primary" role="button"
                   href="${absolutePath}/controller?command=find_all_rental_points"><fmt:message key="administrator.rental_point_list"/>
                </a>

                <a style="width: 250px" class="btn btn-primary" role="button"
                   href="${absolutePath}/controller?command=find_all_bicycles"><fmt:message key="administrator.bicycle_list"/>
                </a>

                <a style="width: 250px" class="btn btn-primary" role="button"
                   href="${absolutePath}/controller?command=find_all_rent_records"><fmt:message key="administrator.rent_record_list"/>
                </a>
            </div>
        </c:if>
    <br/>
    <a  class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/user_page.jsp"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
