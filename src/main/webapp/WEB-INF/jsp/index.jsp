<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>LOGOTYPE</title>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script>
        $(document).ready(function(){
        	$.ajax({
        		url: "get-fields-to-draw",
        		type: "GET",
        		dataType: "json",
        		success: function(data){/* 
        			
        			<div class="form-group" align="left">
                    <label><span style="color:grey;">First Name</span></label> <br>
                    <input type="text" id = "firstName" class="form-control">
                    <span id='current-message' style="color:red;"></span>
                    </div> */
        			
        			
        			//alert(JSON.stringify(data));
        			$.each(data, function(key, value){
        				    $group = $("<div name=\"fsd\" class=\"form-group\" align=\"left\">"+
            					  	"<label>"+
            						"<span style=\"color:grey;\">"+
            						value["label"]+
            						"</span>"+ 
            						(value["required"]?"*":"")+
            						"</label> <br>"+
            						"</div>"
            						);
        				    switch(value["type"]){
        				    case "Single line text":
        				    	$group.attr("id", "text");
        				    	$input = $("<input type=\"text\" class=\"form-control\" id=\""+ value["label"] +"\"/>");
        				    	break;
        				    case "Multiline text":
        				    	$group.attr("id", "textarea");
        				    	$input = $("<textarea rows=\"5\" cols=\"25\" class=\"form-control\" id=\""+ value["label"] +"\"></textarea>");
        				    	break;
        				    case "Radio button":
        				    	$group.attr("id", "radio");
        				    	var options = value["options"].split("|");
        				        $.each(options, function(i, opt){
        				    	    $input = $("<input type=\"radio\" name=\"" + value["label"] + " value=\""+opt+"\"\">");
        				    	    $(opt).after($input);
        				    	    $group.append($input);
        				   	    });
        				    	break;
        				    case "Checkbox":
        				    	$group.attr("id", "checkbox");
        				    	$input = $("<input type=\"checkbox\" id=\""+ value["label"] +"\">");
        				    	break;
        				    case "Combobox":
        				    	$group.attr("id", "select");
        				    	$input = $("<select class=\"form-control\" id=\""+ value["label"] +"\">"+"</select>");
        				    	$option;
        				    	var options = value["options"].split("|");
        				    	$.each(options, function(i, opt){
        				    		$option = $("<option value=\""+opt+"\">" + opt + "</option>");
        				    		$input.html($option);
        				    	});
        				    	break;
        				    case "Date":
        				    	$group.attr("id", "date");
        				    	$input = $("<input type=\"date\" class=\"form-control\" id=\""+ value["label"] +"\">");
        				    default:	
        				    }  
        				$group.append($input);
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
        		var response;
        		$.each($(this).children("div.form-group"), function(){
        			response = {};
        			response["id"] = null;
        			response["value"] = "";
        			switch($(this).attr("id")){
        			    case "text":
        			    	response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
        			    	response["value"] = $(this).children("input").eq(0).val();
        			    	break;
        			    case "textarea":
        			    	response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
        			    	response["value"] = $(this).children("textarea").eq(0).text();
        			    	break;
        			    case "radio":
        			    	response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
        			    	response["value"]= $(this).children("input[name=" + $(this).children("label").eq(0).children("span").eq(0).html() + "]:checked").val(); 
        			    	break;
        			    case "checkbox":
        			    	response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
        			    	response["value"] = $(this).children("input").eq(0).is(":checked");
        			    	break;
        			    case "select":
        			    	response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
        			    	response["value"] = $(this).children("select").eq(0).val();
        			    	break;
        			    case "date":
        			        response["label"] = $(this).children("label").eq(0).children("span").eq(0).html();
    			    	    response["value"] = $(this).children("input").eq(0).val();
        			}
        			object.list.push(response);
        		});
        	
        		alert(JSON.stringify(object));
        		$.ajax({
	  	    		  url:"send-response",
	  	    		  type:"POST",
	  	    		  data:JSON.stringify(object.list),
	  	    		  datatype:"json",
	  	    		  contentType:"application/json",
	  	    		  success:function(data){
	  	    	          alert(data["message"]);
	  	    		  }
	  	    	 });
        	});
        });        
        
  </script>
</head>

<body style="background-color:#f1f1f1;">
    <nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header" style="margin-left:150px;">
      <a class="navbar-brand" href="/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <div style="margin-right:150px">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="/login">Log In</a></li>
    </ul>
    </div>
    </div>
    </nav>
    
    <div class="container" align="center">
           <div class = "jumbotron p-2" style="max-width:400px; background-color:white; padding-bottom:5px; padding-left:30px; padding-top:15px">
               <form id="form" action="send-response" method="post">
                   <div align="left" id="last">
                        <input class="btn-primary form-control" id="button" style="max-width:100px;" type="submit" value="SUBMIT">
                   </div>
               </form>
           </div>
    </div>
</body>
</html>
