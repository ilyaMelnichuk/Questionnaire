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
    <div class="container" style="text-align:center;">
           <div class="col-lg-4"></div>
           <div class="col-lg-4" style="displya:inline-block;">
               <div class="panel panel-default" style="margin-top:10px; min-width: 1200px;">
                   <div class="panel-heading" align="left" style="background-color:white;">
                        <h4>
                            Responses
                        </h4>
                   </div>
                   <div class="panel-body" style="margin-left:15px; margin-right:15px;">
                       <table class="table table-striped">
          
          <thead>
               <tr>
                   <th>field1</th>
                   <th>field2</th>
                   <th>field3</th>
                   <th>field4</th>
                   <th>field5</th>
               </tr>    
               <tr>
                   <th>field1</th>
                   <th>field2</th>
                   <th>field3</th>
                   <th>field4</th>
                   <th>field5</th>
               </tr>     
          </thead>
          <tbody class="table table-striped">
                  <tr>
                      <td>field_value1</td>
                      <td>field_value2</td>
                      <td>field_value3</td>
                      <td>field_value4</td>
                      <td>field_value5</td>
                  </tr>
          </tbody>
      </table>
                   </div>
               </div>
           </div>
           <div class="col-lg-4">
           </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        
    </script>
</body>
</html>