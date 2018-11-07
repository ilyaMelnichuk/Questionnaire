<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
   <head>
   
       <meta charset="utf-8" />
       <meta name="viewport" content="width=device-width, initial-scale=1" />
   </head>
   <body>
    
    <form method="POST" action="${contextPath}/login">
      <table>
         <tr>
            <td>User:</td>
            <td><input type="text" name="email" value=""></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type="password" name="password" /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
    
       
     
 </body>
</html>