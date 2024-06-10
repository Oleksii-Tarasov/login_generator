<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        <%@include file="css/style.css"%>
    </style>
    <title>Login Generator</title>
</head>
<body>

<div class="modal modal-sheet position-static d-block bg-body-secondary p-4 py-md-5" tabindex="-1" role="dialog"
     id="modalSheet">
    <div class="modal-dialog" role="document">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5">Login Generator</h1>
                <button type="button" class="btn-copy" onclick="copyToClipboard()">
                    <i class="fa-regular fa-copy"></i>
                </button>
            </div>
            <div class="modal-body py-0">
                <p>Enter Full Name in order:</p>
                <form class="row g-3" action="generate" method="post">
                    <div class="col-auto">
                        <input type="text" class="form-control" size=23 id="fullName" name="fullName"
                               placeholder="Name LastName Patronymic">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary mb-3">Generate Login</button>
                    </div>
                </form>
                <c:if test="${not empty login}">
                    <h1 class="modal-title fs-5">Login for `${fullName}`:</h1>
                    <h1 class="modal-title fs-5" id="generatedLogin">${login}</h1>
                </c:if>
            </div>
        </div>
    </div>

    <div class="b-example-divider"></div>

    <div class="modal-dialog" role="document">
        <div class="modal-content rounded-4 shadow">
            <div class="modal-header border-bottom-0">
                <h1 class="modal-title fs-5">Generation From File</h1>
                <div class="d-flex">
                    <button type="button" class="btn-copy me-5" onclick="downloadResults()">
                        <i class="fa-solid fa-download"></i>
                    </button>
                    <button type="button" class="btn-copy" onclick="copyTableToClipboard()">
                        <i class="fa-regular fa-copy"></i>
                    </button>
                </div>
            </div>
            <div class="modal-body py-0">
                <p>Upload File(.txt) with Full Names.</p>
                <p>Full Names should be in order: Name LastName Patronymic<br>
                    Each Full Name starts on a new line.</p>
                <form class="row g-3" action="upload" method="post" enctype="multipart/form-data">
                    <div class="col-auto">
                        <input type="file" id="file" name="file" accept=".txt">
                        <button type="submit" class="btn btn-primary mb-3">Generate Logins</button>
                    </div>
                </form>
                <c:if test="${not empty results}">
                    <table class="table" id="resultsTable">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Full Name</th>
                            <th scope="col">Generated Login</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% int id = 1; %>
                        <c:forEach var="entry" items="${results}">
                            <tr>
                                <td><%=id %>
                                </td>
                                <td>${entry.fullName}</td>
                                <td>${entry.login}</td>
                            </tr>
                            <% id++; %>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${not empty message}">
                    <h1 class="modal-title fs-5">${message}</h1>
                </c:if>
            </div>
        </div>
    </div>
</div>

<div class="copy-notification" id="copyNotification">
    Copied to clipboard!
</div>

<script>
    function copyToClipboard() {
        var textToCopy = document.getElementById("generatedLogin").innerText;
        navigator.clipboard.writeText(textToCopy).then(function () {
            showNotification("Copied to clipboard!")
        }, function (err) {
            console.error('Could not copy text: ', err);
        });
    }

    function copyTableToClipboard() {
        var table = document.getElementById("resultsTable");
        var rows = table.querySelectorAll('tr');
        var csvContent = "";
        rows.forEach(function (row) {
            var cells = row.querySelectorAll('td, th');
            var rowContent = [];
            cells.forEach(function (cell) {
                rowContent.push(cell.innerText);
            });
            csvContent += rowContent.join(",") + "\n";
        });

        navigator.clipboard.writeText(csvContent).then(function () {
            showNotification("Copied to clipboard!");
        }, function (err) {
            console.error('Could not copy table: ', err);
        });
    }

    function downloadResults() {
        window.location.href = "download";
    }

    function showNotification(message) {
        var notification = document.getElementById("copyNotification");
        notification.innerText = message;
        notification.style.display = 'block';
        setTimeout(function () {
            notification.style.display = 'none';
        }, 2000);
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
