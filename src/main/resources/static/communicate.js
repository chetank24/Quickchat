/**
 * 
 */

var tempmsg = "";
var connectionStatus = "Connecting...";

function getMessage() {

	//	alert("getting messages");
	//alert(connectionStatus);
	if (connectionStatus == "Connected") {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				if (tempmsg != this.responseText) {
					//document.getElementById("you").innerHTML=this.responseText;
					var para = document.createElement("P");
					var t = document.createTextNode("Alien : "+this.responseText);
					para.appendChild(t);
					document.getElementById("you").appendChild(para);
					tempmsg = this.responseText;

					var objDiv = document.getElementById("you");
					objDiv.scrollTop = objDiv.scrollHeight;
				}

			}
		};
		xhttp.open("GET", "/getm", true);
		xhttp.send();

	}
}

function sendMessage() {
	var params = "message=" + document.getElementById("messageInput").value;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {

			var para = document.createElement("P");
			//para.style.bottom=0px;
			var t = document.createTextNode("Me    : "+document
					.getElementById("messageInput").value);
			para.appendChild(t);
			document.getElementById("you").appendChild(para);

			document.getElementById("messageInput").value = "";

			var objDiv = document.getElementById("you");
			objDiv.scrollTop = objDiv.scrollHeight;
		}
	};
	xhttp.open("POST", "/setm?" + params, true);
	xhttp.send(params);
}

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

function cleanUp() {
	document.getElementById("status").innerHTML = "Cleaning Up..";

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("status").innerHTML = this.responseText;
			connectionStatus = this.responseText;
		}
	};
	xhttp.open("GET", "/cleanUp", true);
	xhttp.send();

}


function verifyConnection() {
	
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("status").innerHTML = this.responseText;
				connectionStatus = this.responseText;
			}
		};
		xhttp.open("GET", "/verifyConnection", true);
		xhttp.send();
	
	
}
setInterval(function() {
	getMessage();
}, 2000);

setInterval(function() {
	verifyConnection();
}, 3000);



function runScript(e) {
	if (e.keyCode == 13) {
		sendMessage();
		return false;
	}
}
