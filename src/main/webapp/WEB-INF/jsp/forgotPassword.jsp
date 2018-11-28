<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
    <div class="navbar-header">
      <a class="navbar-brand" href="/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="/login">Log In</a></li>
    </ul>
    </div>
    </nav>
    <div class="container" align="center">
           <div class="col-lg-4"></div>
           <div class="col-lg-4">
               <div class="panel panel-default" style="margin-top:10px; min-width: 400px;">
                   <div class="panel-heading" align="left" style="background-color:white;">
                        <h4>
                            Forgot Your Password?
                        </h4>
                        <span id='message'></span>
                   </div>
                   <div class="panel-body" style="margin-left:15px; margin-right:15px;">
                   <form id="form"  action="/forgot-password" method="post">
                       <div class="form-group" align="left">
                           <label><span style="color:grey;">Email</span></label> <br>
                           <input type="email" id = "email" class="form-control">
                       </div>
                       <div class="form-group" align="left">
                           <input class="btn-primary form-control" id="button" style="max-width:200px;" type="submit" value="SEND RESET LINK">
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
	    var form = $(this);
	    var url = form.attr('action');
	    var object = {};
	    object["email"] = $("#email").val();
	    
	    var json = JSON.stringify(object);
	    $.ajax({
	           type: 'POST',
	           dataType: "json",
	           contentType: "application/json",
	           url: url,
	           data: json,
	           success: function(data)
	           {
        	           $("#message").html(data["message"]);
        	           $("#email").val("");
	           },
	           error: function(data){
	        	   alert(JSON.stringify(data));
	           }
        });
	    
     }); 
        
    </script>
</body>
</html>