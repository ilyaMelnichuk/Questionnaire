/**
 * 
 */
function getPage(url){
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
		    return data["content"];
		}
	});
}
function draw(fields){
	$("#tbody").empty();
	$("#tbody").append("<tr></tr>");
	for(var key in fields){ 
		$delete = "<button data-delete-id=\"" + fields[key].label + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>";
		$edit = "<button id=\"" + fields[key].label + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>";
		$row = $('<tr>'+
		      '<td class=\"field-label\" data-id=\"' + fields[key].id + '\">'+fields[key].label+'</td>'+
		      '<td class=\"field-type\" data-options=\"'+fields[key].options+'\">'+fields[key].type+'</td>'+
		      '<td class=\"field-required\">'+fields[key].required+'</td>'+
		      '<td class=\"field-isActive\">'+fields[key].isActive+'</td>'+
		      '<td>'+
		      '<div align=\"right\">'+
		      $edit+ 
		      $delete+
		      '</div>'+
	          '</td>'+
		      '</tr>');
		$row.attr("id", fields[key].label);
		$("#tbody").append($row);
	}
}
function paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress){
	$(".paging#1").empty();
    $(".paging#2").empty();
    $(".paging#1").append("<label>" + (size*number + 1).toString() + "-" + (size*number + numberOfElements).toString() +  " of " + (totalElements).toString() + "</label>");
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
}

