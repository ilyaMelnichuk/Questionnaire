<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <form:form method="POST" modelAttribute="user" action="/forgot-password" >
      <fieldSet>
          <h1 class="h3 mb-3 font-weight-normal">To reset password write your email below.</h1>
          <form:label path="email">Email*</form:label>
          <form:errors path="email"/>
          <form:input path="email" id="inputEmail" class="form-control" placeholder="Email*"/>
           <button class="btn btn-lg btn-primary btn-block" type="submit">SAVE</button>
          <p class="mt-5 mb-3 text-muted"></p>
      </fieldSet>
    </form:form>

</body>
</html>