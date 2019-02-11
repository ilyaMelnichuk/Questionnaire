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
    	    		success: function(polls){
    	    			var index = -1;
    		            //loop through the list of responses
    	    			for(var key in polls){
    	    				//create new row 
    	    				$row = $("<tr id=\"" + polls[key].id + "\"></tr>");
    	    				$("<td style=\"background-color:#32CD32;\">"+polls[key].userName+"</td>").appendTo($row);
    	    				$.each(labels, function(k, s){
	    		            	$("<td id=\"" + s + "\">N/A</td>").appendTo($row);
	    		            });
    	    				$row.appendTo("#tbody");
    	    				for(var k in polls[key].responses){
    	    					$row.children("td[id=\"" + polls[key].responses[k].fieldId + "\"]").eq(0).html(polls[key].responses[k].value);
    	    				}
    	    		    }
    	    		}	
    	    	});
    		}
    	});
   });