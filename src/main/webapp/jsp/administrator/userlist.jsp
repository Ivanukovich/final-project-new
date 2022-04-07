<%@ page import="by.epam.bicyclerental.model.entity.UserRole" %><%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 02.03.2022
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<c:set var="USER" value="<%=UserRole.USER%>"/>

<fmt:setBundle basename="context.language" />


<html>
<head>
    <script>
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function() {
            null
        };
    </script>

    <title>Title</title>
</head>
<body>

<div class="page">
    <div class="row">
        <div class="container">
            <h3 class="text-center"><fmt:message key="administrator.user_list"/> </h3>
            <hr>
            <br>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><fmt:message key="administrator.first_name"/> </th>
                    <th><fmt:message key="administrator.last_name"/> </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${user_list}">
                    <tr>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.blocked}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <td>
                            <a href="${absolutePath}/controller?command=create_administrator&user_id=${user.userId}">
                                <fmt:message key="administrator.create_administrator"/>
                            </a>
                        </td>

                        <td>
                            <a href="${absolutePath}/controller?command=edit_user&user_id=${user.userId}">
                                <fmt:message key="action.edit"/>
                            </a>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${user.blocked}">
                                    <a href="${absolutePath}/controller?command=unblock_user&user_id=${user.userId}">
                                        <fmt:message key="action.unblock"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${absolutePath}/controller?command=block_user&user_id=${user.userId}">
                                        <fmt:message key="action.block"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <a href="${absolutePath}/controller?command=delete_user&user_id=${user.userId}">
                                <fmt:message key="action.delete"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="text-center">
                <a class="btn btn-info" href="${absolutePath}/jsp/administrator/usercreation.jsp" role="button"><fmt:message key="administrator.add_user"/></a>
            </div>
        </div>
    </div>
    <div class="text-center">
        <ctg:footertag/>
    </div>
</div>

</body>
</html>
