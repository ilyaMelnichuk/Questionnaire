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