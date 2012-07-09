<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Register</title>
	<%@include file="/template/header.jsp"%> 
    <script type='text/javascript' src='/js/user/register.js'></script>
    <link rel="stylesheet" type="text/css" href="/css/user/register.css" />
</head>
<body>
	<div class='container'>
    	<jsp:include page="/template/userHeader" />
    	
		<form action='/user/create.json' method='POST' class='well form-horizontal' id='form_register'>
			<a class='close pull-right' href='/'><spring:message code="register.cancel"/></a>
			<legend><h1 class='topic red big'><spring:message code="register.title"/></h1></legend>
			<!-- Email -->
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="tb_email"><spring:message code="user.email"/> : </label>
					<div class="controls"><input type="text" class="input-xlarge" id="tb_email" name='email' placeholder="<spring:message code="user.email"/>"/></div>
				</div>
			</fieldset>
			
			<!-- Password -->
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="tb_password"><spring:message code="user.password"/> : </label>
					<div class="controls"><input type="password" class="input-xlarge" id="tb_password" name='password' placeholder="<spring:message code="user.password"/>"/></div>
				</div>
			</fieldset>
			
			<!-- Confirm Password -->
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="tb_confirmpassword"><spring:message code="register.confirmPassword"/> : </label>
					<div class="controls"><input type="password" class="input-xlarge" id="tb_confirmPassword" placeholder="<spring:message code="register.confirmPassword"/>"/></div>
				</div>
			</fieldset>
			
			<!-- Username -->
			<fieldset>				
				<div class="control-group">
					<label class="control-label" for="tb_username"><spring:message code="user.username"/> : </label>
					<div class="controls"><input type="text" class="input-xlarge" id="tb_username" name='username' placeholder="<spring:message code="user.username"/>"/></div>
				</div>
			</fieldset>
			
			<fieldset>
				<div id='div_error' class='span6 hide'>
				</div>
				<button type="submit" class="btn btn-primary btn-large span3 offset8"><spring:message code="register.register"/></button>
			</fieldset>
		</form>
		
		<div id='div_register_success' class='hide pull-center'>
			<h1 class='topic red big'><spring:message code="register.success"/></h1>
			<span><spring:message code="register.redirect"/></span>
		</div>
        <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
