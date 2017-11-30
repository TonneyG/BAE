<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Drop Image Here</title>
</head>
<script>
	var ws = new WebSocket("ws://echo.websocket.org/echo");
	ws.onopen = function(){
		console.log("open");
	}
	ws.onmessage = function(e){
		var blob = e.data;
		console.log("message:"+blob.size+"bytes");
		if(window.webkitURL){
			URL = webkitURL;
		}
		var uri = URL.createObjectURL(blob);
		var img = document.createElement("img");
		img.src = uri;
		document.body.appendChild(img);
	}
	//handle drop event
	document.ondrop = function(e){
		document.body.style.backgroundColor = "#fff";
		try{
			e.preventDefault();
			handleFileDrop(e.dataTransfer.files[0]);
			return false;
		}catch(err){
			console.log(err);
		}
	}
	
	//provide visual feedback for the drop area
	document.ondragover = function(e){
		e.preventDefault();
		document.body.style.background = "#6fff41";
	}
	
	document.ondragleave = function(){
		document.body.style.background = "#fff";
	}
	
	function handleFileDrop(file){
		var reader = new FileReader();
		reader.readAsArrayBuffer(file);
		reader.onload = function(){
			console.log("sending:"+file.name);
			ws.send(reader.result);
		}
	}
	
</script>
<body>
	<h1>WebSocket Image Drop</h1>
</body>
</html>