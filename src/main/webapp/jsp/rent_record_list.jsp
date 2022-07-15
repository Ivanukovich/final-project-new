<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 26.06.2022
  Time: 18:44
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
    <title>Rent Record Database</title>
</head>
<body>

<header>
    <c:import url="header/header.jsp"/>
</header>

<div class="container">
    <br/>
    <h1 class="text-center"><fmt:message key="administrator.rental_point_list"/> </h1>
    <br/>
    <table class="table">
        <thead>
        <tr>
            <th><fmt:message key="registration.login"/></th>
            <th><fmt:message key="administrator.model"/></th>
            <th><fmt:message key="administrator.location"/></th>
            <th><fmt:message key="administrator.start"/></th>
            <th><fmt:message key="administrator.finish"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="rent_record" items="${rent_record_list}">
            <tr>
                <td style="width: 18%"><c:out value="${user_logins.get(rent_record.userId)}"/></td>
                <td style="width: 18%"><c:out value="${bicycle_models.get(rent_record.bicycleId)}"/></td>
                <td style="width: 18%"><c:out value="${rental_point_locations.get(rent_record.rentalPointId)}"/></td>
                <td style="width: 18%"><c:out value="${rent_record.start}"/></td>
                <td style="width: 18%"><c:out value="${rent_record.finish}"/></td>
                <td style="width: 10%">
                    <c:if test="${rent_record.finish != null}">
                        <a class="btn btn-danger" href="${absolutePath}/controller?command=delete_rent_record&rent_record_id=${rent_record.rentRecordId}">
                            <fmt:message key="action.delete"/>
                        </a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/database_control.jsp">
        <fmt:message key="common.back"/>
    </a>
</div>
<c:import url="footer/footer.jsp"/>
</body>
</html>
