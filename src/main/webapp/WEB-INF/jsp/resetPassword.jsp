<div class="container" align="center">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
		<div class="panel panel-default"
			style="margin-top: 10px; min-width: 400px;">
			<div class="panel-heading" align="left"
				style="background-color: white;">
				<h4>Reset Password</h4>
			</div>
			<div class="panel-body logotype-panel">
				<form id="form" action="reset-password" method="post">
					<div class="form-group" align="left">
						<label><span style="color: grey;">Confirm New
								Password</span> *</label> <br> <input type="password" id="password"
							class="form-control"> <span id='password-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<label id="label"><span style="color: grey;">Confirm
								New Password</span> *</label> <input type="password" id="confirmPassword"
							class="form-control" /> <span id='confirm-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input class="btn-primary form-control" id="button"
							style="max-width: 100px;" type="submit" value="CHANGE">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="${contextPath}/js/reset-password.js"></script>