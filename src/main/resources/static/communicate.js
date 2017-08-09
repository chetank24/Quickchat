/**
 * 
 */

function getMessage() {
	
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo").innerHTML = this.responseText;
	    }
	  };
	  xhttp.open("GET", "/test", true);
	  xhttp.send();
}


function sendMessage() {
	var params = "message="+document.getElementById("message").value;
	var xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	     document.getElementById("demo").innerHTML = this.responseText;
	    }
	  };
	  xhttp.open("POST", "/test?"+params, true);
	  xhttp.send(params);
}