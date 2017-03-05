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
	name="categories" requestURI="category/list.do" id="row">
	
	<!-- Action links -->
	<spring:message code="category.edit" var="categoryEditHeader" />
		<security:authorize access="hasRole('ADMIN')">
		<display:column title="${categoryEditHeader}">
			<a href="category/administrator/edit.do?categoryId=${row.id}">
				<spring:message	code="category.edit" />
			</a>
	</display:column>
	</security:authorize>
	<display:column>
			<a href="category/view.do?categoryId=${row.id}">
				<spring:message	code="category.view" />
			</a>
	</display:column>
		
	
	
	<!-- Attributes -->
	
	
	
	<spring:message code="category.name" var="categoryTitleHeader" />
	<display:column property="name" title="${categoryTitleHeader}" sortable="false" />

</display:table>


	<security:authorize access="hasRole('ADMIN')">
		<a href="category/administrator/create.do"><spring:message  code="category.create" /></a>
		<br>
	</security:authorize>
