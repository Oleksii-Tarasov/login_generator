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
    <label for="fullName">Enter Full Name in order: first name, last name, patronymic:</label>
    <input type="text" id="fullName" name="fullName">
    <button type="submit">Generate Login</button>
</form>

<c:if test="${not empty error}">
    <h2>${error}</h2>
</c:if>

<c:if test="${not empty login}">
    <h2>Generated Login for `${fullName}`: ${login}</h2>
</c:if>

<form action="upload" method="post" enctype="multipart/form-data">
    <label for="file">Upload File(.txt) with Full Names</label>
    <input type="file" id="file" name="file" accept=".txt">
    <button type="submit">Upload and Generate Logins</button>
</form>

<c:if test="${not empty results}">
    <h2>Generated Logins</h2>
    <table border="1">
        <tr>
            <th>Original Name</th>
            <th>Generated Login</th>
        </tr>
        <c:forEach var="entry" items="${results}">
            <tr>
                <td>${entry.fullName}</td>
                <td>${entry.login}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>
