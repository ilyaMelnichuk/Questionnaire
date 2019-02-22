

	<div class="container" align="center">
		<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="panel panel-default"
				style="margin-top: 10px; min-width: 400px;">
				<div class="panel-heading" align="left"
					style="background-color: white;">
					<h4>Edit Profile</h4>
					<span id='message'></span>
				</div>
				<div class="panel-body logotype-panel">
					<form id="form" action="/check-changes" method="post">
						<div class="form-group" align="left">
							<label><span style="color: grey;">First Name</span></label> <br>
							<input type="text" id="firstName" class="form-control"> <span
								id='current-message' style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<label><span style="color: grey;">Last Name</span></label> <br>
							<input type="text" id="lastName" class="form-control"> <span
								id='password-message' style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<label><span style="color: grey;">Email</span> *</label> <input
								type="email" id="email" class="form-control" /> <span
								id='confirm-message' style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<label><span style="color: grey;">Phone Number</span></label> <input
								type="text" id="phoneNumber" class="form-control" /> <span
								id='confirm-message' style="color: red;"></span>
						</div>
						<div class="form-group" align="left">
							<input class="btn-primary form-control" id="button"
								style="max-width: 100px;" type="submit" value="SAVE">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
    <script src="${contextPath}/js/edit-profile.js"></script>