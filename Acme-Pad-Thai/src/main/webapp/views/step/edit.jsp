<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="${requestURI}" modelAttribute="step">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="number" />
	<form:hidden path="recipe" />
	<form:hidden path="stepHints" />
	
	
	<form:label path="description">
		<spring:message code="step.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="picture">
		<spring:message code="step.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors cssClass="error" path="picture" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="step.save" />" />&nbsp; 
	<jstl:if test="${step.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="step.delete" />" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="step.cancel" />"
		onclick="javascript:window.location.href='recipe/user/edit.do?recipeId=${urlID}'" />
	<br />
</form:form>
