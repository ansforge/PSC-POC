$(document).ready(function() {                
                const pscToken = getJeton();
                const splited=pscToken.split('.');
                const header=atob(splited[0]);
                const body=atob(splited[1]);

                let jsonViewerHeader = new JSONViewer();
                document.querySelector("#json-header").appendChild(jsonViewerHeader.getContainer());                           
        		jsonViewerHeader.showJSON(JSON.parse(header), -1, -1);
                
                let jsonViewerBody = new JSONViewer();
                document.querySelector("#json-body").appendChild(jsonViewerBody.getContainer());                            
                jsonViewerBody.showJSON(JSON.parse(body), -1, -1);      
        });                
function getJeton(){
 return $("#token").val();
}

