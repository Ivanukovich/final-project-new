<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="by.epam.bicyclerental.model.entity.BicycleStatus" %>
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
    <title>Bicycle Edit</title>
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
                <h1><fmt:message key="administrator.bicycle_edit"/></h1>
            </div>

            <form name="BicycleEditForm" method="post" action="${absolutePath}/controller" novalidate>
                <input type="hidden" name="command" value="edit_bicycle"/>
                <input type="hidden" name="bicycle_id" value="${bicycle.bicycleId}">
                <br/>

                <div class="row">
                    <label class="form-label"><fmt:message key="administrator.model"/></label>
                    <input type="text" maxlength="20" name="model" class="form-control form-control-sm" value = ${bicycle.model}>
                </div>

                <br/>
                <div class="text-center mb-3">
                    <button type="submit" class="btn btn-success"><fmt:message key="action.save"/></button>
                </div>
            </form>
        </div>
    </div>
    <a class="btn btn-secondary" href="${absolutePath}/controller?command=find_all_bicycles">
        <fmt:message key="common.back"/>
    </a>
</div>
<c:import url="footer.jsp"/>
</body>
</html>

