<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<ul class="nav nav-list">
  <li><a href="/admin/dashboard"><i class="icon-hdd"></i> <spring:message code="admin.nav.dashboard"/></a></li>
  <li class="nav-header"><spring:message code="admin.nav.userAdmin"/></li>
  <li><a href="/admin/user/list"><i class="icon-user"></i><spring:message code="admin.nav.userList"/></a></li>
  <li><a href="/admin/user/create"><i class="icon-plus-sign"></i><spring:message code="admin.nav.createUser"/></a></li>
  <li class="nav-header"><spring:message code="admin.nav.errorAdmin"/></li>  
  <li><a href="/admin/error/report"><i class="icon-file"></i><spring:message code="admin.nav.errorReport"/></a></li>
</ul>