<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 26.02.2022
  Time: 21:09
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
    <title>Login</title>
</head>
<body>
<header>
    <c:import url="header/header.jsp"/>
</header>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-3">
            <br/>
            <div class="text-center">
                <h1><fmt:message key="guest.authorisation"/></h1>
            </div>
            <form name="LoginForm" method="post" action="${absolutePath}/controller" novalidate>
                <input type="hidden" name="command" value="login"/>
                <br/>
                <div class="row">
                    <label><fmt:message key="guest.login"/></label>
                    <input type="text" maxlength="20" name="login" class="form-control form-control-sm">
                </div>
                <br/>
                <div class="row">
                    <label><fmt:message key="guest.password"/></label>
                    <input type="password" maxlength="20" name="password" class="form-control form-control-sm">

                    <c:if test="${!empty incorrect_login_or_password}">
                        <div style="color: #ff0000">
                            <fmt:message key="error.incorrect_login_or_password"/>
                        </div>
                    </c:if>
                </div>
                <br/>
                <div class="text-center mb-3">
                    <button type="submit" class="btn btn-success"><fmt:message key="guest.submit"/></button>
                </div>
            </form>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/home.jsp"><fmt:message key="common.back"/></a>
</div>

<c:import url="footer/footer.jsp"/>
</body>
</html>