function load(pageAddress, size){
	var url = pageAddress + size;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
			var size = parseInt(data["size"]);
			var numberOfElements = parseInt(data["numberOfElements"]);
			var totalElements = parseInt(data["totalElements"]);
			var totalPages = parseInt(data["totalPages"]);
			var number = parseInt(data["number"]);
			var fields = data["content"];
			draw(fields);
			/*$("#tbody").empty()
			$("#tbody").append("<tr></tr>")
			for(var key in fields){ 
				$delete = "<button data-delete-id=\"" + fields[key].label + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>";
				$edit = "<button id=\"" + fields[key].label + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>";
				$row = $('<tr>'+
				      '<td class=\"field-label\" data-id=\"' + fields[key].id + '\">'+fields[key].label+'</td>'+
				      '<td class=\"field-type\" data-options=\"'+fields[key].options+'\">'+fields[key].type+'</td>'+
				      '<td class=\"field-required\">'+fields[key].required+'</td>'+
				      '<td class=\"field-isActive\">'+fields[key].isActive+'</td>'+
				      '<td>'+
				      '<div align=\"right\">'+
				      $edit+ 
				      $delete+
				      '</div>'+
			          '</td>'+
				      '</tr>');
				$row.attr("id", fields[key].label);
				$("#tbody").append($row);
			}*/
			
			paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
			
			var $activeField;
        	
		    $(document).on("click", ".delete", function(){
			    if(confirm("Are you sure you want to delete field \"" + $(this).closest("tr").children("td").eq(0).html() + "\"?")){
			    	if(numberOfElements < 2 && number != 0){
			    		var s = "&size=" + (size).toString();
			    		var url = "get-fields-page?page=" + (number - 1).toString() + s;
			    		label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
				    	$.ajax({
			  	    		  url:"delete-field",
			  	    		  type:"POST",
			  	    		  data:JSON.stringify(label),
			  	    		  datatype:"json",
			  	    		  contentType:"application/json",
			  	    		  success:function(data){
			  	    		  }
			  	    	});
			    		newFields = getPage(url);
	        		    draw(newFields);
	        		    paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
    		        }else if(number + 1 < totalPages){
			    		--numberOfElements;
    		        	var s = "&size=" + (size).toString();
			    		var url = "get-fields-page?page=" + (number).toString() + s;
			    		var label = {};
				    	label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
				    	$.ajax({
			  	    		  url:"delete-field",
			  	    		  type:"POST",
			  	    		  data:JSON.stringify(label),
			  	    		  datatype:"json",
			  	    		  contentType:"application/json",
			  	    		  success:function(data){
			  	    		  }
			  	    	   });
	        		    newFields = getPage(url);
	        		    draw(newFields);
	        		    paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
    		        }else if(number != 0){
			    	var label = {};
			    	label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
			    	$.ajax({
		  	    		  url:"delete-field",
		  	    		  type:"POST",
		  	    		  data:JSON.stringify(label),
		  	    		  datatype:"json",
		  	    		  contentType:"application/json",
		  	    		  success:function(data){
		  	    		  }
		  	    	   });
			    	$(this).closest("tr").remove();
			       }else{
			    	   var label = {};
			    	   label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
				    	$.ajax({
			  	    		  url:"delete-field",
			  	    		  type:"POST",
			  	    		  data:JSON.stringify(label),
			  	    		  datatype:"json",
			  	    		  contentType:"application/json",
			  	    		  success:function(data){
			  	    		  }
			  	    	   }); 
				    	$(this).closest("tr").remove();
			       }
			    }else{
			    	
			    }
		    });
		    
		    
		    
		    $(document).on("click", ".edit", function(e){
        		$('#editModal').modal(); 
        	});
		    
		    
		    //filling in the inputs when modal opens 
        	$(document).on("shown.bs.modal", "#editModal", function(e){
        		if($(e.relatedTarget).attr("id") == "addButton"){
        			$("#options-div").hide();
        			$activeField = "new";
        		}else{
        		    $activeField = $(e.relatedTarget).closest("tr").children("td"); 
        		    $("#label-input").data("id", $activeField.eq(0).data("id"));
        		    $("#label-input").val($activeField.eq(0).html());
        		    $("#type-input").val($activeField.eq(1).html());
        		    if($activeField.eq(1).html() == "Combobox" || $activeField.eq(1).html() == "Radio button" || $activeField.eq(1).html() == "Checkbox"){
        			    $("#options-div").show();
        			    $("#options-input").val($activeField.eq(1).data("options"));
        		    }else{
        			    $("#options-input").val('');
        			    $("#options-div").hide();
        		    }
        		    if($activeField.eq(2).html() == "true"){
        			    $("#required-input").prop("checked", true);
        		    }else{
        			    $("#required-input").prop("checked", false);
        	 	    }
        		    if($activeField.eq(3).html() == "true"){
        		        $("#isActive-input").prop("checked", true);
        		    }else{
        			    $("#isActive-input").prop("checked", false);
        		    }
        		}
        	});
        	//changing type of a field
        	$("#type-input").on("change", function() {
        		  if(this.value == "Combobox" || this.value == "Radio button" || this.value == "Checkbox"){
        			  $("#options-div").show();
        		  }else{
	        	      $("#options-div").hide();
        		  }
        	});
        	//saving a field
        	$(document).on("click", '#save', function(){
        		var $row;
        		if($("#label-input").val() != ""){
        		    var object = {};
        		    object["label"]=$("#label-input").val();
        		    object["type"]=$("#type-input").val();
        		
        		    object["required"]=$("#required-input").is(":checked");
    		        object["isActive"]=$("#isActive-input").is(":checked");
    		        if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
    		    	    object["options"]=$("#options-input").val();
    		        }else{
    		        	object["options"]="";
    		        }
        		    if($activeField != "new"){
        		    	object["id"]=$("#label-input").data("id");
        			    //object["oldLabel"]=$activeField.eq(0).html();
        		        $activeField.eq(0).html($("#label-input").val());
        		        $activeField.eq(1).html($("#type-input").val());
        		        if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
        		    	    $activeField.eq(1).data("options", $("#options-input").val());
        		        }
        		        $activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
        		        $activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
        		    }else{
        		    	if(numberOfElements < size){
      	    		    	++numberOfElements;
      	    		    	++totalElements;
      	    		    	++size;
      	    		    	$row = $('<tr>'+
		        				      '<td class=\"field-label\">'+$("#label-input").val()+'</td>'+
		        				      '<td class=\"field-type\" data-options=\"'+$("#options-input").val()+'\">'+$("#type-input").val()+'</td>'+
		        				      '<td class=\"field-required\">'+$("#required-input").is(":checked")+'</td>'+
		        				      '<td class=\"field-isActive\">'+$("#isActive-input").is(":checked")+'</td>'+
		        				      '<td>'+
		        				      '<div align=\"right\">'+
		        				      "<button id=\"" + $("#label-input").val() + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>"+
		        				      "<button data-delete-id=\"" + $("#label-input").val() + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>"+
		        				      '</div>'+
		        			          '</td>'+
		        				      '</tr>');
		        			$row.attr("id", $("#label-input").val());
		        			$("#tbody").append($row);
	    		        }else{
	    		        	++totalElements;
	    		        	if(totalElements%(totalPages*size) == 0){
	    		        		++totalPages;
	    		        		paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
	    		        	}
	    		        }
        		    	
        		    }
        		}else{
        			$("#message").html("label shouldn't be empty");
        		}    
        		$.ajax({
  	    		  url:"save-field",
  	    		  type:"POST",
  	    		  data:JSON.stringify(object),
  	    		  datatype:"json",
  	    		  contentType:"application/json",
  	    		  success:function(data){
  	    			  if($activeField == "new" && $.isNumeric(data["message"])){
  	    				  $("#message").html("field has been created");
  	    				  if(numberOfElements < size){
  	    				      $row.children("td").eq(0).data("id", parseInt(data["message"]));
  	    				  }
  	    			  }else if(data["message"] == "field has been updated"){
  	    				  $("#message").html(data["message"]);
        			      object["oldLabel"]=$activeField.eq(0).html();
        		          $activeField.eq(0).html($("#label-input").val());
        		          $activeField.eq(1).html($("#type-input").val());
        		          if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
        		    	      $activeField.eq(1).data("options", $("#options-input").val());
        		          }
        		          $activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
        		          $activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
        		      } else {
        		    	  $("#message").html(data["message"]);
        		      }
  	    		  }
  	    	   });
        	})
        	//when modal closes we clear all its inputs
        	$(document).on("hidden.bs.modal", "#editModal", function(e){
        		$("#message").html("");
    			$("#label-input").data("id");
    		    $("#type-input").val("Single line text");
    		    $("#options-input").html("");
    			$("#options-div").hide();
    			$("#required-input").prop("checked", false);
    			$("#isActive-input").prop("checked", false);
        	});
        	
        	
        	$(document).on("submit", ".js-page", function(e) {
        		e.preventDefault();
        		var s = $(".js-size").first().val()==""?"5":$(".js-size").first().val();
        		//s = "&size=" + s;
        		var url = $(e.target).attr("action").toString();
        		$("#tbody").empty();
        		$(".paging#1").empty();
        		$(".paging#2").empty();
        		load(url, s);
        	});
        	$(document).on("keyup", ".js-size", function(e) {
        		if(e.keyCode == 13){
        		    e.preventDefault();
        		    var s = $(e.target).val();
        		    //s = "&size=" + s;
        		    var url = $(e.target).attr("id");
        		    $("#tbody").empty();
        		    $(".paging#1").empty();
        		    $(".paging#2").empty();
        		    load(url, s);
        	    }
        	});
			//pagination widgets
			
			/*$("div.paging#1").append("<label>" + (parseInt(data["size"])*parseInt(data["number"]) + 1).toString() + "-" + (parseInt(data["size"])*parseInt(data["number"]) + parseInt(data["numberOfElements"])).toString() +  " of " + data["totalElements"] + "</label>");
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
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "\"><input type=\"submit\" value=\"<<\" /></form></td>");
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "\"><input type=\"submit\" value=\"<\" /></form></td>");
			}
			while(i < pages && i < totalPages){
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
			    i++;
			}
			if(number != totalPages - 1 && totalPages != 0){
				$pageList.append("<td><form class=\"js-page page-link\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "\"><input type=\"submit\" value=\">\" /></form></td>");
				$pageList.append("<td><form class=\"js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "\"><input type=\"submit\" value=\">>\" /></form></td>");
			}
			$pageList.appendTo("#pages");
			$(".js-size").attr("id", pageAddress);*/
		}
	});
} 
$('document').ready(function(){
	            load("/get-fields-page?page=0", "&size=5");
	        	
	       });