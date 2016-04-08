/**
 * http://stackoverflow.com/a/3561711
 */
function escapeRegExp(s) {
    return s.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&');
};

Handlebars.registerHelper('highlight', function(text, query) {
	return text.replace(new RegExp(escapeRegExp(query), "gi"), '<span class="yellow">' + query + '</span>')
});

$(function() {

	var _allTranslation = [];
	$.ajax({
		method: "GET",
		url: 'translation',
		dataType: "json"
	})
	.done(function(data) {
		_allTranslation = data;
	})
	.fail(function(jqXHR) {
		Materialize.toast('Error', 3000);
	});
	
	var translate_card;
	$.get("translate_card.hbs", function(template_text){
	    translate_card = Handlebars.compile(template_text);
	});

	$("#btnNewcode").click(function() {
		$("#rowNewcode").show();
		$("#en").focus();
	});
	$("#btnCancelNewcode").click(function() {
		$("#rowNewcode").hide();
	});
	$("#btnSaveNewcode").click(function() {
		Materialize.toast('Done', 1500);
	});
	
	var $column1 = $("#column1");
	var $column2 = $("#column2");
	var $column3 = $("#column3");
	$("#search").keyup( _.debounce(function() {
		$column1.empty();
		$column2.empty();
		$column3.empty();
		var query = $("#search").val();
		if( !query ) {
			return false;
		}
		query = query.toLowerCase();

		var list = _.chain(_allTranslation)
		.filter(function(e) {
			return (e.code + e.textEn + e.textKo + e.textZh).toLowerCase().indexOf(query) > -1;
		})
		.first(30)
		.each(function(e, i) {
			e.query = query;
			
			var html = translate_card(e);
			if( i%3 === 0 ) {
				$column1.append(html);
			}
			else if( i%3 === 1 ) {
				$column2.append(html);
			}
			else if( i%3 === 2 ) {
				$column3.append(html);
			}
		});
	}, 1000));
	
	function renderTranslationCards(translations) {
		
	}
})