
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<title>Fields</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="/bootstrap-3.3.7/css/bootstrap.min.css" />
</head>
<body>


<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand mr-0 mr-md-2" href="/" aria-label="LOGOTYPE"></a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Fields </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Responses</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">John Doe</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
      <div class="container">
      <table class="table table-striped">
          <thead>
              <tr>
                   <th>Fields</th>
                   <th></th>
                   <th></th>
                   <th></th>
                   <th><input type="button" value="+ ADD FIELD"></th>
              </tr>     
          </thead>
          <thead>
               <tr>
                   <th>label</th>
                   <th>type</th>
                   <th>required</th>
                   <th>isActive</th>
                   <th></th>
               </tr>         
          </thead>
          <tbody class="table table-striped">
              <c:forEach var = "field" items="${fields}">
                  <tr>
                      <td>${field.label}</td>
                      <td>${field.type}</td>
                      <td>${field.required}</td>
                      <td>${field.isActive}</td>
                      <td>
                          <a href="#" class="text-muted">
                              <span class="glyphicon glyphicon-edit"></span>
                          </a>
                          <a href="#" class="text-muted">
                              <span class="glyphicon glyphicon-trash"></span>
                          </a>                       	        
                      </td>
                  </tr>
              </c:forEach>
              <tr></tr>
          </tbody>
      </table>
      </div>
  </div>

<script src="/jquery-3.3.1.min.js/js/bootstrap.min.js"></script>
<script src="/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</body>
</html>