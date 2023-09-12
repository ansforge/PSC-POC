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
//     var x = document.getElementById("details");
//     x.style.visibility = "hidden";
       details();         
        });   
                     
function getData(id){
 return $(id).val();
}

function details() {
  var btn = document.getElementById("btnDetails");
  var x = document.getElementById("details");
  if (x.style.visibility === "hidden") {
//	  alert("Les informations qui vont être affichées sont remontées dans le navigateur uniquement à titre de démonstration. En production, elles ne doivent pas sortir pas du proxy (\'Relying Party\')")
	   btn.textContent="Masquer des détails techniques"
    x.style.visibility = "visible";
//    document.querySelector("#json-header").appendChild(jsonViewerHeader.getContainer());                           
//    jsonViewerHeader.showJSON(JSON.parse(header), -1, -1);
//    document.querySelector("#json-body").appendChild(jsonViewerBody.getContainer());                            
//    jsonViewerBody.showJSON(JSON.parse(body), -1, -1); 
   
  } else {
    x.style.visibility = "hidden";
    btn.textContent="Voir des détails techniques"
  }
}