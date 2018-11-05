<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
   <head>
   
       <meta charset="utf-8" />
       <meta name="viewport" content="width=device-width, initial-scale=1" />
       <link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
       <script src="/jquery-3.3.1.min.js/js/bootstrap.min.js"></script>
       <script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>
   </head>
   <body class="text-center">
   <form:form method="POST" modelAttribute="user" action="/create-user" class="form-signup">
      <fieldSet>
      <h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>
      <form:label path="email"  class="sr-only">Email address</form:label>
      <form:input path="email" id="inputEmail" class="form-control" placeholder="Email address"/>
      <form:label path="password" class="sr-only">Password</form:label>
      <form:input path="password" id="inputPassword" class="form-control" placeholder="Password"/>
      <form:label path="firstName" class="sr-only">First Name</form:label>
      <form:input path="firstName" id="inputFirstName" class="form-control" placeholder="First name"/>
      <form:label path="lastName" class="sr-only">Last Name</form:label>
      <form:input path="lastName" id="inputLastName" class="form-control" placeholder="Last name" />
      <form:label path="phoneNumber" class="sr-only">Password</form:label>
      <form:input path="phoneNumber" id="inputPhoneNumber" class="form-control" placeholder="Phone number"/>
           <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
          <p class="mt-5 mb-3 text-muted"></p>
      </fieldSet>
    </form:form>
    
       
     
    </body>
</html>