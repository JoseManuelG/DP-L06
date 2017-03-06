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
	name="cooks" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<spring:message code="cook.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}" >
			<a href="cook/view.do?cookId=${row.id}">
				<spring:message	code="cook.view" />
			</a>
	</display:column>		
	
	
	<!-- Attributes -->

	<spring:message code="cook.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />
	
	<spring:message code="cook.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" />

	<spring:message code="cook.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}"  />

	<spring:message code="cook.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}"  />

	<spring:message code="cook.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"	/>
	
	<spring:message code="cook.cookname" var="cooknameHeader" />
	<display:column property="userAccount.username" title="${cooknameHeader}" />

</display:table>
