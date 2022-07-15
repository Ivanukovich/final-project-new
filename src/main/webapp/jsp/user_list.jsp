<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %><%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 02.03.2022
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="by.epam.bicyclerental.model.entity.UserStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<fmt:setLocale value="${locale}" scope="session" />
<c:set var="USER" value="<%=UserRole.USER%>"/>

<fmt:setBundle basename="context.language" />

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style><%@include file="../CSS/style.css"%></style>
    <title>User Database</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <div class="text-center">
        <h1 ><fmt:message key="administrator.user_list"/> </h1>
    </div>
    <br/>
    <table class="table">
        <thead>
            <tr>
                <th><fmt:message key="registration.first_name"/></th>
                <th><fmt:message key="registration.last_name"/></th>
                <th><fmt:message key="registration.email"/></th>
                <th><fmt:message key="registration.login"/></th>
                <th><fmt:message key="administrator.status"/></th>
            </tr>
        </thead>
        <tbody>

        <c:forEach var="user" items="${user_list}">
            <tr data-href="${absolutePath}/controller?command=find_user&user_id=${user.userId}">
                <td style="width: 18%;"><c:out value="${user.firstName}"/></td>
                <td style="width: 18%;"><c:out value="${user.lastName}"/></td>
                <td style="width: 18%;"><c:out value="${user.email}"/></td>
                <td style="width: 18%;"><c:out value="${user.login}"/></td>
                <td style="width: 8%;"><c:out value="${user.userStatus}"/></td>
                <td class="text-center" style="width: 10%;">
                    <a class="btn btn-outline-primary" href="${absolutePath}/controller?command=find_user&user_id=${user.userId}">
                        <fmt:message key="action.edit"/>
                    </a>
                </td>
                <td class="text-center" style="width: 10%;">
                    <c:if test="${user.userId != user_id && user.userStatus != UserStatus.ACTIVE}">
                        <a class="btn btn-danger" href="${absolutePath}/controller?command=delete_user&user_id=${user.userId}">
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
        <a class="btn btn-success" href="${absolutePath}/jsp/add_user.jsp" role="button"><fmt:message key="administrator.add_user"/></a>
    </div>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/database_control.jsp"><fmt:message key="common.back"/></a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
