<div id="container" class="container" align="center">
	<div class="jumbotron logotype-jumbotron2">
		<div
			style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
			<h4 style="float: left;">Fields</h4>
			<input type="button" data-button="add" data-edit-id="add"
				data-toggle="modal" data-target="#editModal" id="addButton"
				class="btn-primary form-control edit" value="+ ADD FIELD"
				style="max-width: 120px; text-align: center; float: right;">
		</div>
		<div id="content" >
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
		<div
			style="border-bottom: 2px solid #dddddd; min-height: 45px; min-width: 940px;">
			<div style="float: left; width: 33%;" class="paging" id="1"></div>
			<div style="float: left; width: 33%;" class="paging" id="2"></div>
			<div style="float: left; width: 33%;" class="paging" id="3">
				<label for="size">size of page</label> <input type="number"
					class="js-size" name="size" />
			</div>
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
				<button type="button" class="btn btn-primary" id="save" class="close" data-dismiss="modal">
					SAVE</button>
			</div>
		</div>
	</div>
</div>
<script src="${contextPath}/js/fields.js"></script>