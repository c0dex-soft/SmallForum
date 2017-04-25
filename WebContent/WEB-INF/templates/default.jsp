<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/static/images/smallforum-icon.png" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<tiles:insertAttribute name="css-main"></tiles:insertAttribute>
<tiles:insertAttribute name="css-login"></tiles:insertAttribute>
<tiles:insertAttribute name="css-topics"></tiles:insertAttribute>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<tiles:insertAttribute name="script"></tiles:insertAttribute>
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
</head>
<body>
	<div class="header">
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>
	<div class="content">
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>
<!-- 	<hr /> -->
	<div class="footer">
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</div>
</body>
</html>