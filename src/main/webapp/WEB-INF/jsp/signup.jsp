<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
   <head>
       <title>LOGOTYPE</title>
       <meta charset="utf-8">
       <meta name="viewport" content="width=device-width, initial-scale=1">
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
       <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
       
   </head>
   <body class="text-center">
   <h1 class="h3 mb-3 font-weight-normal">Edit Profile</h1>
   <form:form method="POST" modelAttribute="userToRegister" action="/signup" class="form-signup">
      <fieldSet>
          <h1 class="h3 mb-3 font-weight-normal">Sign Up</h1>
          <label>${error}</label>
          <form:errors path="email"/>
          <form:input path="email" id="inputEmail" class="form-control" placeholder="Email*"/>
          <form:errors path="password"/>
          <form:input path="password" id="inputPassword" class="form-control" placeholder="Password*"/>
          <form:errors path="firstName"/>
          <form:input path="firstName" id="inputFirstName" class="form-control" placeholder="First name"/>
          <form:errors path="lastName"/>
          <form:input path="lastName" id="inputLastName" class="form-control" placeholder="Last name" />
          <form:errors path="phoneNumber"/>
          <form:input path="phoneNumber" id="inputPhoneNumber" class="form-control" placeholder="Phone number"/>
           <button class="btn btn-lg btn-primary btn-block" type="submit">SIGN UP</button>
          <p class="mt-5 mb-3 text-muted"></p>
      </fieldSet>
    </form:form>
    <div>
        <p>Already have account? </p>
        <a href="<c:url value="/login"/>">Log In</a>
    </div>   
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/js/getrequest.js"></script> 
    </body>
</html>