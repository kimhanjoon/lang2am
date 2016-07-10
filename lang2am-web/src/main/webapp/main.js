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
function highlight(text, queryRegExpList) {
	return _.reduce(queryRegExpList, function(m, e) {
		return m.replace(e, '<span class="yellow">$1</span>');
	}, text);
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
	$.get("text_table.hbs", function(template_text){
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
    		Materialize.toast('Failed : ' + jqXHR.responseJSON.message, 3000, "red accent-3");
		});
	});

    $('#newcode_table').pasteImageReader(function(result) {
    	var $img = $("<img></img>");
    	$img.attr("src", result.dataURL);
    	$img.attr("height", "70px");
    	$("#saveImage").append($img);
    });

	var _last_query = "";
	var _last_queryRegExpList = null;
	search_texts("");
	$("#search").keyup( _.debounce(function() {
		var query = $("#search").val();

		if( _last_query !== query ) {
			search_texts(query);
		}

	}, 1000));

	function search_texts(query, limit) {
		query = query.replace(/ +/g, " ").trim();
		_last_query = query
		var queryRegExpList = _.map(query.split(" "), function(e) {
			return new RegExp("("+escapeRegExp(_.escape(e))+")", "gi");
		});
		_last_queryRegExpList = queryRegExpList;
		console.log(query, " => ", queryRegExpList);

		var category = _.map($(".search-category:checked"), function(e) {
			return $(e).val();
		}).join(",");

		if( !limit ) {
			limit = 30;
		}

		$("#texts_table").empty();

		$.ajax({
    		method: "GET",
    		url: 'text',
    		data: {q:query, c:category, l:limit},
    		dataType: "json",
    	})
    	.done(function(data) {

			_.each(data.textlist, function(e) {
				e.textEn = _.escape(e.textEn);
				e.textKo = _.escape(e.textKo);
				e.textZh = _.escape(e.textZh);
			});

    		if( query && query.length > 0 ) {
    			_.each(data.textlist, function(e) {
    				e.code_highlight = highlight(e.code, queryRegExpList);
    				e.textEn_highlight = highlight(e.textEn, queryRegExpList);
    				e.textKo_highlight = highlight(e.textKo, queryRegExpList);
    				e.textZh_highlight = highlight(e.textZh, queryRegExpList);
    			});
    		}

    		$("#results-count").text(data.textlist.length + " of " + data.total);
			$("#texts_table").append(translate_table({
				texts: data.textlist,
				seeall: data.textlist.length < data.total,
				categoryCode: data.category ? data.category.indexOf("code") > -1 : false,
				categoryText: data.category ? data.category.indexOf("text") > -1 : false,
			}));
			$('#texts_table .dropdown-button').dropdown({ constrain_width: false });
			$('#texts_table .tooltipped').tooltip({delay: 50});
    	})
    	.fail(function(jqXHR) {
    		Materialize.toast('Error', 3000);
    	});
	}

	$("i.search").click(function(e) {
		search_texts(_last_query);
	});

	$(".search-category").change(function(e) {
		search_texts(_last_query);
	});

	$("i.refresh").click(function(e) {
		search_texts(_last_query);
	});

	$("#texts_table").on("click", ".source-code a", function(e) {
		copyToClipboard($(this).find("span").text());
	});

	$("#texts_table").on("click", "#btnEmptyNewcode", function() {
		$("#btnNewcode").click();
	});

	$("#texts_table").on("click", "#btnSeeall", function() {
		search_texts(_last_query, 99999);
	});

	//XXX pre-complie
	var edittext_table;
	$.get("edittext.hbs", function(template_text){
		edittext_table = Handlebars.compile(template_text);
	});

	$("#texts_table").on("click", "i.edit", function(e) {
		var $td = $(this).closest("td");
		var code = $td.closest("tr").data("code");
		var locale = $td.data("locale");
		$.get("text/" + code + "/" + locale, function(data) {
			$td.find(".text,i.edit").hide();
			$td.append(edittext_table(data));
			$td.find("textarea").focus();
		});
	});

	$("#texts_table").on("click", ".cancel-edittext", function(e) {
		var $td = $(this).closest("td");
		$td.find(".text-edittext, .edittext").remove();
		$td.find(".text").show();
		$td.find("i.edit").css('display','');
	});

	$("#texts_table").on("click", ".save-edittext", function(e) {
		var $td = $(this).closest("td");
		var code = $td.closest("tr").data("code");
		var locale = $td.data("locale");
		$.ajax({
    		method: "PUT",
    		url: 'text/' + code + '/' + locale,
    		data: {
    			text: $td.find("textarea").val()
    		}
    	})
    	.done(function(data) {
    		Materialize.toast('Saved.', 1500);
    		$td.find(".text-edittext, .edittext").remove();
    		$td.find(".text").html(highlight(data.text, _last_queryRegExpList)).show();
    		$td.find("i.edit").css('display','');
    	})
    	.fail(function(jqXHR) {
    		Materialize.toast('Failed : ' + jqXHR.responseJSON.message, 3000, "red accent-3");
    	});
	});
});