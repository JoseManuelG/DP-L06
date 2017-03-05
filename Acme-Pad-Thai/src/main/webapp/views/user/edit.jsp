 <%--
 * login.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p><spring:message code="security.register" /></p>
<form:form action="${requestURI}" modelAttribute="actorForm">
	
	<form:hidden path="typeOfActor" />
    	
	<br />
    <form:label path="username">
		<spring:message code="security.username" />
	</form:label>
	<form:input path="username" />	
	<form:errors class="error" path="username" />
	<br />

	<form:label path="password">
		<spring:message code="security.password" />
	</form:label>
	<form:password path="password" />	
	<form:errors class="error" path="password" />
	<br />
	
	<form:label path="name">
		<spring:message code="security.register.name" />
	</form:label>
	<form:input path="name" />	
	<form:errors class="error" path="name" />
	<br />
	
	<form:label path="surname">
		<spring:message code="security.register.surname" />
	</form:label>
	<form:input path="surname" />	
	<form:errors class="error" path="surname" />
	<br />
	
	<form:label path="email">
		<spring:message code="security.register.email" />
	</form:label>
	<form:input path="email" />	
	<form:errors class="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="security.register.phone" />
	</form:label>
	<form:input path="phone" />	
	<form:errors class="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="security.register.addres" />
	</form:label>
	<form:input path="address" />	
	<form:errors class="error" path="address" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="security.register.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="security.register.cancel" />"
		onclick=" javascript:window.location.href='${typeActor}/view.do'" />
	<br />
	
</form:form>