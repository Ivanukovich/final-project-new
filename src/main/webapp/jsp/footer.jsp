<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 02.05.2022
  Time: 20:44
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
<body>
<div class="bg-light">
    <div class="row">
        <form method="post" action="${absolutePath}/controller" novalidate>
            <input type="hidden" name="command" value="language_change"/>
            <br/>
            <div class="col" align="center">
                <select name="locale" class="form-select form-select-sm" onchange='this.form.submit()' style="width: 100px;">
                    <option value="ru_RU" style="width: 80px;" <c:if test="${locale eq'ru_RU'}">selected="selected"</c:if>>русский</option>
                    <option value="en_US" style="width: 80px;" <c:if test="${locale eq'en_US'}">selected="selected"</c:if>>english</option>
                </select>
            </div>
        </form>
    </div>
    <div class="row text-center">
        <p><fmt:message key="footer.title"/></p>
        <p>vladimir.ivanukovich@gmail.com</p>
        <p><fmt:message key="footer.creator"/></p>
    </div>
</div>
</body>
</html>
