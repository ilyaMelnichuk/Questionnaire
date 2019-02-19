var size;
var numberOfElements;
var totalElements;
var totalPages;
var number;

function paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress){
	$(".paging#1").empty();
	$(".paging#2").empty();
	$(".paging#1").append("<label>" + (size*number + (numberOfElements == 0?0:1)).toString() + "-" + (size*number + numberOfElements).toString() +  " of " + (totalElements).toString() + "</label>");
	var pages;
	var startPage = 0;
	if(totalPages < 5){
		pages = totalPages;
	}else{
		pages = 5;
	}

	if(number < 2){
		startPages = 0;
	}else if(totalPages - number < 3){ 
		if(totalPages - number == 1){
			if(totalPages < 5){
				startPage = 0;
			}else{
				startPage = number - 4;	
			}
		}else{
			if(totalPages < 5){
				startPage = 0;
			}else{
				startPage = number - 3;	
			}
		}
	}else{
		startPage = number - 2;
	}
	var i = 0;
	$(".paging#2").append("<table id=\"pages\"></table>");
	$pageList = $("<tr></tr>");
	if(number != 0){
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (0).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<<\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (0).toString() + "&size=\"><input type=\"submit\" value=\"<<\" disabled/></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\"<\" disabled/></form></td>");
	}
	while(i < pages && i < totalPages){
		if(number != startPage + i){
			$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "&size=\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
		}else{
			$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "&size=\"><input class=\"page-link\" style=\"color:blue;\"type=\"submit\" value=" + (startPage + i + 1).toString() + " disabled/></form></td>");
		}
		i++;
	}
	if(number != totalPages - 1 && totalPages != 0){
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">\" /></form></td>");
		$pageList.append("<td><form class=\"js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">>\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">\" disabled/></form></td>");
		$pageList.append("<td><form class=\"js-page page-item\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "&size=\"><input class=\"page-link\" type=\"submit\" value=\">>\" disabled/></form></td>");
	}
	$pageList.appendTo("#pages");
	$(".js-size").attr("id", pageAddress);
}

