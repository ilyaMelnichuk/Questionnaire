<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>LOGOTYPE</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body style="background-color:#f1f1f1">
<nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header" style="margin-left:150px;">
      <a class="navbar-brand" href="${contextPath}/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="${contextPath}/fields">Fields</a></li>
      <li><a href="${contextPath}/responses">Responses</a></li>
      <li><a href="${contextPath}/login">Log In</a></li>
    </ul>
    </div>
    </div>
    </nav>
    <div class="container" align="center">
           <div class = "jumbotron p-2" style="max-width:400px; background-color:white; padding-bottom:5px; padding-left:30px; padding-top:15px" align="center">
               <h4>Thank you!</h4><br>
               <h5>Your response was saved</h5>
           </div>
    </div>
</body>
</html>