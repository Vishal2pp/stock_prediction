<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Stock Prediction</title>
<link rel="stylesheet" type="text/css" href="css/style.css" title="style" />
</head>

<body onload="getHistory()">
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
					<li><a href="index.jsp">Home</a></li>
					<li class="selected"><a href="history.jsp">History</a></li>
					<li><a href="predict.jsp">Prediction</a></li>
					<li><a href="contact.jsp">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">
			<div id="content">
				<div id="historyDiv"></div>
			</div>
		</div>
		<div id="footer">Developed By Vishal Patil.</div>
	</div>
</body>
<script>
function getHistory()
{
var xhttp = new XMLHttpRequest();
xhttp.open("POST", "FetchHistoryServlet", true);
xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
var id = "<%=request.getParameter("id")%>";
xhttp.send("id="+id);
xhttp.onreadystatechange = function() 
{
	if(this.readyState == 4 && this.status == 200) 
	{
		if(this.responseText == "No Id")
		{
			document.getElementById("historyDiv").innerHTML = "No ID is selected to show history";
		}
		else
		{
			var data = JSON.parse(this.responseText);
			console.log(data);
			var dataArr = data["d"];
			var tb = "<table border='1'padding='0'><tr><th colspan='7'>History Data for "+dataArr[0].symbol+"</th></tr>";
			tb += "<tr><th>Sr.No</th><th>Date</th><th>High</th><th>Low</th><th>Open</th><th>Close</th><th>Volume</th><th>Amount Change</th><th>Percent Change</th></tr>";
			for(var i = 0; i < dataArr.length; i++)
			{
				
				tb += "<td>"+(i+1)+"</td>";
				tb += "<td>"+dataArr[i].date+"</td>";
				tb += "<td>"+dataArr[i].high+"</td>";
				tb += "<td>"+dataArr[i].low+"</td>";
				tb += "<td>"+dataArr[i].open+"</td>";
				tb += "<td>"+dataArr[i].close+"</td>";
				tb += "<td>"+dataArr[i].volume+"</td>";
				tb += "<td>"+dataArr[i].amt_change+"</td>";
				tb += "<td>"+dataArr[i].per_change+"</td></tr>";
			}
			tb += "</table>";
			document.getElementById("historyDiv").innerHTML = tb ;
		}
	}
};
}
</script>
</html>
