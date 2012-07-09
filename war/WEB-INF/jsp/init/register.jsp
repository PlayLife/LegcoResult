<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Init Account</title>
	<%@include file="/template/header.jsp"%> 
    <script type='text/javascript' src='/js/init/register.js'></script>
    
</head>
<body>
	<div class='container'>
    	<jsp:include page="/template/userHeader" />
    	
		<form action='/init/create.json' method='POST' class='well form-horizontal' id='form_register'>
			<a class='close pull-right' href='/'><spring:message code="init.cancel"/></a>
			<legend><h1 class='topic red big'><spring:message code="init.registerTitle"/></h1></legend>
			<!-- User ID -->
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="tb_email"><spring:message code="user.userId"/> : </label>
					<div class="controls"><input type="text" class="input-xlarge" id="tb_userId" value='${userId}' disabled/></div>
				</div>
			</fieldset>
			
			<!-- Email -->
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="tb_email"><spring:message code="user.email"/> : </label>
					<div class="controls"><input type="text" class="input-xlarge" id="tb_email" value='${email}' disabled/></div>
				</div>
			</fieldset>
						
			<!-- Username -->
			<fieldset>				
				<div class="control-group">
					<label class="control-label" for="tb_username"><spring:message code="user.username"/> : </label>
					<div class="controls"><input type="text" class="input-xlarge" id="tb_username" name='username' value='${username}'/></div>
				</div>
			</fieldset>
			
			<fieldset>
				<div id='div_error' class='span6 hide'>
				</div>
				<button type="submit" class="btn btn-primary btn-large span3 offset8"><spring:message code="init.go"/></button>
			</fieldset>
		</form>
		
		<div id='div_register_success' class='hide pull-center'>
			<h1 class='topic red big'><spring:message code="init.success"/></h1>
			<span><spring:message code="init.redirect"/></span>
		</div>
        <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
