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
	name="actors" requestURI="contest/list.do" id="row">
	
	<!-- Action links -->

	<display:column>
			<a href="contest/view.do?contestId=${row.id}">
				<spring:message	code="contest/view" />
			</a>
	</display:column>
	
	<display:column>
			<a href="qualified/user/create.do?contestId=${row.id}">
				<spring:message	code="contest/register" />
			</a>
	</display:column>			
	
	
	<!-- Attributes -->

	<spring:message code="user.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="user.surname" var="surnameHeader" />
	<display:column property="surname" title="${nameHeader}" sortable="true" />

	<spring:message code="user.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />

	<spring:message code="user.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />

	<spring:message code="user.address" var="addressHeader" />
	<display:column property="address" title="${addressHeader}"	sortable="false" />

</display:table>
