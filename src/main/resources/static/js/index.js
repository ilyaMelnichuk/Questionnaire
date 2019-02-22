$(document).ready(function(){
        	$.ajax({
        		url: "get-fields-to-draw",
        		type: "GET",
        		dataType: "json",
        		success: function(data){
        			$.each(data, function(key, value){
        				    $group = $("<div name=\"fsd\" class=\"form-group input\" align=\"left\" data-id=\"" + value["id"] + "\">"+
            					  	"<label>"+
            						"<span style=\"color:grey;\">"+
            						value["label"]+
            						"</span>"+ 
            						(value["required"]?"*":"")+
            						"</label> <br>"+
            						"</div>"
            						);
        				    $group.attr("id", value["type"]);
        				    switch(value["type"]){
        				    case "Single line text":
        				    	$input = $("<input type=\"text\" class=\"form-control\" id=\""+ value["label"] +"\" " + (value["required"]?"required":"") + "/>").appendTo($group);
        				    	break;
        				    case "Multiline text":
        				    	$input = $("<textarea rows=\"5\" cols=\"25\" class=\"form-control\" id=\""+ value["label"] +"\" " + (value["required"]?"required":"") + "></textarea>").appendTo($group);
        				    	break;
        				    case "Radio button":
        				    	var options = value["options"].split("|");
        				        $.each(options, function(i, opt){
        				    	    $input = $("<input type=\"radio\" name=\"" + value["label"] + "\" value=\""+opt+"\" " + (value["required"]?"required":"") + "/> <span>" + opt + "</span> <br>").appendTo($group);
        				   	    });
        				    	break;
        				    case "Checkbox":
        				    	var options = value["options"].split("|");
        				    	$.each(options, function(i, opt){
        				    	    $input = $("<input type=\"checkbox\" value=\""+opt+"\"  name=\""+ opt +"\"/> <span>" + opt + "</span> <br>").appendTo($group);
        				   	    });
        				    	break;
        				    case "Combobox":
        				    	$input = $("<select>").attr("class", "form-control").appendTo($group);
        				    	var options = value["options"].split("|");
        				    	$.each(options, function(i, opt){
        				    		$input.append($("<option>").attr("value",opt).text(opt));
        				    	});
        				    	break;
        				    case "Date":
        				    	$input = $("<input type=\"date\" class=\"form-control\" id=\""+ value["label"] +"\" " + (value["required"]?"required":"") + "/>").appendTo($group);
        				    default:	
        				    }
        				    $("#form").children().last().before($group);
        			})
        		},
        	    error: function(error){
        	    	alert(JSON.stringify(error));
        	    }
        	});
        	$(document).on("submit", "#form", function(e){
        		e.preventDefault();
                var object = {
                		list:[]
                }
        		var resp;
        		$.each($(this).children("div.form-group.input"), function(){
        			resp = {};
        			resp["id"] = null;
        			resp["fieldId"] = $(this).data("id");
        			
        			
        			
        			
        			var value = "";
        			switch($(this).attr("id")){
        			    case "Single line text":
        			    	value = $(this).children("input").eq(0).val();
        			    	break;
        			    case "Multiline text":
        			    	value = $(this).children("textarea").eq(0).val();
        			    	break;
        			    case "Radio button":
        			    	value = ($(this).children("input[name=\"" + $(this).children("label").eq(0).children("span").eq(0).html() + "\"]:checked").val()==undefined?"":$(this).children("input[name=\"" + $(this).children("label").eq(0).children("span").eq(0).html() + "\"]:checked").val()); 
        			    	break;
        			    case "Checkbox":
        			    	$(this).children("input:checked").each(function(){
        			    		value = value.concat($(this).attr("name")).concat(", ");
        			    	});
        			    	value = value.substring(0, value.length - 2);
        			    	break;
        			    case "Combobox":
        			    	value = $(this).children("select").eq(0).val();
        			    	break;
        			    case "Date":
        			        value = $(this).children("input").eq(0).val();
        			}
        		    value = value.replace(new RegExp("&", "g"), "&amp");
        		    value = value.replace(new RegExp('"', "g"), '\"');
        		    value = value.replace(new RegExp("'", "g"), "\'");
        		    value = value.replace(new RegExp("<", "g"), "&lt");
        		    value = value.replace(new RegExp(">", "g"), "&gt");
        			resp["value"] = value;
        			
        			object.list.push(resp);
        		});
        		var socket = new SockJS('/responses');
        	    var stompClient = Stomp.over(socket);
        	    stompClient.connect({}, function(frame){
        	    	stompClient.send("/app/responses", {}, JSON.stringify(object.list));
        	    });
        	    setTimeout(function(){
        	    	window.location.href = "/success";
	        	    }, 1000);
        	});
        	$(document).on("click", "#reset", function(e){$.each($(this).closest("form").children("div.form-group.input"), function(){
        			switch($(this).attr("id")){
        			    case "Single line text": 
        			    	$(this).children("input").eq(0).val("");
        			    	break;
        			    case "Multiline text":
        			    	var value = $(this).children("textarea").eq(0).val("");
        			    	break;
        			    case "Radio button":
        			    	$(this).children("input[name=" + $(this).children("label").eq(0).children("span").eq(0).html() + "]:checked").prop("checked", false); 
        			    	break;
        			    case "Checkbox":
        			    	$(this).children("input").each(function(){
        			    		value = $(this).prop("checked", false);
        			    	});
        			    	break;
        			    case "Combobox":
        			    	$(this).children("select").eq(0).val("Single line text");
        			    	break;
        			    case "Date":
        			    	$(this).children("input").eq(0).val("");
        			}
        		}); 
        	});
        });