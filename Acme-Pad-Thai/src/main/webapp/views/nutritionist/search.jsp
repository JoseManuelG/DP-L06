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

<form:form action="nutritionist/search.do" modelAttribute="stringForm" >

	
	<form:label path="text">
		<spring:message code="nutritionist.search.search" />:
	</form:label>
	<form:input path="text" />
	<form:errors cssClass="error" path="text" />
	<br />


	<input type="submit" name="Accept" value="<spring:message code="nutritionist.search.accept" />" />

	<br />
	
	<jstl:if test="${not empty nutritionists}">
		<display:table pagesize="5" class="displaytag" keepStatus="false" name="nutritionists" requestURI="nutritionist/search" id="row">
		
		<!-- Action links -->
		<spring:message code="nutritionist.view.title" var="viewTitleHeader" />
		<display:column title="${viewTitleHeader}">
				<spring:message code="nutritionist.title" />
		</display:column>
		</display:table>
	</jstl:if>


</form:form>