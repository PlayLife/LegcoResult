<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Cube Drawing</title>
	<%@include file="/template/header.jsp"%> 
    <script type='text/javascript' src='/js/home.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/home.css" />
</head>
<body>
	<div class='container'>
		<jsp:include page="/template/userHeader" />
        <hr/>
        <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
