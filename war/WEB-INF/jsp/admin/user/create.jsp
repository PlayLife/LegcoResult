<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Admin Panel</title>
	<%@include file="/template/header.jsp"%>
	<script type='text/javascript' src='/js/admin/main.js'></script>
    <script type='text/javascript' src='/js/admin/user/create.js'></script>
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
					<form action='/admin/user/create.json' method='POST' class='well form-horizontal' id='form_register'>
						<legend><h1 class='topic red big'>Create User</h1></legend>
						
						<!-- User Role -->
						<fieldset>
							<div class="control-group">
								<label class="control-label" for="tb_userRole">Role : </label>
								<div class="controls"><select name='s_userRole'><option value='ADMIN'>Admin</option><option value='USER'>User</option></select></div>
							</div>
						</fieldset>
						
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
							<button type="submit" class="btn btn-primary btn-large span3 offset8">Create</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
    <%@include file="/template/footer.jsp"%>
    </div>
</body>
</html>
