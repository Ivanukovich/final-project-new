<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 20.03.2022
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <title>Bicycle Select</title>
</head>
<body>
<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <div class="row justify-content-center">
        <div class = "text-center">
            <h1><fmt:message key="user.free_bicycles_text"/><c:out value="${rental_point.location}"/></h1>
        </div>
        <div class="col-md-6">
            <br/>
            <c:choose>
                <c:when test="${bicycle_list.size() == 0}">
                    <div class="text-center">
                        <h2><fmt:message key="user.no_bicycles_on_rental_point"/></h2>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="list-group">
                        <c:forEach var="bicycle" items="${bicycle_list}">
                            <a class="list-group-item list-group-item-action" href="${absolutePath}/controller?command=select_bicycle&bicycle_id=${bicycle.bicycleId}&rental_point_id=${rental_point.rentalPointId}">
                                <c:out value="${bicycle.model}"/>
                            </a>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${absolutePath}/controller?command=find_available_rental_points"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
