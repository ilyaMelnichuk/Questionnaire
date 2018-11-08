$(document).ready(function() {
    	       $(':button[type="submit"]').prop('disabled', true);
    	       $('#inputPassword').keyup(function() {
    	           if($(this).val() == $('#inputConfirmPassword')) {
    	               $(':button[type="submit"]').prop('disabled', false);
    	           }else{
    	        	   $(':button[type="submit"]').prop('disabled', true);
    	           }
    	       };
    	       $('#inputConfirmPassword').keyup(function() {
        	       if($(this).val() == $('#inputPassword')) {
        	           $(':button[type="submit"]').prop('disabled', false);
        	       }else{
    	        	   $(':button[type="submit"]').prop('disabled', true);
    	           }
    	       };
    	     });