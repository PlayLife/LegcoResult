<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Error</title>
	<%@include file="/template/header.jsp"%> 
	<script type='text/javascript' src='/js/error/show.js'></script>
	<script>
	var s_log = '${s_log}';
	</script>
</head>
<body>
	<div class='container'>
		<jsp:include page="/template/userHeader" />
        <hr/>
        <div id='div_show'>
	        <h1 class='topic red big'>${displayMessage}</h1><button id='btn_report' class='btn btn-danger pull-right btn-large'>Report</button>
	        <div id='div_error' class='pull-right hide'></div>
	        <br /><br />
	        <hr />
	        <h1 class='topic blue medium'>Validation Error</h1>
	        <table class='table table-striped'>
	        	<th>Error Code</th><th>Message</th>
	        	<c:forEach var="ex" items="${validation}">
	        		<tr><td>${ex.errorCode}</td><td>${ex.msg}</td></tr>
	        	</c:forEach>
	        </table>
	        
			<hr />
	        <h1 class='topic blue medium'>Presentation Error</h1>
	        <table class='table table-striped'>
	        	<th>Error Code</th><th>Message</th>
	        	<c:forEach var="ex" items="${presentation}">
	        		<tr><td>${ex.errorCode}</td><td>${ex.msg}</td></tr>
	        	</c:forEach>
	        </table>
	        
	        <hr />
	        <h1 class='topic blue medium'>Logic Error</h1>
	        <table class='table table-striped'>
	        	<th>Error Code</th><th>Message</th>
	        	<c:forEach var="ex" items="${logic}">
	        		<tr><td>${ex.errorCode}</td><td>${ex.msg}</td></tr>
	        	</c:forEach>
	        </table>
	        
	        <hr />
	        <h1 class='topic blue medium'>Persistence Error</h1>
	        <table class='table table-striped'>
	        	<th>Error Code</th><th>Message</th>
	        	<c:forEach var="ex" items="${persistence}">
	        		<tr><td>${ex.errorCode}</td><td>${ex.msg}</td></tr>
	        	</c:forEach>
	        </table>
	        
	        <hr />
	        <h1 class='topic blue medium'>Exception</h1>
	        <table class='table table-striped'>
	        	<th>Message</th>
	        	<c:forEach var="ex" items="${exception}">
	        		<tr><td>${ex}</td></tr>
	        	</c:forEach>
	        </table>
        </div>
        
        <div id='div_success' class='hide pull-center'>
			<h1 class='topic red big'><spring:message code="error.success"/></h1>
			<span><spring:message code="error.redirect"/></span>
		</div>
        <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
