<%--
  Created by IntelliJ IDEA.
  User: vov4i
  Date: 07.03.2022
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="RegistrationForm" method="post" action="${absolutePath}/controller" novalidate>
    <input type="hidden" name="command" value="registration"/>

    <div class="form-group">
        <label class="form-label">${first_name_label}</label>
        <input type="text" name="first_name" class="form-control form-control-sm">
    </div>

    <div class="form-group">
        <label class="form-label">${last_name_label}</label>
        <input type="text" name="last_name" class="form-control form-control-sm">
    </div>

    <div class="form-group">
        <label class="form-label">${login_label}</label>
        <input type="text" name="login" class="form-control form-control-sm">
    </div>

    <div class="form-group">
        <label class="form-label">${email_label}</label>
        <input type="text" name="email" class="form-control form-control-sm">
    </div>

    <div class="form-group">
        <label class="form-label">${password_label}</label>
        <input type="text" name="password" class="form-control form-control-sm">
    </div>

    <div class="text-center mb-3">
        <button type="submit" class="btn btn-success"><fmt:message key="registration.submit"/></button>
    </div>
</form>
</body>
</html>
