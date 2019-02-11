<!DOCTYPE html>
<html>
<head>
<title>Fields</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="css/style.css" />

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="js/fields.js"></script>
</head>
<body style="background-color: #f1f1f1;">

	<nav class="navbar navbar-collapsible"
		style="background-color: white; border-bottom-color: #dddddd; border-radius: 0px;">
		<div class="container-fluid">
			<div class="navbar-header" style="margin-left: 150px">
				<a class="navbar-brand"><span style="color: black;"><strong>LOGO</strong></span><span
					style="color: blue;">TYPE</span></a>
			</div>
			<div style="margin-right: 150px">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/fields">Fields</a></li>
					<li><a href="/responses">Responses</a></li>
					<li><a href="/logout">Log Out</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container" align="center">
		<div class="jumbotron"
			style="background-color: white; padding-top: 10px; padding-bottom: 0px; padding-left: 0px; padding-right: 0px;">
			<div
				style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
				<h4 style="float: left;">Fields</h4>
				<input type="button" data-button="add" data-edit-id="add"
					data-toggle="modal" data-target="#editModal" id="addButton"
					class="btn-primary form-control edit" value="+ ADD FIELD"
					style="max-width: 120px; text-align: center; float: right;">
			</div>
			<div id="content" style="padding-left: 20px; padding-right: 20px;">
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
				</table>
			</div>
			<div class="paging" id="1">
			</div>
			<div class="paging" id="2">
			    <ul class="pagination"><li class="page-item disabled"><a class="page-link" href="#" tabindex="-1">&lt;&lt;</a> </li><li class="page-item" id="raquo"><a class="page-link"  href="#">&gt;&gt;</a></li></ul>
			</div>
			<div class="paging" id="3">
			    <input type="radio" name="size" value="value" required/> <span>" 5 "</span>
			    <input type="radio" name="size" value="value" required/> <span>" 10 "</span>
			    <input type="radio" name="size" value="value" required/> <span>" 15 "</span>
			</div>
		</div>
	</div>

	<div class="modal" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit Field</h4>
					<span id="message"></span>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label style="justify-content: center"><span
								style="color: grey">Label</span>*</label> <input type="text"
								class="form-control" id="label-input" data-id=""
								style="justify-content: center">
						</div>
						<div class="form-group">
							<label><span style="color: grey">Type</span>*</label> <select
								class="form-control" id="type-input">
								<option value="Single line text">Single line text</option>
								<option value="Multiline text">Multiline text</option>
								<option value="Radio button">Radio button</option>
								<option value="Checkbox">Checkbox</option>
								<option value="Combobox">Combobox</option>
								<option value="Date">Date</option>
							</select>
						</div>
						<div class="form-group" id="options-div">
							<label><span style="color: grey;">Options</span>*</label>
							<textarea rows="5" cols="25" class="form-control"
								id="options-input"></textarea>
						</div>
						<div class="form-group">
							<label> <input type="checkbox" id="required-input" />
								Required <input type="checkbox" id="isActive-input" /> Is Active
							</label>
						</div>

					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						CANCEL</button>
					<button type="button" class="btn btn-primary" id="save">
						SAVE</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>