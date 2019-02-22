$('#form').on("submit", function(e) {
	    e.preventDefault(); 
	    
	    var b = true;
	    if($("#password").val().length < 6){
        	$("#current-message").html("please enter your current password");
        	b = false;
        }
    	if($("#newPassword").val().length < 6){
        	$("#password-message").html("password should have 6 characters at least");
        	b = false;
        }else{
        	$("#password-message").html("");
        }
        if($("#newPassword").val() != $("#confirmNewPassword").val()){
        	$("#confirm-message").html("passwords do not match");
        	b = false;
        }else{
        	$("#confirm-message").html("");
        }
        
        if(b){
	    var form = $(this);
	    var url = form.attr('action');
	    var object = {};
	    object["password"] = $("#password").val();
	    object["newPassword"] = $("#newPassword").val();
	    
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