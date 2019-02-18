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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<%
	if (request.getUserPrincipal().getName().equals("admin")) {
%>
<script src="js/responses.js"></script>
<%
	} else {
%>
<script src="js/my-responses.js"></script>
<%
	}
%>

</head>
<body style="background-color: #f1f1f1;">

	<%if (request.getUserPrincipal().getName().equals("admin")) {%>
	<%@ include file="adminNavbar.jsp"%>
	<%} else {%>
	<%@ include file="userNavbar.jsp"%>
	<%}%>
	
	<div class="container" align="center">
		<div class="jumbotron"
			style="background-color: white; padding-top: 5px; padding-bottom: 0px; padding-left: 0px; padding-right: 0px;">
			<div style="border-bottom: 2px solid #dddddd; padding-left: 10px"
				align="left">
				<h4>Responses</h4>
			</div>
			<div id="content" style="padding-left: 20px; padding-right: 20px;">
				<table id="table" class="table table-striped">
					<thead>
						<tr id="head">
							<!-- table header -->
						</tr>
					</thead>
					<tbody id="tbody" class="table table-striped">

					</tbody>
					<tfoot id="tfooter" align="center" style="min-height: 10px;">
					</tfoot>
				</table>
			</div>
			<div
				style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
				<div style="float: left; width: 33%;" id="div"></div>
				<div style="float: left; width: 33%;" id="1"></div>
				<div style="float: left; width: 33%;" id="div2"></div>
				<div style="float: left; width: 33%;" class="paging" id="2"></div>
				<div style="float: left; width: 33%;" class="paging" id="3">
					<label for="size">size of page</label> <input type="number"
						class="js-size" name="size" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>