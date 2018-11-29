/**
 * 
 */
$('document').ready(function(){
    	$.ajax({
    		url:"get-default-page",
    		type:"GET",
    		datatype:"json",
    		success: function(data){
    			var fields = data["content"];
    			var labels = [];
    			for(var key in fields){
    				$("#head").append( $("<th>" + fields[key].label + "</th>"));
    				labels.push(fields[key].label);
    			}
    			$.ajax({
    	    		url:"get-all-responses",
    	    		type:"GET",
    	    		datatype:"json",
    	    		success: function(responses){
    		            var index = -1;
    		            //loop through the list of responses
    	    			for(var key in responses){
    	    				//create new row 
    	    				if(index != responses[key].id){
    	    					$row = $("<tr id=\"" + responses[key].id + "\"></tr>");
    	    		            $.each(labels, function(k, s){
    	    		            	$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
    	    		            });
    	    		            $row.appendTo("#tbody");
    	    				    index = responses[key].id;
    	    			    }
    	    				$row = $("tr[id=\"" + responses[key].id + "\"]");
    		                $row.children("td[name=\"" + responses[key].label + "\"]").eq(0).html(responses[key].value);
    	    		    }	
    		            
    	    			var socket = new SockJS('/responses');
    	        	    var stompClient = Stomp.over(socket);
    	        	    stompClient.connect({}, function(frame){
    	        	    });
    	        	    
    	        	    setTimeout(function(){
    	        	    stompClient.subscribe("/topic/responses", function (message){
	    	    			var response = JSON.parse(message.body);
	    	    			$row = $("<tr></tr>");
	    	    			$.each(labels, function(k, s){
	    		            	$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
	    		            });
	    	    			for(var key in response){
	    		                $row.children("td[name=\"" + response[key].label + "\"]").eq(0).html(response[key].value);
	    	    			}
	    	    			$row.appendTo("#tbody");
	    	    		});
    	        	    }, 500);
    	    		}
    	    	});
    		}
    	});
   });