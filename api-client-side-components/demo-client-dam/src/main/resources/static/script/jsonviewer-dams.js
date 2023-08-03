$(document).ready(function() {                                
                let jsonViewer= new JSONViewer();
                document.querySelector("#json-dams").appendChild(jsonViewer.getContainer());                            
                jsonViewer.showJSON(JSON.parse(getDams()), -1, -1);      
        });                
function getDams(){
 return $("#dams").val();
}

