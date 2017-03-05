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


<form:form action="masterclass/cook/edit.do"
	modelAttribute="masterClass">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="promoted" />
	<form:hidden path="cook" />
	<form:hidden path="attends" />
	<form:hidden path="learningMaterials" />
	
	
	<form:label path="title">
		<spring:message code="masterclass.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="masterclass.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="title" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="masterclass.save" />" />&nbsp; 
	<jstl:if test="${masterClass.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="masterclass.delete" />" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="masterclass.cancel" />"
		onclick="javascript:window.location.href='masterclass/listByCook.do'" />	<br />
</form:form>
