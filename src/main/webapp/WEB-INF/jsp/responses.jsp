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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script type="text/javascript">
    $('document').ready(function(){
    	$.ajax({
    		url:"get-default-page",
    		type:"GET",
    		datatype:"json",
    		success: function(data){
    			var fields = data["content"];
    			var labels = [];
    			for(var key in fields){
    				$("#head").append( $("<th>" + fields[key].label + "</th>"));
    				labels.push(fields[key].label);
    			}
    			$.ajax({
    	    		url:"get-all-responses",
    	    		type:"GET",
    	    		datatype:"json",
    	    		success: function(responses){
    		            var index = -1;
    		            //loop through the list of responses
    	    			for(var key in responses){
    	    				//create new row 
    	    				if(index != responses[key].id){
    	    					$row = $("<tr id=\"" + responses[key].id + "\"></tr>");
    	    		            $.each(labels, function(k, s){
    	    		            	$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
    	    		            });
    	    		            $row.appendTo("#tbody");
    	    				    index = responses[key].id;
    	    			    }
    	    				$row = $("tr[id=\"" + responses[key].id + "\"]");
    		                $row.children("td[name=\"" + responses[key].label + "\"]").eq(0).html(responses[key].value);
    	    		    }	
    		            
    	    			var socket = new SockJS('/responses');
    	        	    var stompClient = Stomp.over(socket);
    	        	    stompClient.connect({}, function(frame){
    	        	    });
    	        	    setTimeout(function(){
    	        	    stompClient.subscribe("/topic/responses", function (message){
	    	    			//alert("reps");
	    	    			var response = JSON.parse(message.body);
	    	    			$row = $("<tr></tr>");
	    	    			$.each(labels, function(k, s){
	    		            	$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
	    		            });
	    	    			for(var key in response){
	    		                $row.children("td[name=\"" + response[key].label + "\"]").eq(0).html(response[key].value);
	    	    			}
	    	    			$row.appendTo("#tbody");
	    	    		});
    	        	    }, 500);
    	    			/* var socket = new SockJs("/responses");
    	    	    	var stompClient = Stomp.over(socket);
    	    	    	stompClient.connect({}, function(frame){
    	    	    		stompClient.subscribe("/topic/responses", function (response){
    	    	    			//alert("reps");
    	    	    			$row = $("<tr></tr>");
    	    	    			$.each(labels, function(k, s){
    	    		            	$("<td name=\"" + s + "\">N/A</td>").appendTo($row);
    	    		            });
    	    	    			for(var key in responses){
    	    		                $row.children("td[name=\"" + responses[key].label + "\"]").eq(0).html(responses[key].value);
    	    	    			}
    	    	    		});
    	    	    	}) */
    	    		}
    	    	});
    		}
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
    <div style="margin-right:150px;">
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
    </div>
    </nav>
    <div class="container" align="center">
    <div class="jumbotron" style="background-color:white; padding-top:5px; padding-bottom:0px; padding-left:0px; padding-right:0px;">
        <div style="border-bottom: 2px solid #dddddd; padding-left:10px" align="left">
            <h4>Responses</h4>
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