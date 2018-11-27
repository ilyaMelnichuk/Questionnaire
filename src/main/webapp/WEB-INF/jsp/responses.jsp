<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>LOGOTYPE</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script type="text/javascript">
    $('document').ready(function(){
    	$.ajax({
    		url:"get-default-page",
    		type:"GET",
    		datatype:"json",
    		success: function(data){
    			var fields = data["content"];
    			var count = 0;
    			for(var key in fields){
    				$th = $("<th>" + fields[key].label + "</th>");
    				$th.attr("id", key + "");
    				$("#head").append($th);
    				count++;
    			}
    			$("head").attr("name", count+"");
    		}
    	});
    	
    	$.ajax({
    		url:"get-all-responses",
    		type:"GET",
    		datatype:"json",
    		success: function(responses){
    			for(var key in responses){
    		            $row = $("<tr>"+"</tr>");
    		            $td;
    		            $.each($("#head").children("<th>"), function(){
    		            	$td = $("<td></td>");
    		            	$td.attr("name", $("this").html());
    		            	$row.append($td);
    		        	});
    		    }	
    		}
    	});
   });
  </script>
    
    
</head>
<body style="background-color:#f1f1f1;">
    <nav class="navbar navbar-collapsible" style="background-color:white; border-bottom-color:#dddddd; border-radius:0px;">
    <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="/"><span style="color:black;"><strong>LOGO</strong></span><span style="color:blue;">TYPE</span></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="/fields">Fields</a></li>
      <li class="active"><a href="/responses">Responses</a></li>
      <li class="dropdown">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">${name}
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="/edit-profile">Edit Profile</a></li>
          <li><a href="/change-password">Change Password</a></li>
          <li><a href="/logout">Log Out</a></li>
        </ul>
      </li>
    </ul>
    </div>
    </nav>
    <div class="container" align="center">
    <div class="jumbotron" style="background-color:white; padding-top:20px; padding-bottom:0px; padding-left:0px; padding-right:0px;">
        <div style="border-bottom: 2px solid #dddddd; min-height:45px; padding-left:10px; padding-right:10px;">
            <h4 style="float:left;">Fields</h4>
            <input type="button" data-button="add" data-edit-id="add" data-toggle="modal" data-target="#editModal" id="addButton" class="btn-primary form-control edit" value="+ ADD FIELD" style="max-width:120px; text-align:center; float:right;">
        </div>
        <div id="content" style="padding-left:20px;padding-right:20px;">
            <table id="table" class="table table-striped">
               <thead>
                   <tr id="head">
                       <!-- table header -->
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
</body>
</html>