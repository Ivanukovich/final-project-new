<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ctg" uri="customtags" %>

<c:set var="absolutePath">${pageContext.request.contextPath}</c:set>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="context.language" />

<html>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand"><fmt:message key="common.site_name"/></a>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:if test="${not empty sessionScope.user_id}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/jsp/user_page.jsp"><fmt:message key="user.home_page"/></a>
                    </li>
                </c:if>
            </ul>
            <ul class="nav justify-content-end">
                <c:choose>
                    <c:when test="${not empty sessionScope.user_id}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/profile.jsp"><fmt:message key="user.profile"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${absolutePath}/controller?command=logout"><fmt:message key="user.logout"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message key="guest.log_in"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/jsp/registration.jsp"><fmt:message key="guest.registration"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </nav>
</body>
</html>