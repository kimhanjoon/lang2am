/**
 * http://stackoverflow.com/a/3561711
 */
function escapeRegExp(s) {
    return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
}
function copyToClipboard(text) {
	console.log(text);
    var $temp = $("<input>")
    $("body").append($temp);
    $temp.val(text).select();
    document.execCommand("copy");
    $temp.remove();
}
function highlight(text, queryRegExp) {
	return text.replace(queryRegExp, '<span class="yellow">$1</span>')
}
Handlebars.registerHelper('nvl', function(first, second) {
	return first ? first : second;
});
$(function() {

	$("#search").focus();
	shortcut.add("Ctrl+Alt+F",function() {
		$("#search").focus();
	});
	shortcut.add("Ctrl+Alt+R",function() {
		search_texts(_last_query);
	});
	shortcut.add("Ctrl+Alt+O",function() {
		$("#btnNewcode").click();
	});
	shortcut.add("Ctrl+Alt+S",function() {
		$("#btnSaveNewcode").click();
		//TODO 신규 생성된 코드 클립보드에 복사
	}, {target: $("#newcode_table").get(0)});
	shortcut.add("Ctrl+Alt+C",function() {
		$("#btnCancelNewcode").click();
		$("#search").focus();
	});
	
	//XXX pre-complie
	var translate_table;
	$.get("translate_table.hbs", function(template_text){
		translate_table = Handlebars.compile(template_text);
	});

	$("#btnNewcode").click(function() {
		$("#newcode_card").show();
		$("#en").focus();
	});
	$("#btnCancelNewcode").click(function() {
		$("#newcode_card").hide();
	});
	$("#btnSaveNewcode").click(function() {
		$.post("text/", {
			code: $("#code").val(),
			ko: $("#ko").val(), 
			en: $("#en").val(), 
			zh: $("#zh").val(),
		})
		.done(function(data) {
    		Materialize.toast('Saved.', 1500);
    		copyToClipboard(data.code);
    		$("#code,#ko,#en,#zh").val("");
    		$("#en").focus();
    		$("#search").val(data.code);
    		search_texts(data.code);
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
    		Materialize.toast('Failed : ' + jqXHR.responseJSON.message, 3000);
		});
	});
	
    $('#newcode_table').pasteImageReader(function(result) {
    	var $img = $("<img></img>");
    	$img.attr("src", result.dataURL);
    	$img.attr("height", "70px");
    	$("#saveImage").append($img);
    });
	
	var _last_query = "";
	search_texts("");
	$("#search").keyup( _.debounce(function() {
		var query = $("#search").val();

		if( _last_query !== query ) {
			search_texts(query);
		}
		
	}, 1000));
	
	function search_texts(query) {
		_last_query = query;
		$("#texts_table").empty();
		$.ajax({
    		method: "GET",
    		url: 'text',
    		data: {q:query},
    		dataType: "json",
    	})
    	.done(function(data) {
    		
    		if( query && query.length > 0 ) {
    			var queryRegExp = new RegExp("("+escapeRegExp(query)+")", "gi");
        		console.log(query, " => ", queryRegExp);
    			_.each(data, function(e) {
    				e.code_highlight = highlight(e.code, queryRegExp);
    				e.textEn_highlight = highlight(e.textEn, queryRegExp);
    				e.textKo_highlight = highlight(e.textKo, queryRegExp);
    				e.textZh_highlight = highlight(e.textZh, queryRegExp);
    			});
    		}

			$("#texts_table").append(translate_table({
				texts: data,
			}));
    	})
    	.fail(function(jqXHR) {
    		Materialize.toast('Error', 3000);
    	});
	}
	
	$("#texts_table").on("click", "i.copy", function(e) {
		copyToClipboard($(this).closest("td").data("code"));
	})
});