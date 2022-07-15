<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 27.04.2022
  Time: 22:03
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
    <title>Bicycle Rent</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>


<div class="container">
    <br/>
    <c:choose>
        <c:when test="${not empty sessionScope.user_id}">
            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user_page.jsp"><fmt:message key="user.home_page"/></a>
        </c:when>
        <c:otherwise>
            <h1><fmt:message key="guest.home_page_text"/></h1>
        </c:otherwise>
    </c:choose>
</div>

<c:import url="footer/footer.jsp"/>
</body>
</html>
