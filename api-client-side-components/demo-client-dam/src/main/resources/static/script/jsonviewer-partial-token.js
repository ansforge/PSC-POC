/*
 * (c) Copyright 2023, ANS. All rights reserved.
 */
$(document).ready(function() {                
                const header=getData("#header");
                const body=getData("#body");                           
                let jsonViewerHeader = new JSONViewer();    
                let jsonViewerBody = new JSONViewer();
                   document.querySelector("#json-header").appendChild(jsonViewerHeader.getContainer());                           
    jsonViewerHeader.showJSON(JSON.parse(header), -1, 0);
    document.querySelector("#json-body").appendChild(jsonViewerBody.getContainer());                            
    jsonViewerBody.showJSON(JSON.parse(body), -1, 0); 
       details();         
        });   
                     
function getData(id){
 return $(id).val();
}

function details() {
  var btn = document.getElementById("btnDetails");
  var x = document.getElementById("details");
  var btnTxt="Détails du jeton Pro Santé Connect";   
  if (x.style.visibility === "hidden") {
	   btn.textContent="Masquer le jeton"
    x.style.visibility = "visible";   
  } else {
    x.style.visibility = "hidden"; 
    if ( $( "#pageDAM" ).length ) {	 
    			btnTxt="Détails du jeton d'API";
     } 
    btn.textContent=btnTxt;
  }
}