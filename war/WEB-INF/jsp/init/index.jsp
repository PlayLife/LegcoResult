<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Legco Result</title>
<%@include file="/template/header.jsp"%>
</head>
<body>
	<div class='container'>
		<jsp:include page="/template/userHeader" />

		<div class='well'>
			<a class='close pull-right' href='/'><spring:message
					code="init.cancel" /></a>
			<h1 class='topic red big'>
				<spring:message code="init.title" />
			</h1>
			<hr />
			<a href='${loginUrl}' class="btn btn-primary btn-large span3 offset8"><spring:message
					code="init.go" /></a> <br />
			<br />
		</div>

		<%@include file="/template/footer.jsp"%>
	</div>
</body>
</html>