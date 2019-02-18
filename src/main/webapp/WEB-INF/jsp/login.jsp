<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>LOGOTYPE</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body style="background-color: #f1f1f1;">
    <%@ include file = "/WEB-INF/jsp/anonymousNavbar.jsp"%>
	<div class="container" align="center">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron"
				style="margin-top: 150px; min-width: 450px; background-color: white;">
				<form method="POST" action="${contextPath}/login">
					<div class="form-group" align="center">
						<h2>
							<strong>LOGO</strong><span style="color: blue;">TYPE</span>
						</h2>
					</div>
					<div class="form-group" align="center">
						<h4>Log In</h4>
					</div>
					<div class="form-group" align="center">
						<h5>${message}</h5>
					</div>
					<div class="form-group">
						<input type="text" name="email" placeholder="Email"
							class="form-control">
					</div>
					<div class="form-group">
						<input type="password" name="password" placeholder="Password"
							class="form-control" />
					</div>
					<div class="from-group" align="center">
						<label> <input type="checkbox" name="remember_me">
							Remember me <a href="/forgot-password">Forgot your password?</a>
						</label>
					</div>
					<br>
					<button class="btn-primary form-control" type="submit">LOG
						IN</button>
					<br>
					<div class="form-group" align="center">
						<label> Don't have account? <a
							href="<c:url value="/signup" />">Sign up</a>
						</label>
					</div>
				</form>
			</div>
		</div>
		<div class="col-lg-4"></div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</body>
</html>