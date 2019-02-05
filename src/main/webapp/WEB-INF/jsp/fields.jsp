<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<title>Fields</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript">
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
		    }
	});
	
	var $activeField;
	
    $(document).on("click", ".delete", function(){
	    if(confirm("Are you sure you want to delete field \"" + $(this).closest("tr").children("td").eq(0).html() + "\"?")){
	    	var label = {};
	    	label["message"] = $(this).closest("tr").children("td").eq(0).html();
	    	var url = $("#save").data("delete");
	    	$.ajax({
  	    		  url:url,
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
	        if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
	    	    object["options"]=$("#options-input").val();
	        }else{
	        	object["options"]="";
	        }
		    if($activeField != "new"){
			    object["oldLabel"]=$activeField.eq(0).html();
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
			    object["oldLabel"]="";
		    }
		}else{
			$("#message").html("label shouldn't be empty");
		}    
		var url = $("#save").data("save");
		$.ajax({
  		  url:url,
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
		          if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
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
	    $("#options-input").html("");
		$("#options-div").hide();
		$("#required-input").prop("checked", false);
		$("#isActive-input").prop("checked", false);
	});
});
</script>
</head>
<body style="background-color:#f1f1f1;">

<nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header" style="margin-left:150px">
      <a class="navbar-brand" href="${contextPath}/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="${contextPath}/fields">Fields</a></li>
      <li><a href="${contextPath}/responses">Responses</a></li>
      <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">${name}
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li ><a href="${contextPath}/edit-profile">Edit Profile</a></li>
          <li><a href="${contextPath}/change-password">Change Password</a></li>
          <li><a href="${contextPath}/logout">Log Out</a></li>
        </ul>
      </li>
    </ul>
    </div>
    </div>
</nav>

<div class="container" align="center">
    <div class="jumbotron" style="background-color:white; padding-top:10px; padding-bottom:0px; padding-left:0px; padding-right:0px;">
        <div style="border-bottom: 2px solid #dddddd; min-height:45px; padding-left:10px; padding-right:10px;">
            <h4 style="float:left;">Fields</h4>
            <input type="button" data-button="add" data-edit-id="add" data-toggle="modal" data-target="#editModal" id="addButton" class="btn-primary form-control edit" value="+ ADD FIELD" style="max-width:120px; text-align:center; float:right;">
        </div>
        <div id="content" style="padding-left:20px;padding-right:20px;">
            <table id="table" class="table table-striped">
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
               <tfoot id="tfooter" align="center">
               </tfoot>
           </table>
       </div>
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
                <form role="form">
                  <div class="form-group">
                      <label style="justify-content:center"><span style="color:grey">Label</span>*</label>
                      <input type="text" class="form-control" id="label-input" style="justify-content:center">
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
                <button type="button" class="btn btn-primary" id="save" data-save="${contextPath}/save-field" data-delete="${contextPath}/delete-field">
                    SAVE
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>