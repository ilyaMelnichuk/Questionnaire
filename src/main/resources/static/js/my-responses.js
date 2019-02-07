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
    			$("#head").append( $("<th style=\"background-color:#32CD32;\">" + "Name" + "</th>"));
    			for(var key in fields){
    				$("#head").append( $("<th>" + fields[key].label + "</th>"));
    				//labels.push(fields[key].label);
    				labels.push(fields[key].id);
    			}
    			$.ajax({
    	    		url:"get-personal-responses",
    	    		type:"GET",
    	    		datatype:"json",
    	    		success: function(responses){
    		            var index = -1;
    		            //loop through the list of responses
    	    			for(var key in responses){
    	    				//create new row 
    	    				if(index != responses[key].id){
    	    					$row = $("<tr id=\"" + responses[key].id + "\"></tr>");
    	    					$("<td style=\"background-color:#32CD32;\">"+responses[key].user+"</td>").appendTo($row);
    	    		            $.each(labels, function(k, s){
    	    		            	$("<td id=\"" + s + "\">N/A</td>").appendTo($row);
    	    		            	//$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
    	    		            });
    	    		            $row.appendTo("#tbody");
    	    				    index = responses[key].id;
    	    			    }
    	    				$row = $("tr[id=\"" + responses[key].id + "\"]");
    		                //$row.children("td[name=\"" + responses[key].label + "\"]").eq(0).html(responses[key].value);
    		                $row.children("td[id=\"" + responses[key].fieldId + "\"]").eq(0).html(responses[key].value);
    	    		    }	
    	    		}
    	    	});
    		}
    	});
   });