/**
 * 
 */

var tempmsg="";
var myMsg="";



function getMessage() {
	
//	alert("getting messages");
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	     
	    	if(tempmsg!=this.responseText)
	    		{
	    		//document.getElementById("you").innerHTML=this.responseText;
	    		var para = document.createElement("P");
	    		var t = document.createTextNode(this.responseText);
	    		para.appendChild(t); 
	    		document.getElementById("you").appendChild(para);
	    		tempmsg=this.responseText;
	    		
	    		var objDiv = document.getElementById("you");
	    		objDiv.scrollTop = objDiv.scrollHeight;
	    		}
	    		
	    	
	    	 
	    }
	  };
	  xhttp.open("GET", "/getm", true);
	  xhttp.send();
}


function sendMessage() {
	var params = "message="+document.getElementById("messageInput").value;
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	        	
	    	var para = document.createElement("P");
	    	//para.style.bottom=0px;
    		var t = document.createTextNode(document.getElementById("messageInput").value);
    		para.appendChild(t); 
    		document.getElementById("you").appendChild(para);
	    	
	    	document.getElementById("messageInput").value="";
	    	
	    	var objDiv = document.getElementById("you");
    		objDiv.scrollTop = objDiv.scrollHeight;
	    }
	  };
	  xhttp.open("POST", "/setm?"+params, true);
	  xhttp.send(params);
}

var connectionStatus="Connecting";

function setup() {
	document.getElementById("status").innerHTML = connectionStatus;
	if (connectionStatus != "Connected") {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("status").innerHTML = this.responseText;
				connectionStatus = this.responseText;
			}
		};
		xhttp.open("GET", "/setup", true);
		xhttp.send();
	}
}

setInterval(function(){ getMessage(); }, 4000);


function runScript(e) {
    if (e.keyCode == 13) {
    	sendMessage();
        return false;
    }
}
