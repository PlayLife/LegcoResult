<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Google Login Fail</title>
	<%@include file="/template/header.jsp"%> 
</head>
<body>
	<div class='container'>
		<jsp:include page="/template/userHeader" />
		
		<div class='well'>
			
			<h1 class='topic red big'><spring:message code="init.googleFail"/></h1>
			
		</div>
		
        <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>