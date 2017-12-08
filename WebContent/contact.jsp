<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Stock Prediction</title>
<link rel="stylesheet" type="text/css" href="css/style.css" title="style" />
</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<h1>
						<a href="index.html">Stock Prediction</a>
					</h1>
					<h2>Live, History, Predict...</h2>
				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<li><a href="index.jsp">Home</a></li>
					<li><a href="history.jsp">History</a></li>
					<li><a href="predict.jsp">Prediction</a></li>
					<li class="selected"><a href="contact.jsp">Contact Us</a></li>
				</ul>
			</div>
		</div>
		<div id="site_content">
			<div id="content">
				<!-- insert the page content here -->
				<h1>Contact Us</h1>
				<p>Below is an example of how a contact form might look with this template:</p>
				<form action="#" method="post">
					<div class="form_settings">
						<p>
							<span>Name</span><input class="contact" type="text" name="your_name" value="" />
						</p>
						<p>
							<span>Email Address</span><input class="contact" type="text" name="your_email" value="" />
						</p>
						<p>
							<span>Message</span>
							<textarea class="contact textarea" rows="8" cols="50" name="your_enquiry"></textarea>
						</p>
						<p style="padding-top: 15px">
							<span>&nbsp;</span><input class="submit" type="submit" name="contact_submitted" value="submit" />
						</p>
					</div>
				</form>
			</div>
		</div>
		<div id="footer">Developed By Vishal Patil.</div>
	</div>
</body>
</html>
