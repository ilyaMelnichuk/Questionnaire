/**
 * 
 */
var $alert;

function sendAlert(alertCode, alertMessage, placeToEmbed){
	var alertType;
	switch(alertCode){
	    case 0: 
	    	alertType = "success";
	    	break;
	    case 1:
	    	alertType = "warning";
	    	break;
	    default://error
	    	alertType = "danger";
	}
	$alert = $("<div id=\"alert\" class=\"alert alert-" + alertType + " alert-dismissible logotype-alert\" role=\"alert\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button><h4>" + alertMessage + "</h4></div></div>");
	$placeToEmbed = $(placeToEmbed)
	$placeToEmbed.prepend($alert);
}