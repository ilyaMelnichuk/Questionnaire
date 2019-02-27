var sizeTemplates;
var numberOfElementsTemplates;
var totalElementsTemplates;
var totalPagesTemplates;
var numberTemplates;

var sizeFields;
var numberOfElementsFields;
var totalElementsFields;
var totalPagesFields;
var numberFields;

var sizeChosen;
var numberOfElementsChosen;
var totalElementsChosen;
var totalPagesChosen;
var numberChosen;

function paginate(size, numberOfElements, totalElements, totalPages, number, pageAddress, preffix){
	$("." + preffix + "-paging#" + preffix + "-1").empty();
	$("." + preffix + "-paging#" + preffix + "-2").empty();
	$("." + preffix + "-paging#" + preffix + "-1").append("<label>" + (size*number + (numberOfElements == 0?0:1)).toString() + "-" + (size*number + numberOfElements).toString() +  " of " + (totalElements).toString() + "</label>");
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
	$("." + preffix + "-paging#" + preffix + "-2").append("<table id=\"" + preffix + "-pages\"></table>");
	$pageList = $("<tr></tr>");
	if(number != 0){
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (0).toString() + "\"><input class=\"page-link\" type=\"submit\" value=\"<<\" /></form></td>");
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "=\"><input class=\"page-link\" type=\"submit\" value=\"<\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (0).toString() + "\" action=\"/get-fields-page?page=" + (0).toString() + "\"><input type=\"submit\" value=\"<<\" disabled/></form></td>");
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (number - 1).toString() + "\" action=\"/get-fields-page?page=" + (number - 1).toString() + "=\"><input class=\"page-link\" type=\"submit\" value=\"<\" disabled/></form></td>");
	}
	while(i < pages && i < totalPages){
		if(number != startPage + i){
			$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "\"><input type=\"submit\" value=" + (startPage + i + 1).toString() + " /></form></td>");
		}else{
			$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (startPage + i + 1).toString() + "\" action=\"/get-fields-page?page=" + (startPage + i).toString() + "\"><input class=\"page-link\" style=\"color:blue;\"type=\"submit\" value=" + (startPage + i + 1).toString() + " disabled/></form></td>");
		}
		i++;
	}
	if(number != totalPages - 1 && totalPages != 0){
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "\"><input class=\"page-link\" type=\"submit\" value=\">\" /></form></td>");
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item disabled\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "\"><input class=\"page-link\" type=\"submit\" value=\">>\" /></form></td>");
	}else{
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (number + 1).toString() + "\" action=\"/get-fields-page?page=" + (number + 1).toString() + "\"><input class=\"page-link\" type=\"submit\" value=\">\" disabled/></form></td>");
		$pageList.append("<td><form class=\"" + preffix + "-js-page page-item\" id=\"" + (totalPages - 1).toString() + "\" action=\"/get-fields-page?page=" + (totalPages - 1).toString() + "\"><input class=\"page-link\" type=\"submit\" value=\">>\" disabled/></form></td>");
	}
	$pageList.appendTo("#" + preffix + "-pages");
	$("." + preffix + "-js-size").attr("id", pageAddress);
}




function loadTemplates(pageAddress, pageSize){
	var url = pageAddress + pageSize;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
			var templates = data["content"];
			$("#templates-table").append("<tr></tr>")
			for(var key in templates){ 
				$delete = "<button data-delete-id=\"" + templates[key].id + "\" class=\"delete\" style=\"background:transparent; border:none;\"> <span class=\"glyphicon glyphicon-trash\"> </span> </button>";
				$edit = "<button data-id=\"" + templates[key].id + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span class=\"glyphicon glyphicon-edit\"> </span> </button>";
				$row = $('<tr>'+
						'<td class=\"template-name\" data-id=\"' + templates[key].id + '\">'+templates[key].name+'</td>'+
						'<td class=\"template-fields\">'+templates[key].totalFields+'</td>'+
						'<td class=\"field-responses\">'+templates[key].totalResponses+'</td>'+
						'<td>'+
						'<div align=\"right\">'+
						$edit+ 
						$delete+
						'</div>'+
						'</td>'+
				'</tr>');
				$("#templates-table").append($row);
			}
			
			sizeTemplates = parseInt(data["size"]);
			numberOfElementsTemplates = parseInt(data["numberOfElements"]);
			totalElementsTemplates = parseInt(data["totalElements"]);
			totalPagesTemplates = parseInt(data["totalPages"]);
			numberTemplates = parseInt(data["number"]);
			paginate(sizeTemplates, numberOfElementsTemplates, totalElementsTemplates, totalPagesTemplates, numberTemplates, pageAddress, "templates");
		}
	});
} 

