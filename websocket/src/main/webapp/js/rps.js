var url = "ws://localhost:61614/stomp";
var un="guest",pw="guest";

var client,src,dest;
var hasUserPicked,hasOpponentPicked=false;
var opponentsBtns = '<button id="opponentRockBtn" name="opponentRock" disabled="disabled">Rock</button>'+
'<button id="opponentPaperBtn" name="opponentPaper" disabled="disabled">Paper</button>'+
'<button id="opponentScissorsBtn" name="opponentScissors" disabled="disabled">Scissors</button>';
var opponentsPick;
var rockBtn,paperBtn,scissorsBtn;

$(document).ready(function(){
	if(!window.WebSocket){
		var msg = "Your brower does not have Websocket support.This example will not work properly.";
		$("#nameFields").css("visibility","hidden");
		$("#instructions").css("visibility","visiable");
		$("#instructions").html(msg);
	}
});

var startGame = function(){
	$("#myName").attr("disabled","disabled");
	$("#opponentName").attr("disabled","disabled");
	$("#goBtn").attr("disabled","disabled");
	$("#instructions").css("visibility","visible");
	$("#buttons").css("visibility","visible");
	//Quenues are named after the players
	dest = "/queue/"+$("#opponentName").val();
	src= "/queue/"+$("#myName").val();
	//建立stomp连接
	connect();
}

//构建connection
var connect = function(){
	client = Stomp.client(url);
	client.connect(un,pw,onconnect,onerror);
}

//回调函数onconnect
var onconnect = function(){
	console.log("connected to "+url);
	client.subscribe(src,function(message){
		console.log("message received: "+message.body);
		hasOpponentPicked = true;
		if(!hasUserPicked){
			$("#opponentsButtons").css("visibility","hidden");
			$("#instructions").html("<p>Your opponent is waiting for you.Make your move!</p>");
		}else{
			$("#instructions").html("<p>Results:</p>");
			client.disconnect(function(){
				console.log("Disconnected...");
			});
		}
		$("#opponentsButtons").html(opponentsBtns);
		switch(message.body){
			case "rock":
				opponentsPick = "#opponentRockBtn";
				break;
			case "paper":
				opponentsPick = "#opponentPaperBtn";
				break;
			case "scissors":
				opponentsPick = "#opponentScissorsBtn";
		}
		$(opponentsPick).css("background-color","yellow");
	});
	console.log("subscribed to " + src);
}

var onerror = function(error){
	console.log(error);
}

var buttonClicked = function(btn){
	client.send(dest,null,btn.name);
	hasUserPicked = true;
	console.log("message sent: "+btn.name);
	$("#"+btn.id).css("background-color","orange");
	$("#rockBtn").attr("disabled","disabled");
	$("#paperBtn").attr("disabled","disabled");
	$("#scissorsBtn").attr("disabled","disabled");
	if(hasOpponentPicked){
		$("#opponentsButtons").css("visibility","visible");
		$("#instructions").html("<p>Results:</p>");
		client.disconnect(function(){
			onerror = function(){};
			console.log("Disconnected...");
		});
	}else{
		$("#instructions").html("<p>Waiting for opponent...</p>");
	}
};