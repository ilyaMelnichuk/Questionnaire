
<div class="container" align="center">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
		<div class="panel panel-default"
			style="margin-top: 10px; min-width: 400px;">
			<div class="panel-heading" align="left"
				style="background-color: white;">
				<h4>Forgot Your Password?</h4>
				<span id='message'></span>
			</div>
			<div class="panel-body logotype-panel">
				<form id="form" action="/forgot-password" method="post">
					<div class="form-group" align="left">
						<label><span style="color: grey;">Email</span></label> <br> <input
							type="email" id="email" class="form-control">
					</div>
					<div class="form-group" align="left">
						<input class="btn-primary form-control" id="button"
							style="max-width: 200px;" type="submit" value="SEND RESET LINK">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="${contextPath}/js/forgot-password.js"></script>