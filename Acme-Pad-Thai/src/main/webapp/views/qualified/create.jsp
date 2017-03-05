<%--
 * action-1.jsp
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

	<jstl:out value="${contest.tittle }"></jstl:out>
	<br>
	<br>
<form:form action="qualified/user/create.do" modelAttribute="qualified">
	

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="contest" />
	<form:hidden path="winner" />
	
	<form:select id="recipesDropdown" path="recipe">
	<form:option value="0" label="----"/>
	<form:options items="${recipes}" itemValue="id" itemLabel="title" />
	</form:select>

	<input type="submit" name="save"value="<spring:message code="qualified.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="qualified.cancel" />"
		onclick=" javascript:window.location.href='contest/list.do'" />
	<br />

</form:form>
