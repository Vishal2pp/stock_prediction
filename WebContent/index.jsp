<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Stock Prediction</title>
<link rel="stylesheet" type="text/css" href="css/style.css" title="style" />
<style>
table, th, td {
	border: 2px solid black;
	border-collapse: collapse;
}
</style>

<script type="text/javascript">
function fetchData()
{
	var timeLeft = 15;
	//var elem = document.getElementById('timeDive');
	var timerId = setInterval(countdown, 1000);

	function countdown() {
	  if (timeLeft == 0) {
	    clearTimeout(timerId);
	    call();
	  } else {
	   // elem.innerHTML = 'Refreshing in '+timeLeft + ' seconds';
	    timeLeft--;
	  }
	}	
}
function call()
{
	var tmp = document.getElementById("symbId");
	var symId = tmp.options[tmp.selectedIndex].value;
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "FetchDataServlet?sid="+symId, true);
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
			table += "<tr><td colspan='5' align='right'><input type='button' id='"+data.liveData.symbol+"'value='Get History' onclick='getHistory(this)'/><div id='timeDiv'></div></td></tr></table>";
			document.getElementById("dataDiv").innerHTML = table ;
			fetchData();
		}
	};
}

function getHistory(obj)
{
	window.open("history.jsp?id="+obj.id, "_self");
}
</script>
</head>
<body onload="call()">
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<h1>
						<a href="index.html">Stock Prediction</span></a>
					</h1>
					<h2>Live, History, Predict...</h2>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<li class="selected"><a href="index.jsp">Home</a></li>
					<li><a href="history.jsp">History</a></li>
					<li><a href="predict.jsp">Prediction</a></li>
					<li><a href="contact.jsp">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">

			<div id="content"><br><br>
				<div id="formDiv">
					<table border="1">
						<tr>
							<td>Select Symbol-</td>
							<td>
								<select id="symbId">
								  <option value="PERSISTENT.BO">Persistent Limited</option>
								  <option value="INFY.NS">Infosys</option>
								  <option value="AP-U.TI">Apple</option>
								  <option value="AD8.AX">Audi</option>
								  <option value="MSFT">Microsoft</option>
								  <option value="RELIANCE.NS">Reliance</option>
								  <option value="TCS.NS">TCS</option>
								  <option value="FB-U.TI">Facebook</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="button" id="btn" value="Fetch Data" onclick="call()"></td>
						</tr>
					</table>
				</div>
				<div id="dataDiv"></div>
				<br> <br>
			</div>
		</div>
		<div id="footer">Developed By Vishal Patil.</div>
	</div>
</body>
</html>