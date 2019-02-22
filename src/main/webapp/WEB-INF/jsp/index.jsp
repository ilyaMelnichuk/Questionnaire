<div class="container" align="center">
	<div class="jumbotron p-2 logotype-jumbotron1">
		<form id="form" action="send-response" method="post">
			<div align="left" id="last" class="form-group"
				style="min-height: 35px;">
				<input class="btn-primary form-control" id="button"
					style="max-width: 100px; float: left;" type="submit" value="SUBMIT">
				<input class="btn-primary form-control" id="reset"
					style="max-width: 100px; float: right;" type="button" value="RESET">
			</div>
		</form>
	</div>
</div>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
	<script src="${contextPath}/js/index.js"></script>