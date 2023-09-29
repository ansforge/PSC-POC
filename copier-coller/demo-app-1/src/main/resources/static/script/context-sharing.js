let pscContext;

function getFromCache(serverURL) {
	console.log("GEtfromCahe serveurURL: " + serverURL)
	
    $.get(serverURL, function (data) {
		console.log('getFromCache, serveurUrl: ' + serverURL)
        console.log('etFromCache, data: ' + data)
        console.log(data)
        if (data !== null && data !== '') {
            pscContext = data;
            const btnPreFill = $('#btnPreFill');
            const contextTooltip = $('#contextTooltip')

            btnPreFill.removeAttr('hidden');
            contextTooltip.removeAttr('hidden')
            document.getElementById('contextTooltip').setAttribute(
                'title', JSON.stringify(pscContext, null, 2))
        }
    });
}

function fillForm(mappingFilePath) {
	console.log('fillForm, mappingFilePath: ' + mappingFilePath)
    $.getJSON(window.location.origin + mappingFilePath, function(data) {
		
		console.log('fillForm,$.getJSON, pscContext : ' + pscContext)
        for (const [key, value] of Object.entries(data)) {
			console.log('fillForm key-value: ' + key + ' ' + value)
            if (document.getElementById(key)) {
                $('#' + key).val(_.get(pscContext, value, ''))
            }
        }
    })
}

function putInCache(schemaName, serverURL, viewURL, mappingFilePath) {
    let putPscContext = {};
    console.log('putincache, serverURL ' + serverURL + ' mappingFilePath ' + mappingFilePath + ' viewURL ' + viewURL + ' schemaName ' + schemaName)
    $.getJSON(window.location.href.replace('patient/form',mappingFilePath) , function (data) {
        for (const [key, value] of Object.entries(data)) {
            if (document.getElementById(key)) {
                _.set(putPscContext, value, document.getElementById(key).value)
            }
        }
        _.set(putPscContext, 'schemaId', schemaName);
        console.log('putincache, putPscContext ..')
        console.log(putPscContext)
        
        
        $.ajax({
            url: '../share',
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


