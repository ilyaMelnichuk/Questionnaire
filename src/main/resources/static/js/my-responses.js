/**

 * 
 */
function paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress){
	//$("#head").empty();
    //$("#tbody").empty();
    $("#div").empty();
    $("#div2").empty();
    $("#div").append("<label>" + (size*number + 1).toString() + "-" + (size*number + numberOfElements).toString() +  " of " + (totalElements).toString() + "</label>");
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
		$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (0).toString() + "\" action=\"/get-personal-polls-page?page=" + (number - 1).toString() + "&size=\"><input type=\"submit\" value=\"<<\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number - 1).toString() + "&size=\"><input type=\"submit\" value=\"<\" /></form></td>");
	}
	while(i < pages && i < totalPages){
		$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (startPage + i).toString() + "&size=\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
	    i++;
	}
	if(number != totalPages - 1 && totalPages != 0){
		$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number + 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (number + 1).toString() + "&size=\"><input type=\"submit\" value=\">\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-personal-polls-page?page=" + (totalPages - 1).toString() + "&size=\"><input type=\"submit\" value=\">>\" /></form></td>");
	}
	$pageList.appendTo("#pages");
	$(".js-size").attr("id", pageAddress);
}
function load(pageAddress, size){
	$.ajax({
		url:"get-default-page",
		type:"GET",
		datatype:"json",
		success: function(fieldsData){
			var fields = fieldsData["content"];
			var labels = [];
			$("#head").append( $("<th style=\"background-color:#32CD32;\">" + "Name" + "</th>"));
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
	    				$("<td style=\"background-color:#32CD32;\">"+polls[key].userName+"</td>").appendTo($row);
	    				$.each(labels, function(k, s){
    		            	$("<td id=\"" + s + "\">N/A</td>").appendTo($row);
    		            });
	    				$row.appendTo("#tbody");
	    				for(var k in polls[key].responses){
	    					$row.children("td[id=\"" + polls[key].responses[k].fieldId + "\"]").eq(0).html(polls[key].responses[k].value);
	    				}
	    		    }
	    			
	    			paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
	    			
	    			
	    			var socket = new SockJS('/responses');
	    		    var stompClient = Stomp.over(socket);
	    		    stompClient.connect({}, function(frame){
	    		    });
	    		    
	    		    setTimeout(function(){
	    		    stompClient.subscribe("/topic/responses", function (message){
      	    		    if(numberOfElements < size){
      	    		    	++numberOfElements;
      	    		    	++totalElements;
      	    		    	++size
	    				    var wpoll = JSON.parse(message.body);
	    				    $row = $("<tr id=\"" + wpoll.id + "\"></tr>");
	    				    $("<td style=\"background-color:#32CD32;\">"+wpoll.userName+"</td>").appendTo($row);
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
	    		        		paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
	    		        	}
	    		        }
	    			});
	    		    }, 500);
	    		}	
	    	});
			
			
			
		}
	
	
	
	});
}

$('document').ready(function(){
   	load("/get-personal-polls-page?page=0", "&size=5");
   	
   	$(document).on("submit", ".js-page", function(e) {
		e.preventDefault();
		var s = $(".js-size").first().val()==""?"5":$(".js-size").first().val();
		var url = $(e.target).attr("action").toString();
		$("#head").empty();
		$("#tbody").empty();
		$("#div").empty();
		$("#div2").empty();
		load(url, s);
	});
	$(document).on("keyup", ".js-size", function(e) {
		if(e.keyCode == 13){
		    e.preventDefault();
		    var s = $(e.target).val();
		    //s = "&size=" + s;
		    var url = $(e.target).attr("id");
		    $("#head").empty();
		    $("#tbody").empty();
		    $("#div").empty();
		    $("#div2").empty();
		    load(url, s);
	    }
	});
});