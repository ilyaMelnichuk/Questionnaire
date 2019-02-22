<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${request.getContextPath()}" />
<c:set var="url"
	value="${requestScope['javax.servlet.forward.request_uri']}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>LOGOTYPE</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/css/style.css">
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body class="logotype-body">

	<%if (request.getUserPrincipal() == null) {%>
	<%@ include file="anonymousNavbar.jsp"%>
	<%
	} else if (request.getUserPrincipal().getName().equals("admin")){
	%>
	<%@ include file="adminNavbar.jsp"%>
	<%}else{%>
	<%@ include file="userNavbar.jsp"%>
	<%}%>

	<c:choose>
		<c:when test="${url=='/access-denied'}">
		    <%@ include file="accessDenied.jsp"%>
		</c:when>
        <c:when test="${url=='/login'}">
			<%@ include file="login.jsp"%>
		</c:when>
		<c:when test="${url=='/login-error'}">
			<%@ include file="login.jsp"%>
		</c:when>
		<c:when test="${url=='/signup-success'}">
			<%@ include file="login.jsp"%>
		</c:when>
		<c:when test="${url=='/forgot-password'}">
            <%@ include file="forgotPassword.jsp"%>
		</c:when>
		<c:when test="${url=='/reset-password'}">
			<%@ include file="resetPassword.jsp"%>
		</c:when>
		<c:when test="${url=='/signup'}">
			<%@ include file="signup.jsp"%>
		</c:when>
		<c:when test="${url=='/edit-profile'}">
			<%@ include file="editProfile.jsp"%>
		</c:when>
		<c:when test="${url=='/change-password'}">
			<%@ include file="changePassword.jsp"%>
		</c:when>
		<c:when test="${url=='/fields'}">
			<%@ include file="fields.jsp"%>
		</c:when>
		<c:when test="${url=='/responses'}">
			<%@ include file="responses.jsp"%>
		</c:when>
		<c:when test="${url=='/my-responses'}">
			<%@ include file="myResponses.jsp"%>
		</c:when>
		<c:when test="${url=='/success'}">
			<%@ include file="success.jsp"%>
		</c:when>
		<c:otherwise>
		    <%@ include file="index.jsp"%>
		</c:otherwise>
	</c:choose>
	
</body>
</html>