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

<div class="page">
    <div class="row">
        <div class="container">
            <h3 class="text-center"><fmt:message key="administrator.rental_point_list"/> </h3>
            <hr>
            <br>
            <table class="table table-bordered">
                <thead>
                </thead>
                <tbody>
                <c:forEach var="bicycle" items="${bicycle_list}">
                    <td><c:out value="${rental_point.location}"/></td>
                    <td><c:out value="${bicycle.model}"/></td>
                    <td>
                        <a href="${absolutePath}/controller?command=select_bicycle&bicycle_id=${bicycle.bicycleId}&rental_point_id=${rental_point.rentalPointId}">
                            <fmt:message key="user.select"/>
                        </a>
                    </td>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="text-center">
        <ctg:footertag/>
    </div>
</div>

</body>
</html>