function loadFields(pageAddress, pageSize){
	var url = pageAddress + pageSize;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
			var fields = data["content"];
			$("#fields-table").append("<tr></tr>")
			for(var key in fields){ 
				$add = "<button data-id=\"" + fields[key].id + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span style=\"color:green\"class=\"glyphicon glyphicon-plus\"> </span> </button>";
				$options = (fields[key].options == "" ? "N/A" : $("<button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\">options <span class=\"caret\"></span></button><ul class=\"dropdown-menu scrollable-menu\" role=\"menu\"><li>" + fields[key].options + "</li></ul>"));
				$row = $('<tr>'+
						'<td class=\"field-label\" data-id=\"' + fields[key].id + '\">'+fields[key].label+'</td>'+
						'<td class=\"field-type\" data-options=\"'+ fields[key].options +'\">'+fields[key].type+'</td>'+
						'<td class=\"field-required\">'+fields[key].required+'</td>'+
						'<td class=\"field-isActive\">'+fields[key].isActive+'</td>'+
						'<td class=\"field-options\">'+$options+'</td>'+
						'<td>'+
						'<div align=\"right\">'+
						$add+ 
						'</div>'+
						'</td>'+
				'</tr>');
				$("#fields-table").append($row);
			}
			
			sizeFields = parseInt(data["size"]);
			numberOfElementsFields = parseInt(data["numberOfElements"]);
			totalElementsFields = parseInt(data["totalElements"]);
			totalPagesFields = parseInt(data["totalPages"]);
			numberFields = parseInt(data["number"]);
			paginate(sizeFields, numberOfElementsFields, totalElementsFields, totalPagesFields, numberFields, pageAddress, "fields");
		}
	});
} 


function loadChosen(pageAddress, pageSize){
	var url = pageAddress + pageSize;
	$.ajax({
		url:url,
		type:"GET",
		datatype:"json",
		success: function(data){
			var chosen = data["content"];
			$("#chosen-table").append("<tr></tr>")
			for(var key in chosen){ 
				$remove = "<button data-id=\"" + chosen[key].id + "\" class=\"edit\" style=\"background:transparent; border:none;\" data-toggle=\"modal\" data-target=\"#editModal\"> <span style=\"color:red\"class=\"glyphicon glyphicon-minus\"> </span> </button>";
				$options = (chosen[key].options == "" ? "N/A" : $("<button type=\"button\" class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\">options <span class=\"caret\"></span></button><ul class=\"dropdown-menu scrollable-menu\" role=\"menu\"><li>" + chosen[key].options + "</li></ul>"));
				$row = $('<tr>'+
						'<td class=\"field-label\" data-id=\"' + chosen[key].id + '\">'+chosen[key].label+'</td>'+
						'<td class=\"field-type\" data-options=\"'+ chosen[key].options +'\">'+chosen[key].type+'</td>'+
						'<td class=\"field-required\">'+chosen[key].required+'</td>'+
						'<td class=\"field-isActive\">'+chosen[key].isActive+'</td>'+
						'<td class=\"field-options\">'+$options+'</td>'+
						'<td>'+
						'<div align=\"right\">'+
						remove+ 
						'</div>'+
						'</td>'+
				'</tr>');
				$("#chosen-table").append($row);
			}
			
			sizeChosen = parseInt(data["size"]);
			numberOfElementsChosen = parseInt(data["numberOfElements"]);
			totalElementsChosen = parseInt(data["totalElements"]);
			totalPagesChosen = parseInt(data["totalPages"]);
			numberChosen = parseInt(data["number"]);
			paginate(sizeChosen, numberOfElementsChosen, totalElementsChosen, totalPagesChosen, numberChosen, pageAddress, "chosen");
		}
	});
} 




