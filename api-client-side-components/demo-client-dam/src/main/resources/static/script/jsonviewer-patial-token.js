/*
 * (c) Copyright 2023, ANS. All rights reserved.
 */
$(document).ready(function() {                
                const header=getData("#header");
                const body=getData("#body");

                let jsonViewerHeader = new JSONViewer();
                document.querySelector("#json-header").appendChild(jsonViewerHeader.getContainer());                           
        		jsonViewerHeader.showJSON(JSON.parse(header), -1, -1);
                
                let jsonViewerBody = new JSONViewer();
                document.querySelector("#json-body").appendChild(jsonViewerBody.getContainer());                            
                jsonViewerBody.showJSON(JSON.parse(body), -1, -1);      
        });                
function getData(id){
 return $(id).val();
}

