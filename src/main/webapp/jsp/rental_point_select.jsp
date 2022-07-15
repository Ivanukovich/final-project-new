<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 18.03.2022
  Time: 15:12
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
    <title>Rental Point Select</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <div class="row justify-content-center">
        <div class = "text-center">
            <h1><fmt:message key="user.rental_point_list_text"/></h1>
        </div>
        <div class="col-md-6">
            <br/>
            <div class="list-group">
                <c:forEach var="rental_point" items="${rental_point_list}">
                    <a class="list-group-item list-group-item-action" href="${absolutePath}/controller?command=find_available_bicycles&rental_point_id=${rental_point.rentalPointId}">
                        <c:out value="${rental_point.location}"/>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/user_page.jsp"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