$('document').ready(function(){
	loadTemplates("/get-templates-page?page=0", "&size=5");
	var $activeTemplate;
	
    //deleting field
	$(document).on("click", ".delete", function(){
		var templateName = $(this).closest("tr").children("td").eq(0).html();
		if(confirm("Are you sure you want to delete template '" + templateName + "'?")){
			var label = {};
			label["message"] = $(this).closest("tr").children("td").eq(0).data("id");
			$.ajax({
				url:"delete-template",
				type:"POST",
				data:JSON.stringify(label),
				datatype:"json",
				contentType:"application/json",
				success:function(data){
				}
			});
			$(this).closest("tr").remove();
			setTimeout(function(){if(numberOfElementsTemplates == 1){
				if(numberTemplates != 0){
					var s = "&size=" + (sizeTemplates).toString();
					var url = "/get-templates-page?page=" + (numberTemplates - 1).toString();
					$("#templates-table").empty();
					$(".templates-paging#1").empty();
					$(".templates-paging#2").empty();
					load(url, s);
				}else{
					--numberOfElementsTemplates;
					--totalElementsTemplates;
					--totalPagesTemplates;
					$(this).closest("tr").remove();
					$(".templates-paging#1").empty();
					$(".templates-paging#2").empty();
					paginate(sizeTemplates, numberOfElementsTemplates, totalElementsTemplates, totalPagesTemplates, numberTemplates, "/get-templates-page?page=" + (numberTemplates).toString());
				}
			}else if(numberOfElementsTemplates < sizeTemplates || numberTemplates + 1 == totalPagesTemplates){
				$(this).closest("tr").remove();
				--numberOfElementsTemplates;
				--totalElementsTemplates;
				$(".templates-paging#1").empty();
				$(".templates-paging#2").empty();
				paginate(sizeTemplates, numberOfElementsTemplates, totalElementsTemplates, totalPagesTemplates, numberTemplates, "/get-templates-page?page=" + (numberTemplates).toString());
			}else{
				var s = "&size=" + (sizeTemplates).toString();
				var url = "/get-templates-page?page=" + (numberTemplates).toString();
				$("#templates-table").empty();
				$(".templates-paging#1").empty();
				$(".templates-paging#2").empty();
				load(url, s);
			}}, 100);
			sendAlert(1, "template '" + templateName + "' has been deleted", ".container");
		}
	});



	$(document).on("click", ".edit", function(e){
		$('#editModal').modal(); 
	});


	//filling in the inputs when modal opens 
	$(document).on("shown.bs.modal", "#editModal", function(e){
		loadFields("/get-fields-page?page=0", "&size=5");
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
			sendAlert(2, "Label of field shouldn't be empty!", ".container")
		}    
		$.ajax({
			url:"save-field",
			type:"POST",
			data:JSON.stringify(fieldToSend),
			datatype:"json",
			contentType:"application/json",
			success:function(data){
				if($activeField == "new" && $.isNumeric(data["message"])){
					if(numberOfElements < size){
						$row.children("td").eq(0).data("id", parseInt(data["message"]));
					}
					sendAlert(0, "Field '" + fieldLabel + "' of type '" + $("#type-input").val() + "' "  + " has been created", ".container");
				}else if($.isNumeric(data["message"])){
					fieldToSend["oldLabel"]=$activeField.eq(0).html();
					$activeField.eq(0).html(fieldLabel);
					$activeField.eq(1).html($("#type-input").val());
					if($("#type-input").val() == "Combobox" || $("#type-input").val() == "Radio button" || $("#type-input").val() == "Checkbox"){
						$activeField.eq(1).data("options", fieldOptions);
					}
					$activeField.eq(2).html($("#required-input").is(":checked")?"true":"false");
					$activeField.eq(3).html($("#isActive-input").is(":checked")?"true":"false");
					sendAlert(0, "Field '" + fieldLabel + "' of type '" + $("#type-input").val() + "' "  + " has been updated", ".container");
				} else {
					sendAlert(2, $("#message").html(data["message"]), ".container");
				}
			}
		});
	});

	$(document).on("submit", ".js-page", function(e) {
		e.preventDefault();
		var s = $(".js-size").first().val()==""?"5":$(".js-size").first().val();
		if(parseInt(s) > 0){
			s = "&size=" + s;
			var url = $(e.target).attr("action").toString();
			$("#tbody").empty();
			$(".paging#1").empty();
			$(".paging#2").empty();
			load(url, s);
		}else{
			sendAlert(2, "Please, provide correct page size!", ".container");
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
				sendAlert(2, "Please, provide correct page size!", ".container");
			}
		}
	});
	
	$(document).on('keyup',  function (e) {
		if(e.keyCode == 13 && $('#editModal').is(':visible') == true && $("#options-input").is(":focus") == false) {
			e.preventDefault();
			$("#save").click();
		}
    });
});