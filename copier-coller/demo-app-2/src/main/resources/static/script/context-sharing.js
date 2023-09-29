let pscContext;

function getFromCache(serverUrl) {
    $.get(serverUrl, function (data) {
		console.log('getFromCache, serveurUrl: ' + serverURL)
        console.log('etFromCache, data: ' + data)
        console.log(data)
        if (data !== null && data !== "") {
            pscContext = data;
            const btnPreFill = $("#btnPreFill");
            const contextTooltip = $('#contextTooltip')

            btnPreFill.removeAttr("hidden");
            contextTooltip.removeAttr('hidden')
            document.getElementById('contextTooltip').setAttribute(
                'title', JSON.stringify(pscContext, null, 2))
        }
    });
}

function fillForm(mappingFilePath) {
    $.getJSON(window.location.origin + mappingFilePath, function(data) {
        for (const [key, value] of Object.entries(data)) {
            if (document.getElementById(key)) {
                $('#' + key).val(_.get(pscContext, value, ''))
            }
        }
    })
}

function putInCache(schemaName, serverUrl, viewURL, mappingFilePath) {
    let putPscContext = {};
	console.log('putincache, serverURL ' + serverURL + ' mappingFilePath ' + mappingFilePath + ' viewURL ' + viewURL + ' schemaName ' + schemaName)
    $.getJSON(window.location.origin + mappingFilePath, function (data) {
        for (const [key, value] of Object.entries(data)) {
            if (document.getElementById(key)) {
                _.set(putPscContext, value, document.getElementById(key).value)
            }
        }
        _.set(putPscContext, "schemaId", schemaName)

		console.log('putincache, putPscContext ..')
        console.log(putPscContext)
        
        serverURL= '/copier-coller' + serverURL
        console.log ('serverURL corrigée =>' + serverURL)
        $.ajax({
            url: serverUrl,
            type: 'PUT',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(putPscContext)
        })
            .done(function(data) { window.location.href=viewURL })
            .fail(function(jqXHR, textStatus, errorThrown) {
            	 	alert("L'enregistrement des données a échoué.\n\n Erreur: " + jqXHR.status +"\n\n Message: " + jqXHR.responseText)				
            	 })
    })
}


