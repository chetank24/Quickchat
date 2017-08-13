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
	    		document.getElementById("you").innerHTML=this.responseText;
	    		tempmsg=this.responseText;
	    		}
	    		
	    	
	    	 
	    }
	  };
	  xhttp.open("GET", "/getm", true);
	  xhttp.send();
}


function sendMessage() {
	var params = "message="+document.getElementById("message").value;
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	     //document.getElementById("demo").innerHTML = this.responseText;
	    	//tempmsg=tempmsg+document.getElementById("message").value+"_";
	    	document.getElementById("me").innerHTML=document.getElementById("message").value; 
	    	document.getElementById("message").value="";
	    }
	  };
	  xhttp.open("POST", "/setm?"+params, true);
	  xhttp.send(params);
}


function setup() {
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("status").innerHTML = this.responseText;
	    }
	  };
	  xhttp.open("GET", "/setup", true);
	  xhttp.send();
}

setInterval(function(){ getMessage(); }, 4000);
	setInterval(getMessage(), 9000);
