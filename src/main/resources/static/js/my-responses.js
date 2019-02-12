
function load(pageAddress, size, labels){
	var url = pageAddress + size;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
            //loop through the list of responses
			var polls = data["content"];
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
			
            //pagination widgets
			
			$("div.paging#1").append("<label>" + (parseInt(data["size"])*parseInt(data["number"]) + 1).toString() + "-" + (parseInt(data["size"])*parseInt(data["number"]) + parseInt(data["numberOfElements"])).toString() +  " of " + data["totalElements"] + "</label>");
			var pages;
			var startPage = 0;
			var totalPages = parseInt(data["totalPages"]);
			var number = parseInt(data["number"]);
			if(totalPages < 5){
				pages = totalPages;
			}else{
				pages = 5;
			}
			
			if(number < 2){
				startPages = 0;
			}else if(totalPages - number < 3){ 
			  //fix
				if(totalPages - number == 1){
					if(totalPages < 5){
						startPage = 0;
					}else{
					    startPage = number - 4;	
					}
				}else{
					if(totalPages < 5){
						startPage = 0;
					}else{
					    startPage = number - 3;	
					}
				}
			}else{
				startPage = number - 2;
			}
			var i = 0;
			$(".paging#2").append("<table id=\"pages\"></table>");
			$pageList = $("<tr></tr>");
			if(number != 0){
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "&size=\"><input type=\"submit\" value=\"<<\" /></form></td>");
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "&size=\"><input type=\"submit\" value=\"<\" /></form></td>");
			}
			while(i < pages && i < totalPages){
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "&size=\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
			    i++;
			}
			if(number != totalPages - 1 && totalPages != 0){
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "&size=\"><input type=\"submit\" value=\">\" /></form></td>");
				$pageList.append("<td><form class=\"js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "&size=\"><input type=\"submit\" value=\">>\" /></form></td>");
			}
			$pageList.appendTo("#pages");
			$(".js-size").attr("id", pageAddress);
            
			var socket = new SockJS('/responses');
    	    var stompClient = Stomp.over(socket);
    	    stompClient.connect({}, function(frame){
    	    });
    	    
    	    setTimeout(function(){
    	    stompClient.subscribe("/topic/responses", function (message){
    			var response = JSON.parse(message.body);
    			$row = $("<tr></tr>");
    			$("<td style=\"background-color:#32CD32;\">"+responses[key].user+"</td>").appendTo($row);
    			$.each(labels, function(k, s){
	            	//$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
	            	$("<td id=\"" + s + "\">N/A</td>").appendTo($row);
	            });
    			for(var key in response){
	                //$row.children("td[name=\"" + response[key].label + "\"]").eq(0).html(response[key].value);
	                $row.children("td[id=\"" + response[key].fieldId + "\"]").eq(0).html(response[key].value);
    			}
    			$row.appendTo("#tbody");
    		});
    	    }, 500);
		}
	});
}
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
    			
    			load("/get-personal-polls-page?page=0", "&size=5", labels);
    		}
    	});
   });