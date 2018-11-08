<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>LOGOTYPE</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<nav class="navbar">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">WebSiteName</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Home</a></li>
      <li ><a href="#">Page 1</a></li>
      <li><a href="#">Page 2</a></li>
      <li><a href="#">Page 3</a></li>
    </ul>
  </div>
</nav>
  
<form:form method="POST" modelAttribute="user" action="/editProfile" class="form-signup">
      <fieldSet>
          <h1 class="h3 mb-3 font-weight-normal">Sign Up</h1>
          <label>${error}</label>
          <form:label path="firstName">firstName</form:label>
          <form:errors path="firstName"/>
          <form:input path="firstName" id="inputFirstName" class="form-control" placeholder="First name"/>
          <form:label path="lastName">lastName</form:label>
          <form:errors path="lastName"/>
          <form:input path="lastName" id="inputLastName" class="form-control" placeholder="Last name" />
          <form:label path="email">Email*</form:label>
          <form:errors path="email"/>
          <form:input path="email" id="inputEmail" class="form-control" placeholder="Email*"/>
          <form:label path="phoneNumber">phoneNumber</form:label>
          <form:errors path="phoneNumber"/>
          <form:input path="phoneNumber" id="inputPhoneNumber" class="form-control" placeholder="Phone number"/>
           <button class="btn btn-lg btn-primary btn-block" type="submit">SIGN UP</button>
          <p class="mt-5 mb-3 text-muted"></p>
      </fieldSet>
    </form:form>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/js/getrequest.js"></script>
</body>
</html>
