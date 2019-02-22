<div class="container" align="center">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
	    <div class="alert alert-success logotype-alert" role="alert">
	         <h4>You've been registered successfully!</h4>
	    </div>
		<div class="jumbotron logotype-jumbotron3">
			<form id="form" method="POST" action="${contextPath}/login">
				<div class="form-group" align="center">
					<h2>
						<strong>LOGO</strong><span style="color: blue;">TYPE</span>
					</h2>
				</div>
				<div class="form-group" align="center">
					<h4>Log In</h4>
				</div>
				<div class="form-group" align="center">
					<label id="message">${message}</label>
				</div>
				<div class="form-group">
					<input id="email" type="text" name="email" placeholder="Email"
						value="${contextPath}" class="form-control">
				</div>
				<div class="form-group">
					<input id="password" type="password" name="password"
						placeholder="Password" class="form-control" />
				</div>
				<div class="from-group">
					<label> <input type="checkbox" name="remember_me">
						Remember me <a href="${contextPath}/forgot-password">Forgot
							your password?</a>
					</label>
				</div>
				<br>
				<button class="btn-primary form-control" type="submit">LOG
					IN</button>
				<br>
				<div class="form-group">
					<label> Don't have account? <a
						href="<c:url value="${contextPath}/signup" />">Sign up</a>
					</label>
				</div>
			</form>
		</div>
	</div>
	<div class="col-lg-4"></div>
</div>
<script src="${contextPath}/js/login.js"></script>