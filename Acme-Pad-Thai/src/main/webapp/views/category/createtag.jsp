<%--
 * search.jsp
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

<form:form action="category/administrator/createtag.do" modelAttribute="tagForm" >
	<form:hidden path="category" />
	<form:label path="text">
		<spring:message code="category.tag.create" />:
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br />


	<input type="submit" name="Accept" value="<spring:message code="category.accept" />" />

	<br />


</form:form>