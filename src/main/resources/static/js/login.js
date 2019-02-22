$("#form").on("submit", function(e){
	if($("#email").val().length < 5){
		$("#message").html("Please enter email!");
		e.preventDefault();
	}else if($("#password").val().length < 6){
		$("#message").html("Please enter password!");
		e.preventDefault();
	}
});