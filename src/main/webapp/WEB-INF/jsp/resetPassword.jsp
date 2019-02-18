<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>LOGOTYPE</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">




</head>
<body style="background-color: #f1f1f1;">
	<%@ include file = "anonymousNavbar.jsp" %>
	<div class="container" align="center">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="panel panel-default"
				style="margin-top: 10px; min-width: 400px;">
				<div class="panel-heading" align="left"
					style="background-color: white;">
					<h4>Reset Password</h4>
				</div>
				<div class="panel-body"
					style="margin-left: 15px; margin-right: 15px;">
					<form id="form" action="reset-password" method="post">
						<div class="form-group" align="left">
							<label><span style="color: grey;">Confirm New
									Password</span> *</label> <br> <input type="password" id="password"
								class="form-control"> <span id='password-message'
								style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<label id="label"><span style="color: grey;">Confirm
									New Password</span> *</label> <input type="password" id="confirmPassword"
								class="form-control" /> <span id='confirm-message'
								style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<input class="btn-primary form-control" id="button"
								style="max-width: 100px;" type="submit" value="CHANGE">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
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
	    object["message"] = $("#password").val();
	    
	    var json = JSON.stringify(object);

	    $.ajax({
	           type: "POST",
	           dataType: "json",
	           contentType: "application/json",
	           url: url,
	           data: json,
	           success: function(data)
	           {
	        	   window.location.href = "/login";
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