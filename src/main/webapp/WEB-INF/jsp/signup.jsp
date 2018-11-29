<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>LOGOTYPE</title>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>

<body style="background-color:#f1f1f1;">
    <nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header" style="margin-left:150px;">
      <a class="navbar-brand" href="/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px;">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="/login">Log In</a></li>
    </ul>
    </div>
    </div>
    </nav>
    <div class="container" align="center">
           <div class="col-lg-4"></div>
           <div class="col-lg-4">
               <div class="panel panel-default" style="margin-top:10px; min-width: 400px;">
                   <div class="panel-heading" align="left" style="background-color:white;">
                        <h4>
                            Sign Up
                        </h4>
                        <span id='message'></span>
                   </div>
                   <div class="panel-body" style="margin-left:15px; margin-right:15px;">
                   <form id="form"  action="/signup" method="post">
                       <div class="form-group" align="left">
                           <input type="email" id = "email" class="form-control" placeholder="Email*">
                           <span id='current-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input type="password" id = "password" class="form-control" placeholder="Password*">
                           <span id='password-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input type="password" id = "confirmPassword" class="form-control" placeholder="Confirm Password*">
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input type="text" id = "firstName" class="form-control" placeholder="First Name"/>
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input type="text" id = "lastName" class="form-control" placeholder="Last Name"/>
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input type="text" id = "phoneNumber" class="form-control" placeholder="Phone Number"/>
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input class="btn-primary form-control" id="button" style="max-width:100px;" type="submit" value="SIGN UP">
                       </div>
                </form>
                </div>
               </div>
           </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $('#form').on("submit", function(e) {
        	    e.preventDefault(); 
        	    
        	    var b = true;
            	if($("#password").val().length < 6){
                	$("#password-message").html("password should have 6 characters at least");
                	b = false;
                }else{
                	$("#password-message").html("");
                }
                if($("#password").val() != $("#confirmPassword").val()){
                	$("#confirm-message").html("passwords do not match");
                	b = false;
                }else{
                	$("#confirm-message").html("");
                }
                
                if(b){
        	    var form = $(this);
        	    var url = form.attr('action');
        	    var object = {};
        	    object["email"] = $("#email").val();
        	    object["password"] = $("#password").val();
        	    object["firstName"] = $("#firstName").val();
        	    object["lastName"] = $("#lastName").val();
        	    object["phoneNumber"] = $("#phoneNumber").val();
        	    
        	    var json = JSON.stringify(object);
        	    
        	    $.ajax({
        	           type: "POST",
        	           dataType: "json",
        	           contentType: "application/json",
        	           url: url,
        	           data: json,
        	           success: function(data)
        	           {
                	       $("#message").html(data["message"]);
        	           },
        	           error: function(data){
        	        	   alert(JSON.stringify(data));
        	           }
                });
                }
        	    
        }); 
        
    </script>
</body>
</html>