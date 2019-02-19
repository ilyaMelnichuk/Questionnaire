function paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress){
    $("#div").empty();
    $("#div2").empty();
    $("#div").append("<label>" + (size*number + (numberOfElements == 0?0:1)).toString() + "-" + (size*number + numberOfElements).toString() +  " of " + (totalElements).toString() + "</label>");
	var pages;
	var startPage = 0;
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
	$("#div2").append("<table id=\"pages\"></table>");
	$pageList = $("<tr></tr>");
	if(number != 0){
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-personal-polls-page?page=" + (0).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<<\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-personal-polls-page?page=" + (0).toString() + "&size=\"><input type=\"submit\" value=\"<<\" disabled/></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<\" disabled/></form></td>");
	}
	while(i < pages && i < totalPages){
		if(number != startPage + i){
		    $pageList.append("<td><form class=\"js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (startPage + i).toString() + "&size=\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
		}else{
			$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (startPage + i).toString() + "&size=\"><input class=\"page-link\" style=\"color:blue;\"type=\"submit\" value=" + (startPage + i + 1).toString() + " disabled/></form></td>");
		}
		i++;
	}
	if(number != totalPages - 1 && totalPages != 0){
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number + 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (totalPages - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">>\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number + 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">\" disabled/></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (totalPages - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">>\" disabled/></form></td>");
	}
	$pageList.appendTo("#pages");
	$(".js-size").attr("id", pageAddress);
}
function load(pageAddress, size, stompClient){
	$.ajax({
		url:"get-default-page",
		type:"GET",
		datatype:"json",
		success: function(fieldsData){
			var fields = fieldsData["content"];
			var labels = [];
			for(var key in fields){
				$("#head").append( $("<th>" + fields[key].label + "</th>"));
				//labels.push(fields[key].label);
				labels.push(fields[key].id);
			}
			
			
			var url = pageAddress + size;
			
			$.ajax({
	    		url:url,
	    		type:"GET",
	    		datatype:"json",
	    		success: function(data){
	                //loop through the list of responses
	    			var size = parseInt(data["size"]);
	    			var numberOfElements = parseInt(data["numberOfElements"]);
	    			var totalElements = parseInt(data["totalElements"]);
	    			var totalPages = parseInt(data["totalPages"]);
	    			var number = parseInt(data["number"]);
	    			var polls = data["content"];
		            //loop through the list of responses
	    			for(var key in polls){
	    				//create new row 
	    				$row = $("<tr id=\"" + polls[key].id + "\"></tr>");
	    				$.each(labels, function(k, s){
    		            	$("<td id=\"" + s + "\">N/A</td>").appendTo($row);
    		            });
	    				$row.appendTo("#tbody");
	    				for(var k in polls[key].responses){
	    					$row.children("td[id=\"" + polls[key].responses[k].fieldId + "\"]").eq(0).html(polls[key].responses[k].value);
	    				}
	    		    }
	    			
	    			paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);

	    		    
	    		    setTimeout(function(){
	    		    stompClient.subscribe("/user/topic/my-responses", function (message){
	    				if(numberOfElements < size){
	    				    ++numberOfElements;
	    				   	++totalElements;
	    				    var wpoll = JSON.parse(message.body);
	    				    $row = $("<tr id=\"" + wpoll.id + "\"></tr>");
	    				    $.each(labels, function(k, s){
	    		        	    $("<td id=\"" + s + "\">N/A</td>").appendTo($row);
	    		            });
	    				    $row.appendTo("#tbody");
	    				    for(var k in wpoll.responses){
	    					    $row.children("td[id=\"" + wpoll.responses[k].fieldId + "\"]").eq(0).html(wpoll.responses[k].value);
	    				    }
	    		        }else{
	    		        	++totalElements;
	    		        	if(totalElements%(totalPages*size) == 0){
	    		        		++totalPages;
	    		        	}
	    		        }
	    				paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
	    			});
	    		    }, 500);
	    		}	
	    	});
			
			
			
		}
	
	
	
	});
}

$('document').ready(function(){
	var socket = new SockJS('/my-responses');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
    });
   	load("/get-personal-polls-page?page=0&size=", "5", stompClient);
   	
   	$(document).on("submit", ".js-page", function(e) {
		e.preventDefault();
		var s = $(".js-size").first().val()==""?"5":$(".js-size").first().val();
		if(parseInt(s) > 0){
			var url = $(e.target).attr("action").toString();
			$("#head").empty();
			$("#tbody").empty();
			$("#div").empty();
			$("#div2").empty();
			stompClient.unsubscribe("/user/topic/my-responses");
			load(url, s, stompClient);	
		}else{
			alert("Please, provide correct page size!");
		}
		
	});
   	
	$(document).on("keyup", ".js-size", function(e) {
		if(e.keyCode == 13){
		    e.preventDefault();
		    var s = $(e.target).val();
		    if(parseInt(s) > 0){
		    	var url = $(e.target).attr("id");
			    $("#head").empty();
			    $("#tbody").empty();
			    $("#div").empty();
			    $("#div2").empty();
			    setTimeout(function(){
			        stompClient.unsubscribe("/user/topic/my-responses");
			    }, 500);
			    load(url, s, stompClient);
		    }else{
		    	alert("Please, provide correct page size!");
		    }
	    }
	});
});