function load(pageAddress, pageSize){
	var url = pageAddress + pageSize;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
			var fields = data["content"];
			$("#tbody").append("<tr></tr>")
			for(var key in fields){ 
				$delete = "<button data-delete-id=\"" + fields[key].label + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>";
				$edit = "<button id=\"" + fields[key].label + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>";
				$row = $('<tr>'+
						'<td class=\"field-label\" data-id=\"' + fields[key].id + '\">'+fields[key].label+'</td>'+
						'<td class=\"field-type\" data-options=\"'+ fields[key].options +'\">'+fields[key].type+'</td>'+
						'<td class=\"field-required\">'+fields[key].required+'</td>'+
						'<td class=\"field-isActive\">'+fields[key].isActive+'</td>'+
						'<td>'+
						'<div align=\"right\">'+
						$edit+ 
						$delete+
						'</div>'+
						'</td>'+
				'</tr>');
				$row.attr("id", fields[key].label);
				$("#tbody").append($row);
			}
			
			size = parseInt(data["size"]);
			numberOfElements = parseInt(data["numberOfElements"]);
			totalElements = parseInt(data["totalElements"]);
			totalPages = parseInt(data["totalPages"]);
			number = parseInt(data["number"]);
			//polls = data["content"];
			paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress);
		}
	});
} 
$('document').ready(function(){
	load("/get-fields-page?page=0", "&size=5");
	var $activeField;
    //deleting field
	$(document).on("click", ".delete", function(){
		if(confirm("Are you sure you want to delete field \"" + $(this).closest("tr").children("td").eq(0).html() + "\"?")){
			var label = {};
			label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
			$.ajax({
				url:"delete-field",
				type:"POST",
				data:JSON.stringify(label),
				datatype:"json",
				contentType:"application/json",
				success:function(data){
				}
			});
			$(this).closest("tr").remove();
			setTimeout(function(){if(numberOfElements == 1){
				if(number != 0){
					var s = "&size=" + (size).toString();
					var url = "/get-fields-page?page=" + (number - 1).toString();
					$("#tbody").empty();
					$(".paging#1").empty();
					$(".paging#2").empty();
					load(url, s);
				}else{
					--numberOfElements;
					--totalElements;
					--totalPages;
					$(this).closest("tr").remove();
					$(".paging#1").empty();
					$(".paging#2").empty();
					paginate(size, numberOfElements, totalElements, totalPages, number, "/get-fields-page?page=" + (number).toString());
				}
			}else if(numberOfElements < size || number + 1 == totalPages){
				$(this).closest("tr").remove();
				--numberOfElements;
				--totalElements;
				$(".paging#1").empty();
				$(".paging#2").empty();
				paginate(size, numberOfElements, totalElements, totalPages, number, "/get-fields-page?page=" + (number).toString());
			}else{
				var s = "&size=" + (size).toString();
				var url = "/get-fields-page?page=" + (number).toString();
				$("#tbody").empty();
				$(".paging#1").empty();
				$(".paging#2").empty();
				load(url, s);
			}}, 100);
		}
	});



	$(document).on("click", ".edit", function(e){
		$('#editModal').modal(); 
	});


	//filling in the inputs when modal opens 
	$(document).on("shown.bs.modal", "#editModal", function(e){
		if($(e.relatedTarget).attr("id") == "addButton"){
			$("#options-div").hide();
			$activeField = "new";
		}else{
			$activeField = $(e.relatedTarget).closest("tr").children("td"); 
			$("#label-input").data("id", $activeField.eq(0).data("id"));
			$("#label-input").val($activeField.eq(0).html());
			$("#type-input").val($activeField.eq(1).html());
			if($activeField.eq(1).html() == "Combobox" || $activeField.eq(1).html() == "Radio button" || $activeField.eq(1).html() == "Checkbox"){
				$("#options-div").show();
				$("#options-input").html($activeField.eq(1).data("options"));
			}else{
				$("#options-input").val('');
				$("#options-div").hide();
			}
			if($activeField.eq(2).html() == "true"){
				$("#required-input").prop("checked", true);
			}else{
				$("#required-input").prop("checked", false);
			}
			if($activeField.eq(3).html() == "true"){
				$("#isActive-input").prop("checked", true);
			}else{
				$("#isActive-input").prop("checked", false);
			}
		}
	});
	//changing type of a field
	$("#type-input").on("change", function() {
		if(this.value == "Combobox" || this.value == "Radio button" || this.value == "Checkbox"){
			$("#options-div").show();
		}else{
			$("#options-div").hide();
		}
	});
	//saving a field
	$(document).on("click", '#save', function(){
		var $row;
		if($("#label-input").val() != ""){
			var fieldToSend = {};
			fieldToSend["label"] = $("#label-input").val();
			
			var fieldLabel = $("#label-input").val().replace(new RegExp("&", "g"), "&amp");
			fieldLabel = fieldLabel.replace(new RegExp('"', "g"), '\"');
			fieldLabel = fieldLabel.replace(new RegExp("'", "g"), "\'");
			fieldLabel = fieldLabel.replace(new RegExp("<", "g"), "&lt");
			fieldLabel = fieldLabel.replace(new RegExp(">", "g"), "&gt");		

			var fieldOptions = $("#options-input").val().replace(new RegExp("&", "g"), "&amp");
			fieldOptions = fieldOptions.replace(new RegExp('"', "g"), '\"');
			fieldOptions = fieldOptions.replace(new RegExp("'", "g"), "\'");
			fieldOptions = fieldOptions.replace(new RegExp("<", "g"), "&lt");
			fieldOptions = fieldOptions.replace(new RegExp(">", "g"), "&gt");

			fieldToSend["required"]=$("#required-input").is(":checked");
			
			fieldToSend["isActive"]=$("#isActive-input").is(":checked");
			
			fieldToSend["type"]= $("#type-input").val();
			
			if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
				fieldToSend["options"] = fieldOptions;
			}else{
				fieldToSend["options"]="";
			}
			if($activeField != "new"){
				fieldToSend["id"]=$("#label-input").data("id");
				$activeField.eq(0).html(fieldLabel);
				$activeField.eq(1).html($("#type-input").val());
				if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
					$activeField.eq(1).data("options", fieldOptions);
				}
				$activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
				$activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
			}else{
				
				if(numberOfElements < size){
					++numberOfElements;
					$row = $('<tr>'+
							'<td class=\"field-label\">'+fieldLabel+'</td>'+
							'<td class=\"field-type\" data-options=\"'+fieldOptions+'\">'+$("#type-input").val()+'</td>'+
							'<td class=\"field-required\">'+$("#required-input").is(":checked")+'</td>'+
							'<td class=\"field-isActive\">'+$("#isActive-input").is(":checked")+'</td>'+
							'<td>'+
							'<div align=\"right\">'+
							"<button id=\"" + fieldLabel + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>"+
							"<button data-delete-id=\"" + fieldLabel + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>"+
							'</div>'+
							'</td>'+
					'</tr>');
					$row.attr("id", fieldLabel);
					$("#tbody").append($row);
				}
				if(totalElements%(size*totalPages) == 0 || totalElements == 0){
					++totalPages;
				}
				++totalElements;
				$(".paging#1").empty();
				$(".paging#2").empty();
				paginate(size, numberOfElements, totalElements, totalPages, number, "/get-fields-page?page=" + (number).toString());
			}
		}else{
			$("#message").html("label shouldn't be empty");
		}    
		$.ajax({
			url:"save-field",
			type:"POST",
			data:JSON.stringify(fieldToSend),
			datatype:"json",
			contentType:"application/json",
			success:function(data){
				if($activeField == "new" && $.isNumeric(data["message"])){
					$("#message").html("field has been created");
					if(numberOfElements - 1 < size){
						$row.children("td").eq(0).data("id", parseInt(data["message"]));
					}
				}else if(data["message"] == "field has been updated"){
					$("#message").html(data["message"]);
					fieldToSend["oldLabel"]=$activeField.eq(0).html();
					$activeField.eq(0).html(fieldLabel);
					$activeField.eq(1).html($("#type-input").val());
					if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
						$activeField.eq(1).data("options", options);
					}
					$activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
					$activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
				} else {
					$("#message").html(data["message"]);
				}
			}
		});
	})
	
	$(document).on("hidden.bs.modal", "#editModal", function(e){
		$("#message").html("");
		$("#label-input").data("id");
		$("#type-input").val("Single line text");
		$("#options-input").html("");
		$("#options-div").hide();
		$("#required-input").prop("checked", false);
		$("#isActive-input").prop("checked", false);
	});


	$(document).on("submit", ".js-page", function(e) {
		e.preventDefault();
		var s = $(".js-size").first().val()==""?"5":$(".js-size").first().val();
		if(parseInt(s) > 0){
			var url = $(e.target).attr("action").toString();
			$("#tbody").empty();
			$(".paging#1").empty();
			$(".paging#2").empty();
			load(url, s);
		}else{
			alert("Please, provide correct page size!");
		}
	});
	$(document).on("keyup", ".js-size", function(e) {
		if(e.keyCode == 13){
			e.preventDefault();
			var s = $(e.target).val();
			if(parseInt(s) > 0){
				s = "&size=" + s;
				var url = $(e.target).attr("id");
				$("#tbody").empty();
				$(".paging#1").empty();
				$(".paging#2").empty();
				load(url, s);
			}else{
				alert("Please, provide correct page size!");
			}
		}
	});
});