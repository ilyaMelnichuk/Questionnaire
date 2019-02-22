<div class="container" align="center">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
		<div class="panel panel-default"
			style="margin-top: 10px; min-width: 400px;">
			<div class="panel-heading" align="left"
				style="background-color: white;">
				<h4>Sign Up</h4>
				<span style="color: red;" id='message'></span>
			</div>
			<div class="panel-body logotype-panel">
				<form id="form" action="${contextPath}/signup" method="post">
					<div class="form-group" align="left">
						<input type="email" id="email" class="form-control"
							placeholder="Email*"> <span id='current-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input type="password" id="password" class="form-control"
							placeholder="Password*"> <span id='password-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input type="password" id="confirmPassword" class="form-control"
							placeholder="Confirm Password*"> <span
							id='confirm-message' style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input type="text" id="firstName" class="form-control"
							placeholder="First Name" /> <span id='confirm-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input type="text" id="lastName" class="form-control"
							placeholder="Last Name" /> <span id='confirm-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input type="text" id="phoneNumber" class="form-control"
							placeholder="Phone Number" /> <span id='confirm-message'
							style="color: red;"></span>
					</div>
					<div class="form-group" align="left">
						<input class="btn-primary form-control" id="button"
							style="max-width: 100px;" type="submit" value="SIGN UP">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="${contextPath}/js/signup.js"></script>