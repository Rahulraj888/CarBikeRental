<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    request.setAttribute("pageTitle", "About Us - Car & Bike Rental Service");
%>
<jsp:include page="header.jsp" />
<main class="container my-4 flex-grow-1">
	<h1>About Car & Bike Rental Service</h1>
	<div class="card p-3 my-3">
		<h2>Our Story</h2>
		<p>Founded in 2025, our company has grown into one of the most
			trusted car and bike rental services in the region. Our mission is to
			deliver reliable, affordable, and efficient transportation solutions.</p>
		<h2>Our Mission</h2>
		<p>To provide exceptional rental services with a focus on customer
			satisfaction, safety, and innovation.</p>
		<h2>Our Team</h2>
		<p>We are a team of industry experts dedicated to offering the
			best rental experience.</p>
	</div>

	<jsp:include page="footer.jsp" />