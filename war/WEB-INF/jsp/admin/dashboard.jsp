<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Admin Panel</title>
	<%@include file="/template/header.jsp"%>
	<script type='text/javascript' src='/js/admin/main.js'></script>
    <script type='text/javascript' src='/js/admin/dashboard.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/admin/dashboard.css" />
</head>
<body>
	<div class='container'>
	<jsp:include page="/template/userHeader" />
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span3">
					<jsp:include page="/admin/nav" />
				</div>
				<div class="span9">
					<div class='well'>
						<h1 class='topic red big'>Dashboard</h1>
						<hr />
					</div>
				</div>
			</div>
		</div>
    <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
