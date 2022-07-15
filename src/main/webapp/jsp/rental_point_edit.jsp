<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 11.05.2022
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.epam.bicyclerental.model.entity.BicycleStatus" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<fmt:setLocale value="${locale}" scope="session" />

<fmt:setBundle basename="context.language" />

<fmt:message var="location_label" key="administrator.location"></fmt:message>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style><%@include file="../CSS/style.css"%></style>
    <title>Rental Point Edit</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <br/>
            <div class="text-center">
                <h1><fmt:message key="administrator.rental_point_edit"/></h1>
            </div>
            <form method="post" action="${absolutePath}/controller" novalidate>
                <input type="hidden" name="command" value="edit_rental_point"/>
                <input type="hidden" name="rental_point_id" value="${rental_point.rentalPointId}">
                <br/>
                <div class="row">
                    <label class="form-label">${location}</label>
                    <input type="text" maxlength="40" name="location" class="form-control form-control-sm" value = ${rental_point.location}>
                </div>
                <br/>
                <div class="text-center mb-3">
                    <button type="submit" class="btn btn-success"><fmt:message key="action.save"/></button>
                </div>
            </form>
            <br/>

            <c:choose>
                <c:when test="${rental_point_bicycle_list.size() == 0}">
                    <div class="text-center">
                        <h2><fmt:message key="administrator.no_bicycles_on_rental_point"/></h2>
                        <br/>
                        <div class="text-center">
                            <a class="btn btn-danger" href="${absolutePath}/controller?command=delete_rental_point&rental_point_id=${rental_point.rentalPointId}">
                                <fmt:message key="action.delete"/>
                            </a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center">
                        <h2><fmt:message key="administrator.bicycles_at_rental_point"/></h2>
                    </div>

                    <br/>
                    <table class="table">
                        <tbody>
                        <c:forEach var="bicycle" items="${rental_point_bicycle_list}">
                            <tr>
                                <td><c:out value="${bicycle.model}"/></td>
                                <td style="width: 20%"><c:out value="${bicycle.bicycleStatus}"/></td>
                                <td style="width: 40%; text-align: center">
                                    <c:if test="${bicycle.bicycleStatus != BicycleStatus.RENTED}">
                                        <a class="btn btn-outline-danger" href="${absolutePath}/controller?command=remove_bicycle&bicycle_id=${bicycle.bicycleId}&rental_point_id=${rental_point.rentalPointId}">
                                            <fmt:message key="action.delete"/>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>

            <br/>

            <c:if test="${inactive_bicycle_list.size() != 0}">
                <div class="text-center">
                    <h2><fmt:message key="administrator.unused_bicycles"/></h2>
                </div>
                <br/>

                <table class="table">
                    <tbody>
                        <c:forEach var="bicycle" items="${inactive_bicycle_list}">
                            <tr>
                                <td><c:out value="${bicycle.model}"/></td>
                                <td style="width: 40%; text-align: center">
                                    <a class="btn btn-outline-success" href="${absolutePath}/controller?command=add_bicycle_to_rental_point&bicycle_id=${bicycle.bicycleId}&rental_point_id=${rental_point.rentalPointId}">
                                        <fmt:message key="action.add_bicycle"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary"
       href="${absolutePath}/controller?command=find_all_rental_points"><fmt:message key="common.back"/>
    </a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
