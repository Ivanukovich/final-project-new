<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 25.02.2022
  Time: 15:41
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
    <title>Registration</title>
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
                <h1><fmt:message key="registration.title"/></h1>
            </div>
            <form method="post" action="${absolutePath}/controller" novalidate>
                <input type="hidden" name="command" value="registration"/>
                <br/>
                <div class="row">
                    <div class="col">
                        <label><fmt:message key="registration.first_name"/></label>
                        <input type="text" maxlength="20" class="form-control form-control-sm" name="first_name">
                        <c:if test="${!empty invalid_first_name}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_first_name"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col">
                        <label><fmt:message key="registration.last_name"/></label>
                        <input type="text" maxlength="20" class="form-control form-control-sm" name="last_name">
                        <c:if test="${!empty invalid_last_name}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_last_name"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col">
                        <label><fmt:message key="registration.login"/></label>
                        <input type="text" maxlength="20" class="form-control form-control-sm" name="login">
                        <c:if test="${!empty invalid_login}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_login"/>
                            </div>
                        </c:if>
                        <c:if test="${!empty login_already_exists}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.login_was_taken"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col">
                        <label><fmt:message key="registration.email"/></label>
                        <input type="text" maxlength="20" class="form-control form-control-sm" name="email">
                        <c:if test="${!empty invalid_email}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_email"/>
                            </div>
                        </c:if>
                        <c:if test="${!empty email_already_exists}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.email_was_taken"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-6">
                        <label><fmt:message key="registration.password"/></label>
                        <input type="text" maxlength="20" class="form-control form-control-sm" name="password">
                        <c:if test="${!empty invalid_password}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_password"/>
                            </div>
                        </c:if>
                    </div>
                </div>
                <br/>
                <div class="text-center mb-3">
                    <button type="submit" class="btn btn-success"><fmt:message key="registration.submit"/></button>
                </div>
            </form>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/home.jsp">
        <fmt:message key="common.back"/>
    </a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>