<div id="container" class="container" align="center">
	<div class="jumbotron logotype-jumbotron2">
		<div
			style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
			<h4 style="float: left;">Templates</h4>
			<input type="button" data-button="add" data-edit-id="add"
				data-toggle="modal" data-target="#editModal" id="addButton"
				class="btn-primary form-control edit" value="+ ADD TEMPLATE"
				style="max-width: 140px; text-align: center; float: right;">
		</div>
		<div id="content">
			<table id="table" class="table table-striped">
				<thead>
					<tr>
						<th>Title</th>
						<th>fields</th>
						<th>responses</th>
						<th>Is Active</th>
					</tr>
				</thead>
				<tbody id="templates-body" class="table table-striped">
				</tbody>
			</table>
		</div>
		<div
			style="border-bottom: 2px solid #dddddd; min-height: 45px; min-width: 940px;">
			<div style="float: left; width: 33%;" class="paging templates-paging"
				id="templates-1"></div>
			<div style="float: left; width: 33%;" class="paging templates-paging"
				id="templates-2"></div>
			<div style="float: left; width: 33%;" class="paging templates-paging"
				id="templates-3">
				<label for="size">size of page</label> <input type="number"
					class="templates-js-size" name="size" />
			</div>
		</div>
	</div>
</div>




<div class="modal" id="editModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true"
	style="min-width: 900px;">
	<div class="modal-dialog" style="min-width: 900px !important;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
				</button>
			</div>




			<div class="modal-body">
					<div class="form-group">
						<label style="justify-content: center"><span
							style="color: grey">Name</span>*</label> <input id="name-input"
							type="text" style="min-width: 60px;" required />
					</div>
					<div class="form-group">
						<label style="justify-content: center"><span
							style="color: grey">Required</span>*</label> <input id="required-input"
							type="checkbox" />
					</div>
					<div
						style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
						<h4 style="float: left;">Fields</h4>
					</div>
					<div id="fields">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Label</th>
									<th>Type</th>
									<th>Required</th>
									<th>Is Active</th>
									<th>Options</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="fields-body" class="table table-striped">
							</tbody>
						</table>
					</div>
					<div style="border-bottom: 2px solid #dddddd; min-height: 45px;">
						<div style="float: left; width: 33%;" class="paging fields-paging"
							id="fields-1"></div>
						<div style="float: left; width: 33%;" class="paging fields-paging"
							id="fields-2"></div>
						<div style="float: left; width: 33%;" class="paging fields-paging"
							id="fields-3">
							<label for="size">size of page</label> <input type="number"
								class="fields-js-size" name="size" />
						</div>
					</div>


					<div
						style="border-bottom: 2px solid #dddddd; min-height: 45px; padding-left: 10px; padding-right: 10px;">
						<h4 style="float: left;">Chosen Fields</h4>
					</div>
					<div id="chosen">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>Label</th>
									<th>Type</th>
									<th>Required</th>
									<th>Is Active</th>
									<th>Options</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="chosen-body" class="table table-striped">
							</tbody>
						</table>
					</div>
					<div style="border-bottom: 2px solid #dddddd; min-height: 45px;">
						<div style="float: left; width: 33%;" class="paging chosen-paging"
							id="chosen-1"></div>
						<div style="float: left; width: 33%;" class="paging chosen-paging"
							id="chosen-2"></div>
						<div style="float: left; width: 33%;" class="paging chosen-paging"
							id="chosen-3">
							<label for="size">size of page</label> <input type="number"
								class="chosen-js-size" name="size" />
						</div>
					</div>
			</div>



			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">
					CANCEL</button>
				<button type="button" class="btn btn-primary" id="save"
					class="close" data-dismiss="modal">SAVE</button>
			</div>
		</div>
	</div>
</div>



<script src="${contextPath}/js/templates.js"></script>