/*
 * (c) Copyright 2023, ANS. All rights reserved.
 */
let pscContext;

function getFromCache(serverURL) {
	let bearer = getBearerFormCookie();

	$.ajax({
		url: '../share',
		headers: { "Authorization": bearer },
		type: 'GET'
	})
		.done(function(data) {
			if (data !== null && data !== '') {
				pscContext = data;
				const btnPreFill = $('#btnPreFill');
				const contextTooltip = $('#contextTooltip')

				btnPreFill.removeAttr('hidden');
				contextTooltip.removeAttr('hidden')
				document.getElementById('contextTooltip').setAttribute(
					'title', JSON.stringify(pscContext, null, 2))
			}
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			//alert("La lecture du cache a achoué .\n\n Erreur: " + jqXHR.status +"\n\n Message: " + jqXHR.responseText)				
		})
}

function fillForm(mappingFilePath) {
	let file = window.location.href.split('/patient')[0]
	file = file + '/' + mappingFilePath
	$.getJSON(file, function(data) {
		for (const [key, value] of Object.entries(data)) {
			if (document.getElementById(key)) {
				$('#' + key).val(_.get(pscContext, value, ''))
			}
		}
	})
}

function putInCache(schemaName, serverURL, viewURL, mappingFilePath) {
	let putPscContext = {};
	let file = window.location.href.split('/patient')[0]
	file = file + '/' + mappingFilePath
	$.getJSON(file, function(data) {
		for (const [key, value] of Object.entries(data)) {
			if (document.getElementById(key)) {
				_.set(putPscContext, value, document.getElementById(key).value)
			}
		}
		_.set(putPscContext, 'schemaId', schemaName);

		let bearer = getBearerFormCookie();

		$.ajax({
			url: '../share',
			headers: { "Authorization": bearer },
			type: 'PUT',
			dataType: 'json',
			contentType: 'application/json',
			data: JSON.stringify(putPscContext)
		})
			.done(function(data) { window.location.href = viewURL })
			.fail(function(jqXHR, textStatus, errorThrown) {
				alert("L'enregistrement des données a échoué.\n\n Erreur: " + jqXHR.status + "\n\n Message: " + jqXHR.responseText)
			})
	})
}

function getBearerFormCookie() {
	let token = getCookie("sts_token");
//	console.log("getBearerFormCookie token: " + token.split('.')[1]);
	if (token == null || token.length < 5) {
		alert("token non trouvé");
	}
	return "Bearer " + token;
}

function getCookie(name) {
	const value = `; ${document.cookie}`;
	const parts = value.split(`; ${name}=`);
	if (parts.length === 2) return parts.pop().split(';').shift();
}

