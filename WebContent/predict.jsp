<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Stock Prediction</title>
<link rel="stylesheet" type="text/css" href="css/style.css" title="style" />
<script type="text/javascript" src="js/dygraph.js"></script>
<link rel="stylesheet" src="css/dygraph.css" />
<script type="text/javascript">
function analyseData()
{
	var tmp = document.getElementById("symbId");
	var symId = tmp.options[tmp.selectedIndex].value;
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "AnalyseServlet?sid="+symId, true);
	xhttp.send();
	xhttp.onreadystatechange = function() 
	{
		if(this.readyState == 4 && this.status == 200) 
		{
			document.getElementById("dataDiv1").innerHTML = this.responseText ;
		}
	};
}

function predictPrice()
{
	var tmp = document.getElementById("symbId");
	var symId = tmp.options[tmp.selectedIndex].value;
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "SMAServlet?sid="+symId, true);
	xhttp.send();
	xhttp.onreadystatechange = function() 
	{
		if(this.readyState == 4 && this.status == 200) 
		{
			//document.getElementById("dataDiv1").innerHTML = this.responseText ;
			g2 = new Dygraph(
				    document.getElementById("chartDiv"),
				    "analysis/"+symId+".csv", // path to CSV file
				    {
				    	title: 'Actual Closing and SMA calculated closing',				    	
				    	 ylabel: 'Price(Rs.)',
			             xlabel: 'Date (Ticks indicate the start of the indicated time period)',
				    }          // options
				  );
		}
	};
}
/* 
function getHistory(obj)
{
	window.open("history.jsp?id="+obj.id, "_self");
} */
</script>
</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<h1>
						<a href="index.html">Stock Prediction</span></a>
					</h1>
					<h2>Live, Analyze, Predict...</h2>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<li><a href="index.jsp">Home</a></li>
					<li><a href="history.jsp">History</a></li>
					<li class="selected"><a href="predict.jsp">Prediction</a></li>
					<li><a href="contact.jsp">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">
			<div id="content">
				<br> <br>
				<div id="formDiv">
					<table border="1">
						<tr>
							<td>Select Symbol-</td>
							<td><select id="symbId">
									<option value="PERSISTENT.BO">Persistent Limited</option>
									<option value="INFY.NS">Infosys</option>
									<option value="AP-U.TI">Apple</option>
									<option value="AD8.AX">Audi</option>
									<option value="MSFT">Microsoft</option>
									<option value="RELIANCE.NS">Reliance</option>
									<option value="TCS.NS">TCS</option>
									<option value="FB-U.TI">Facebook</option>
							</select></td>
						</tr>
						<tr>
							<td align="center"><input type="button" id="btn" value="Analyse Data" onclick="analyseData()"></td>
							<td align="center"><input type="button" id="btn" value="Predict Price" onclick="predictPrice()"></td>
						</tr>
					</table>
				</div>
				<div id="dataDiv1"></div>
				<div id="priceDiv"></div>
				<div id="chartDiv"></div>
				<br> <br>
			</div>
		</div>
		<div id="footer">Developed By Vishal Patil.</div>
	</div>
</body>
</html>
