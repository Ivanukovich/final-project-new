<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 07.03.2022
  Time: 20:50
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
    <title>Rental points</title>
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
                <c:forEach var="rental_point" items="${rental_point_list}">
                    <td><c:out value="${rental_point.location}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${fn:length(rental_point_bicycle_list[rental_point.rentalPointId]) > 0}">
                                <tbody>
                                <c:forEach var="bicycle" items="${rental_point_bicycle_list[rental_point.rentalPointId]}">
                                    <td><c:out value="${bicycle.model}"/></td>
                                    <td><c:out value="${bicycle.status}"/></td>
                                    <td>
                                        <a href="${absolutePath}/controller?command=remove_bicycle&bicycle_id=${bicycle.bicycleId}">
                                            <fmt:message key="action.delete"/>
                                        </a>
                                    </td>
                                </c:forEach>
                                </tbody>
                            </c:when>
                            <c:otherwise>
                                <th><fmt:message key="administrator.no_bicycles_on_rental_point"/> </th>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </c:forEach>
                </tbody>
            </table>

            <tbody>
            <c:forEach var="bicycle" items="${inactive_bicycle_list}">
                <td><c:out value="${bicycle.model}"/></td>
                <td><c:out value="${bicycle.status}"/></td>
                <c:forEach var="rental_point" items="${rental_point_list}">
                    <td><c:out value="${rental_point.location}"/></td>
                    <a href="${absolutePath}/controller?command=add_bicycle&bicycle_id=${bicycle.bicycleId}&rental_point_id=${rental_point.rentalPointId}">
                        <fmt:message key="administrator.add_bicycle"/>
                    </a>
                </c:forEach>

            </c:forEach>
            </tbody>

        </div>
    </div>
    <div class="text-center">
        <ctg:footertag/>
    </div>
</div>

</body>
</html>
