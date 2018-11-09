<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>LOGOTYPE</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>

<form:form method="POST" modelAttribute="password" action="/change-password" class="form-signup">
      <fieldSet>
          <h1 class="h3 mb-3 font-weight-normal">Edit Profile</h1>
          <label>${message}</label>
          <label>Confirm Password</label>
          <input id="inputConfirmPassword" class="form-control" placeholder="Confirm password"/>
          <label>New Password</label>
          <form:errors path="password"/>
          <form:input path="password" id="inputNewPassword" class="form-control" placeholder="new password"/>
          <label>New Password</label>
          <input id="inputConfirmNewPassword" class="form-control" placeholder="Confirm new password"/>
           <button id="btn" type="submit" class="btn btn-lg btn-primary btn-block">CHANGE</button>
          <p class="mt-5 mb-3 text-muted"></p>
      </fieldSet>
    </form:form>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $('document').ready(function(){
        	$('#inputNewPassword').on("keyUp", function(){
        		if($('this').val == $('#inputConfirmNewPassword')){
        		    $('#btn').disabled = false;
        		}else{
        			$('#btn').disabled = true;
        		}
        	})
        		$('#inputConfirmNewPassword').on("keyUp", function(){
        		if($('this').val == $('#inputNewPassword')){
        		    $('#btn').disabled = false;
        		}else{
        			$('#btn').disabled = true;
        		}
        	})
        });
    </script>
</body>
</html>