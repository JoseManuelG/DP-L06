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

<form:form action="${requestURI}" modelAttribute="categoryForm">
	
	<form:hidden path="categoryId" />
<form:label path="category">
			<spring:message code="category.recipe.catalogue" />:
	</form:label>
		<form:select id="categoryDropdown" path="category">
			<form:option value="0" label="----"/>
			<form:options items="${categories}" itemValue="id" itemLabel="name"/>
			<form:errors cssClass="error" path="category" />
			<br />
		</form:select>
	<br />
	<input type="submit" name="accept"
		value="<spring:message code="category.accept" />" />&nbsp;
</form:form>



<display:table pagesize="5" class="displaytag" keepStatus="true"
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
	
	<!-- Attributes -->

	<spring:message code="recipe.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="true" />
	
	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="recipe.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" sortable="true" />

	<spring:message code="recipe.lastUpdate" var="lastUpdateHeader" />
	<display:column property="lastUpdate" title="${lastUpdateHeader}" sortable="true" />
	
	<spring:message code="recipe.category" var="categoryHeader" />
	<jstl:if test="${row.belongs != '[]'}">
		<display:column title="${categoryHeader}" sortable="true">
			<jstl:forEach items="${row.belongs}" var="belong">
				<jstl:out value="${belong.category.name}"/>
			</jstl:forEach>
		</display:column>
	</jstl:if>
</display:table>