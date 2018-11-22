<!DOCTYPE html>
<html>
<head>
<title>Fields</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<script>
	        $('document').ready(function(){
	        	$.ajax({
	        		url:"get-default-page",
	        		type:"GET",
	        		datatype:"json",
	        		success: function(data){		
	        		    //alert(JSON.stringify(data));
	        			var fields = data["content"];
	        			
	        			$("#tbody").append("<tr></tr>")
	        			for(var key in fields){ 
	        				$delete = "<button data-delete-id=\"" + fields[key].label + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>";
	        				$edit = "<button id=\"" + fields[key].label + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>";
	        				$row = $('<tr>'+
	        				      '<td class=\"field-label\">'+fields[key].label+'</td>'+
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
	        			$("#paging").append("<label>" + data.number*data["pageable"].pageSize + "-" + (data.number*data["pageable"].pageSize + data.numberOfElements) + " of "+data.totalElements+"</label>");
	        			
	    			    
	        		}
	        	    
	        	});
	        	
	        	var $activeField;
	        	
			    $(document).on("click", ".delete", function(){
				    if(confirm("Are you sure you want to delete field \"" + $(this).closest("tr").children("td").eq(0).html() + "\"?")){
				    	var label = {};
				    	label["message"] = $(this).closest("tr").children("td").eq(0).html();
				    	$.ajax({
			  	    		  url:"delete-field",
			  	    		  type:"POST",
			  	    		  data:JSON.stringify(label),
			  	    		  datatype:"json",
			  	    		  contentType:"application/json",
			  	    		  success:function(data){
			  	    			  alert(data["message"]);
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
	        		    $("#label-input").val($activeField.eq(0).html());
	        		    $("#type-input").val($activeField.eq(1).html());
	        		    if($activeField.eq(1).html() == "Combobox" || $activeField.eq(1).html() == "Radio button"){
	        		    	//alert($activeField.eq(1).data("options"));
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
	        		  if(this.value == "Combobox" || this.value == "Radio button"){
	        			  $("#options-div").show();
	        		  }else{
	        			  //$("#options-input").val('');
		        	      $("#options-div").hide();
	        		  }
	        	});
	        	//saving field
	        	$(document).on("click", '#save', function(){
	        		if($("#label-input").val() != ""){
	        		    var object = {};
	        		    object["label"]=$("#label-input").val();
	        		    object["type"]=$("#type-input").val();
	        		
	        		    object["required"]=$("#required-input").is(":checked");
        		        object["isActive"]=$("#isActive-input").is(":checked");
        		        if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button"){
        		    	    object["options"]=$("#options-input").val();
        		        }else{
        		        	object["options"]="";
        		        }
	        		    if($activeField != "new"){
	        			    object["oldLabel"]=$activeField.eq(0).html();
	        		        $activeField.eq(0).html($("#label-input").val());
	        		        $activeField.eq(1).html($("#type-input").val());
	        		        if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button"){
	        		    	    $activeField.eq(1).data("options", $("#options-input").val());
	        		        }
	        		        $activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
	        		        $activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
	        		    }else{
	        			    object["oldLabel"]="";
	        		    }
	        		    //alert(JSON.stringify(object));
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
	  	    			  $("#message").html(data["message"]);
	  	    			  if($activeField != "new" && data["message"] == "field has been updated"){
	        			      object["oldLabel"]=$activeField.eq(0).html();
	        		          $activeField.eq(0).html($("#label-input").val());
	        		          $activeField.eq(1).html($("#type-input").val());
	        		          if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button"){
	        		    	      $activeField.eq(1).data("options", $("#options-input").val());
	        		          }
	        		          $activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
	        		          $activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
	        		      }
	  	    		  }
	  	    	   });
	        	})
	        	
	        	//when modal closes we clear all its inputs
	        	$(document).on("hidden.bs.modal", "#editModal", function(e){
	        		$("#message").html("");
        			$("#label-input").val("");
        		    $("#type-input").val("Single line text");
        			$("#options-div").hide();
        			$("#required-input").prop("checked", false);
        			$("#isActive-input").prop("checked", false);
	        	});
	       });
	       function saveField(field){
	    	   $.ajax({
	    		  url:"save-field",
	    		  type:"POST",
	    		  datatype:"json",
	    		  contentType:"application/json",
	    		  success:function(data){
	    			  $("#message").html(data["message"]);
	    		  }
	    	   });
	       } 
	</script>
</head>
<body style="bakcground-color:#f1f1f1">

<nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header" style="margin-left:150px">
      <a class="navbar-brand" href="/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px">
    <ul class="nav navbar-nav navbar-right">
      <li class="active"><a href="/fields">Fields</a></li>
      <li><a href="/responses">Responses</a></li>
      <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">${name}
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li ><a href="/edit-profile">Edit Profile</a></li>
          <li><a href="/change-password">Change Password</a></li>
          <li><a href="/logout">Log Out</a></li>
        </ul>
      </li>
    </ul>
    </div>
    </div>
</nav>

<div class="container" align="center">
        <table id="table" class="table table-striped">
           <thead>
               <tr>
                   <th><h4>Fields</h4></th>
                   <th></th>
                   <th></th>
                   <th></th>
                   <th><div align="right"><input type="button" data-button="add" data-edit-id="add" data-toggle="modal" data-target="#editModal" id="addButton" class="btn-primary form-control edit" value="+ ADD FIELD" style="max-width:120px; text-align:center"></div></th>
               </tr>         
           </thead>
           <thead>
               <tr>
                   <th>Label</th>
                   <th>Type</th>
                   <th>Required</th>
                   <th>Is Active</th>
                   <th></th>
               </tr>         
           </thead>
           <tbody id="tbody" class="table table-striped">
           </tbody>
       </table>
       <div id="paging">
           
       </div>
</div>

<div class="modal" id="editModal" tabindex="-1" role="dialog" 
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                       <span aria-hidden="true">&times;</span>
                       <span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    Edit Field
                </h4>
                <span id="message"></span>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                  <div class="form-group">
                      <label><span style="color:grey">Label</span>*</label>
                      <input type="text" class="form-control" id="label-input">
                  </div>
                  <div class="form-group">
                    <label><span style="color:grey">Type</span>*</label>
                    <select class="form-control" id="type-input">
                        <option value="Single line text">Single line text</option>
                        <option value="Multiline text">Multiline text</option>
                        <option value="Radio button">Radio button</option>
                        <option value="Checkbox">Checkbox</option>
                        <option value="Combobox">Combobox</option>
                        <option value="Date">Date</option>
                    </select>
                  </div>
                  <div class="form-group" id="options-div">
                      <label><span style="color:grey;">Options</span>*</label>
                      <textarea rows="5" cols="25" class="form-control" id="options-input"></textarea>
                  </div>
                  <div class="form-group">
                    <label>
                        <input type="checkbox" id="required-input"/> Required
                        <input type="checkbox" id="isActive-input"/> Is Active
                    </label>
                  </div>
                  
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    CANCEL
                </button>
                <button type="button" class="btn btn-primary" id="save">
                    SAVE
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>