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
    <div class="navbar-header" style="margin-left:150px">
      <a class="navbar-brand" href="${contextPath}/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="${contextPath}/fields">Fields</a></li>
      <li><a href="${contextPath}/responses">Responses</a></li>
      <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">${name}
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li ><a href="${contextPath}/edit-profile">Edit Profile</a></li>
          <li><a href="${contextPath}/change-password">Change Password</a></li>
          <li><a href="${contextPath}/logout">Log Out</a></li>
        </ul>
      </li>
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
                            Edit Profile
                        </h4>
                        <span id='message'></span>
                   </div>
                   <div class="panel-body" style="margin-left:15px; margin-right:15px;">
                   <form id="form"  action="${contextPath}/check-changes" method="post">
                       <div class="form-group" align="left">
                           <label><span style="color:grey;">First Name</span></label> <br>
                           <input type="text" id = "firstName" class="form-control">
                           <span id='current-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <label><span style="color:grey;">Last Name</span></label> <br>
                           <input type="text" id = "lastName" class="form-control">
                           <span id='password-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <label><span style="color:grey;">Email</span> *</label>
                           <input type="email" id = "email" class="form-control"/>
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <label><span style="color:grey;">Phone Number</span></label>
                           <input type="text" id = "phoneNumber" class="form-control"/>
                           <span id='confirm-message' style="color:red;"></span>
                       </div>
                       <div class="form-group" align="left">
                           <input class="btn-primary form-control" id="button" style="max-width:100px;" type="submit" value="SAVE">
                       </div>
                   </form>
                   </div>
               </div>
           </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function(){
        	$.ajax({
        		url: "load-user-data",
        		type: "GET",
        		dataType: "json",
        		success: function(data){
        			$.each(data, function(key, value){
        				$("#" + key).val(value);
        			})
        		},
        	    error: function(error){
        	    	alert(JSON.stringify(error));
        	    }
        	});
        });
        $('#form').on("submit", function(e) {
        	    e.preventDefault(); 
        	    var form = $(this);
        	    var url = form.attr('action');
        	    var object = {};
        	    object["email"] = $("#email").val();
        	    object["firstName"] = $("#firstName").val();
        	    object["lastName"] = $("#lastName").val();
        	    object["phoneNumber"] = $("#phoneNumber").val();
        	    $.ajax({
        	           type: "POST",
        	           dataType: "json",
        	           contentType: "application/json",
        	           url: url,
        	           data: JSON.stringify(object),
        	           success: function(data)
        	           {
                	           $("#message").html(data["message"]);
        	           },
        	           error: function(data){
        	        	   alert(JSON.stringify(data));
        	           }
                });
        	    
        }); 
        
    </script>
</body>
</html>