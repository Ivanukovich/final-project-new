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
<fmt:setLocale value="${locale}" scope="session" />

<fmt:setBundle basename="context.language" />


<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style><%@include file="../CSS/style.css"%></style>
    <title>Rental Point List</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <div class="text-center">
        <h1 class="text-center"><fmt:message key="administrator.rental_point_list"/> </h1>
    </div>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th><fmt:message key="administrator.location"/></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="rental_point" items="${rental_point_list}">
                <tr>
                    <td  style="width: 80%;"><c:out value="${rental_point.location}"/></td>
                    <td  style="width: 20%;">
                        <a class="btn btn-outline-primary" href="${absolutePath}/controller?command=find_rental_point&rental_point_id=${rental_point.rentalPointId}">
                            <fmt:message key="action.edit"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br/>
    <div class="text-center">
        <a class="btn btn-success" href="${absolutePath}/jsp/add_rental_point.jsp" role="button"><fmt:message key="administrator.add_rental_point"/></a>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/database_control.jsp"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
