<%--
 * list.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="services.CategoryService"%>
<%@page import="services.RecipeService"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="recipes" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${requestURI == 'recipe/user/myRecipes.do'}">
			<spring:message code="recipe.edit.recipe" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="recipe/user/edit.do?recipeId=${row.id}">
				<spring:message	code="recipe.edit" />
				</a>
				
			</display:column>
	</jstl:if>
	</security:authorize>
	<spring:message code="recipe.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="recipe/view.do?recipeId=${row.id}">
				<spring:message	code="recipe.view" />
			</a>
	</display:column>
<jstl:if test="${requestURI == 'recipe/list.do'}">
    <spring:message code="recipe.view.user" var="viewTitleHeader" />
    <display:column title="${viewTitleHeader}">
      <a href="user/view.do?userId=${row.user.id}">
      <spring:message  code="recipe.view" />
    </a>
      
    </display:column>
  </jstl:if>

	<jstl:if test="${booleanlistcontest}">
			<spring:message code="recipe.view.contest" var="contestHeader" />
			<display:column title="${contestHeader}">
				<a href="contest/view.do?contestId=${row.qualifieds[0].contest.id}">
				<spring:message	code="recipe.view.contest" />
				</a>
				
			</display:column>
			
			
				<spring:message code="recipe.view.contest" var="contestHeader" />
			<display:column title="${contestHeader}">
				${row.qualifieds[0].contest.tittle}
				
			</display:column>
	</jstl:if>
  	
  	<jstl:set var="sort"  value="true"/>
	<jstl:if test="${requestURI == 'recipe/latestRecipes.do'}">
		<jstl:set var="sort"  value="false"/>
	</jstl:if>
	
	
	
	<!-- Attributes -->

	<spring:message code="recipe.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="${sort}" />
	
	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="${sort}" />

	<spring:message code="recipe.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" sortable="${sort}" />

	<spring:message code="recipe.lastUpdate" var="lastUpdateHeader" />
	<display:column property="lastUpdate" title="${lastUpdateHeader}" sortable="${sort}" />
	
	<spring:message code="recipe.category" var="categoryHeader" />
	<jstl:if test="${row.belongs != '[]'}">
		<display:column title="${categoryHeader}" sortable="${sort}">
			<jstl:forEach items="${row.belongs}" var="belong">
				<jstl:out value="${belong.category.name}"/>
			</jstl:forEach>
		</display:column>
	</jstl:if>
</display:table>

<security:authorize access="hasRole('USER')">
	<jstl:if test="${requestURI != 'recipe/latestRecipes.do'}">
		<a href="recipe/user/create.do"><spring:message  code="recipe.create" /></a>
		<br>
	</jstl:if>
</security:authorize>

