<%@page import="java.util.HashMap"%>
<%@page import="models.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stock Data</title>

</head>
<body>
<div id="formDiv">
<table border="1">
<tr>
<td>Enter Symbol-</td>
<td><input type="text" id="symbol"></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="button" id="btn" value="Fetch Data" onclick="call()"></td>
</tr>
</table>
</div>
<dir id="timeDive"></dir>
<div id="dataDiv"></div>
</body>
</html>
<script type="text/javascript">
function fetchData()
{
	var timeLeft = 15;
	var elem = document.getElementById('timeDive');
	var timerId = setInterval(countdown, 1000);

	function countdown() {
	  if (timeLeft == 0) {
	    clearTimeout(timerId);
	    call();
	  } else {
	    elem.innerHTML = 'Refreshing in '+timeLeft + ' seconds';
	    timeLeft--;
	  }
	}	
}
function call()
{
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "FetchDataServlet", true);
	xhttp.send();
	xhttp.onreadystatechange = function() 
	{
		if(this.readyState == 4 && this.status == 200) 
		{
			var data = JSON.parse(this.responseText);
			console.log(data);
			var table = "";
			table += "<table border='1'padding='0'><tr><th colspan='5'>Live Data for "+data.liveData.symbol+"</th></tr>";
			table += "<tr><th>Price</th><th>Opening Price</th><th>Ask</th><th>Bid</th><th>Previous Close</th></tr>";
			table += "<td>"+data.liveData.price+"</td>";
			table += "<td>"+data.liveData.openingPrice+"</td>";
			table += "<td>"+data.liveData.ask+"</td>";
			table += "<td>"+data.liveData.bid+"</td>";
			table += "<td>"+data.liveData.preClose+"</td></tr>"; 
			table += "<tr><td colspan='5' align='right'><input type='button' id='"+data.liveData.symbol+"'value='Get History' onclick='getHistory()'></td></tr></table>";
			document.getElementById("dataDiv").innerHTML = table ;
			fetchData();
		}
	};
}

function getHistory()
{
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "FetchHistoryServlet", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	var id = this.id;
	xhttp.send("id="+id);
	xhttp.onreadystatechange = function() 
	{
		if(this.readyState == 4 && this.status == 200) 
		{
			var data = JSON.parse(this.responseText);
			console.log(data);
			/* var table = "";
			table += "<table border='1'padding='0'><tr><th colspan='5'>Live Data for "+data.liveData.symbol+"</th></tr>";
			table += "<tr><th>Price</th><th>Opening Price</th><th>Ask</th><th>Bid</th><th>Previous Close</th></tr>";
			table += "<td>"+data.liveData.price+"</td>";
			table += "<td>"+data.liveData.openingPrice+"</td>";
			table += "<td>"+data.liveData.ask+"</td>";
			table += "<td>"+data.liveData.bid+"</td>";
			table += "<td>"+data.liveData.preClose+"</td></tr>"; 
			table += "<tr><td colspan='5' align='right'><input name='history' type='button' id='"+data.liveData.symbol+"'value='Get History' onclick='getHistory()'></td></tr></table>";
			document.getElementById("dataDiv").innerHTML = table ;
			fetchData(); */
		}
	};
}
</script>