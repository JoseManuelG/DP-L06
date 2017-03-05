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

<img  height="200px" width="250px" alt="La imagen no está disponible" src="<jstl:out value="${category.picture}"/>" />
<br>
<spring:message  code="category.name" />: <jstl:out value="${category.name}" />
<br>
<spring:message  code="category.description" />: <jstl:out value="${category.description}"/>
<br>

<spring:message  code="category.tags" />:
<br>

<br>

<table>
<thead>
 <tr>
 	<spring:message code="category.tags" var="tagHeader" />
    <th>${tagHeader}</th>
  </tr>
 </thead>
	<jstl:forEach var="tag" items="${tags}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<td>
			<jstl:out value="${tag}"/>
		</td>
	</tr>
	</jstl:forEach>
</table>
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="subCategory" requestURI="category/view.do" uid="subCategory">
	
	<!-- Attributes -->

	<spring:message code="category.subCategory" var="subCategoryHeader" />
	<display:column property="name" title="${subCategoryHeader}" sortable="true" />
	<display:column>
			<a href="category/view.do?categoryId=${subCategory.id}">
				<spring:message	code="category.subcategory.view" />
			</a>
		</display:column>		

	</display:table>

	<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="recipes" requestURI="category/view.do" uid="recipe">
	
	<!-- Attributes -->

	<spring:message code="category.recipe" var="recipeHeader" />
	<display:column property="title" title="${recipeHeader}" sortable="true" />
	
		<display:column>
			<a href="recipe/view.do?recipeId=${recipe.id}">
				<spring:message	code="category.view.recipe" />
			</a>
		</display:column>		

</display:table>