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
<fmt:setLocale value="${locale}" scope="session" />

<fmt:setBundle basename="context.language" />

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style><%@include file="../CSS/style.css"%></style>
    <title>Rent Confirmation</title>
</head>
<body>
<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <div class="row justify-content-center">
        <div class = "text-center">
            <h2>
                <fmt:message key="user.rent_confirmation_text_1"/>
                <c:out value="${bicycle.model}"/>
                <fmt:message key="user.rent_confirmation_text_2"/>
                <c:out value="${rental_point.location}"/>
                <c:out value="?"/>
            </h2>
        </div>
        <div class="text-center mb-3">
            <br/>
            <a class="btn btn-success" href="${absolutePath}/controller?command=rent_start&bicycle_id=${bicycle.bicycleId}&user_id=${user_id}">
                <fmt:message key="user.rent_bicycle"/>
            </a>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${absolutePath}/controller?command=find_available_bicycles&rental_point_id=${rental_point.rentalPointId}">
        <fmt:message key="common.back"/>
    </a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
