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

$(function() {

	$("#search").focus();
	shortcut.add("Ctrl+Alt+F",function() {
		$("#search").focus();
	});
	shortcut.add("Ctrl+Alt+R",function() {
		search_translations(_last_query);
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
		Materialize.toast('Done', 1500);
	});
	
    $('#newcode_table').pasteImageReader(function(result) {
    	var $img = $("<img></img>");
    	$img.attr("src", result.dataURL);
    	$img.attr("height", "70px");
    	$("#saveImage").append($img);
    });
	
    function saveNewcode(code, locale, text) {
    	$.ajax({
    		method: "PUT",
    		url: 'translation/' + code + '/' + locale,
    		data: {
    			text: text
    		}
    	})
    	.done(function() {
    		Materialize.toast('Saved', 1500);
    	})
    	.fail(function(jqXHR) {
    		Materialize.toast('Failed : ' + jqXHR.error.message, 3000);
    	});
    }
    
	var _last_query = "";
	search_translations(_last_query);
	$("#search").keyup( _.debounce(function() {
		var query = $("#search").val();

		if( _last_query !== query ) {
			_last_query = query;
			search_translations(_last_query);
		}
		
	}, 1000));
	
	function search_translations(query) {
		$("#translations_table").empty();
		$.ajax({
    		method: "GET",
    		url: 'translation',
    		data: {q:query},
    		dataType: "json",
    	})
    	.done(function(data) {
    		
    		if( query && query.length > 0 ) {
    			var queryRegExp = new RegExp("("+escapeRegExp(query)+")", "gi");
        		console.log(query, " => ", queryRegExp);
    			_.each(data, function(e) {
    				e.code = highlight(e.code, queryRegExp);
    				e.textEn = highlight(e.textEn, queryRegExp);
    				e.textKo = highlight(e.textKo, queryRegExp);
    				e.textZh = highlight(e.textZh, queryRegExp);
    			});
    		}

			$("#translations_table").append(translate_table({
				translations: data,
			}));
    	})
    	.fail(function(jqXHR) {
    		Materialize.toast('Error', 3000);
    	});
	}
	
	$("#translations_table").on("click", "i.copy", function(e) {
		copyToClipboard($(this).closest("td").data("code"));
	})
});