<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
  </head>
<body>
<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand mr-0 mr-md-2" href="/" aria-label="LOGOTYPE"></a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Fields </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Responses</a>
            </li>
            <li class="nav-item">
                <a href="<c:url value="/logout" />">${name}</a>
            </li>
        </ul>
    </div>
</nav>
    <h1>Responses</h1>
    <script src="/jquery-3.3.1.min.js/js/bootstrap.min.js"></script>
    <script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>