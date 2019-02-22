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