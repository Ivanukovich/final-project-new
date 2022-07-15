<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 07.03.2022
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %>
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
    <title>User Edit</title>
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
                <h1><fmt:message key="administrator.user_edit"/></h1>
            </div>
            <form method="post" action="${absolutePath}/controller" novalidate>
                <input type="hidden" name="command" value="edit_user"/>
                <input type="hidden" name="user_id" value="${user.userId}">
                <br/>
                <div class="row">
                    <div class="col">
                        <label class="form-label"><fmt:message key="registration.first_name"/></label>
                        <input type="text" maxlength="20" name="first_name" class="form-control form-control-sm" value = ${user.firstName}>

                        <c:if test="${!empty invalid_first_name}">
                            <div class="invalid-feedback-backend" style="color: red">
                                <fmt:message key="error.incorrect_first_name"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col">
                        <label class="form-label"><fmt:message key="registration.last_name"/></label>
                        <input type="text" maxlength="20" name="last_name" class="form-control form-control-sm" value = ${user.lastName}>

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
                        <label class="form-label"><fmt:message key="registration.login"/></label>
                        <input type="text" maxlength="20" name="login" class="form-control form-control-sm" value = ${user.login}>

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
                        <label class="form-label"><fmt:message key="registration.email"/></label>
                        <input type="text" maxlength="20" name="email" class="form-control form-control-sm" value = ${user.email}>

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
                        <label class="form-label"><fmt:message key="registration.password"/></label>
                        <input type="text" maxlength="20" name="password" class="form-control form-control-sm" value = ${user.password}>

                        <c:if test="${!empty invalid_password}">
                            <div class="invalid-feedback-backend" style="color: red">
                                    ${password_error_message}
                            </div>
                        </c:if>
                    </div>
                </div>
                <br/>
                <div class="text-center mb-3">
                    <button type="submit" class="btn btn-success"><fmt:message key="action.save"/></button>
                </div>
            </form>
            <c:if test="${user.userId != user_id}">
                <br/>
                <div class="d-grid gap-2 d-md-block text-center">
                    <c:choose>
                        <c:when test="${user.role eq UserRole.USER}">
                            <a style="width: 250px" class="btn btn-outline-success" href="${absolutePath}/controller?command=create_administrator&user_id=${user.userId}">
                                <fmt:message key="action.create_administrator"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a style="width: 250px" class="btn btn-outline-danger" href="${absolutePath}/controller?command=remove_administrator&user_id=${user.userId}">
                                <fmt:message key="action.remove_administrator"/>
                            </a>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${user.blocked}">
                            <a style="width: 250px" class="btn btn-outline-success" href="${absolutePath}/controller?command=unblock_user&user_id=${user.userId}">
                                <fmt:message key="action.unblock"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a style="width: 250px" class="btn btn-outline-danger" href="${absolutePath}/controller?command=block_user&user_id=${user.userId}">
                                <fmt:message key="action.block"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${absolutePath}/controller?command=find_all_users"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
