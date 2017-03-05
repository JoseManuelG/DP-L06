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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="nutritionists" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<spring:message code="nutritionist.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}" >
			<a href="nutritionist/view.do?nutritionistId=${row.id}">
				<spring:message	code="nutritionist.view" />
			</a>
	</display:column>		
	
	
	<!-- Attributes -->

	<spring:message code="nutritionist.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="nutritionist.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" sortable="true" />

	<spring:message code="nutritionist.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="nutritionist.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />

	<spring:message code="nutritionist.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"	sortable="false" />
	
	<spring:message code="nutritionist.nutritionistname" var="usernameHeader" />
	<display:column property="userAccount.username" title="${usernameHeader}"	sortable="true" />

</display:table>
