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
	name="recipes" requestURI="category/listrecipefinal.do" id="row">
	
	<!-- Action links -->
	<spring:message code="category.edit" var="categoryEditHeader" />
	
	<display:column title="${categoryEditHeader}">
			<a href="recipe/view.do?recipeId=${row.id}">
				<spring:message	code="category.view.recipe" />
			</a>
	</display:column>
	
	
	<!-- Attributes -->
	
	
	
	<spring:message code="category.name" var="recipeTitleHeader" />
	<display:column property="recipe.title" title="${recipeTitleHeader}" sortable="false" />

</display:table>
