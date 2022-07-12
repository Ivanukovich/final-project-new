<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 07.03.2022
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="by.epam.bicyclerental.model.entity.BicycleStatus" %>
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
    <title>Bicycle Database</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <h1 class="text-center"><fmt:message key="administrator.bicycle_list"/> </h1>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th><fmt:message key="administrator.model"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="bicycle" items="${bicycle_list}">
                <tr>
                    <td><c:out value="${bicycle.model}"/></td>
                    <td><c:out value="${bicycle.bicycleStatus}"/></td>
                    <td style="width: 20%">
                        <a class="btn btn-outline-primary" href="${absolutePath}/controller?command=find_bicycle&bicycle_id=${bicycle.bicycleId}">
                            <fmt:message key="action.edit"/>
                        </a>
                    </td>
                    <td style="width: 20%">
                        <c:if test="${bicycle.bicycleStatus != BicycleStatus.RENTED}">
                            <a class="btn btn-danger" href="${absolutePath}/controller?command=delete_bicycle&bicycle_id=${bicycle.bicycleId}">
                                <fmt:message key="action.delete"/>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/>
    <div class="text-center">
        <a class="btn btn-primary" href="${absolutePath}/jsp/add_bicycle.jsp" role="button"><fmt:message key="administrator.add_bicycle"/></a>
    </div>

    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/database_control.jsp"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>