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
            <table class="table table-bordered">
                <tbody>
                <c:forEach var="rental_point" items="${rental_point_list}">
                    <td><c:out value="${rental_point.location}"/></td>
                    <td>
                        <a href="${absolutePath}/controller?command=find_free_bicycles&rental_point_id=${rental_point.rentalPointId}">
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
