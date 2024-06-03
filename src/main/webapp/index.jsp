<%--<%@ page language="java" contentType="text/html" pageEncoding="iso-8859-1" import="java.util.*" %>--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Generator</title>
</head>
<body>
<h1>Login Generator</h1>
<form action="generate" method="post">
    <label for="fullName">Enter Full Name:</label>
    <input type="text" id="fullName" name="fullName" required>
    <button type="submit">Generate Login</button>
</form>

<c:if test="${not empty login}">
<%--    <label for="fullName"></label>--%>
    <h2>Generated Login: ${login}</h2>
</c:if>
<%--<% String login = (String) request.getAttribute("login"); %>--%>
<%--<% if (login != null && !login.isEmpty()) { %>--%>
<%--    <h2>Generated Login: <%= login %></h2>--%>
<%--<% } %>--%>
</body>
</html>
