<div class="container" align="center">
	<div class="logotype-jumbotron2 jumbotron">
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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="${contextPath}/js/responses.js"></script>