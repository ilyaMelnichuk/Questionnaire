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
	        			}
	        		    }
	        	});
	        	
	        	var $activeField;
	        	
			    $(document).on("click", ".delete", function(){
				    if(confirm("Are you sure you want to delete field \"" + $(this).closest("tr").children("td").eq(0).html() + "\"?")){
				    	var label = {};
				    	//label["message"] = $(this).closest("tr").children("td").eq(0).html();
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
	        		    $("#label-input").val($activeField.eq(0).data("id"));
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
	  	    				  $row.children("td").eq(0).data("id", parseInt(data["message"]))
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
        			//$("#label-input").val("");
        			$("#label-input").data("id");
        		    $("#type-input").val("Single line text");
        		    $("#options-input").html("");
        			$("#options-div").hide();
        			$("#required-input").prop("checked", false);
        			$("#isActive-input").prop("checked", false);
	        	});
	       });
	       /*function saveField(field){
	    	   $.ajax({
	    		  url:"save-field",
	    		  type:"POST",
	    		  datatype:"json",
	    		  contentType:"application/json",
	    		  success:function(data){
	    			  $("#message").html(data["message"]);
	    		  }
	    	   });
	       }*/ 