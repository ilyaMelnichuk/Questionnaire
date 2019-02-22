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