/*
 * (c) Copyright 2023, ANS. All rights reserved.
 */
$(document).ready(function() {                                
                let jsonViewer= new JSONViewer();
                if($("#json-dams").length) {
                document.querySelector("#json-dams").appendChild(jsonViewer.getContainer());                            
                jsonViewer.showJSON(JSON.parse(getDams()), -1, 2);    
                }  
        });                
function getDams(){
 return $("#dams").val();
}

