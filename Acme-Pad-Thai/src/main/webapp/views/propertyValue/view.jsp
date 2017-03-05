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



<spring:message  code="propertyValue.property" />: <jstl:out value="${propertyValue.property}" />
<br>
<spring:message  code="propertyValue.Recipe" />: <jstl:out value="${recipe.ticker}"/>
<br>
<spring:message  code="recipe.summary" />: <jstl:out value="${recipe.summary}"/>
<br>
<spring:message  code="recipe.authorMoment" />: <jstl:out value="${recipe.authorMoment}"/>
<br>
<spring:message  code="recipe.lastUpdate" />: <jstl:out value="${recipe.lastUpdate}"/>
<br>
<spring:message  code="recipe.summary" />: <jstl:out value="${recipe.summary}"/>
<br>
<spring:message  code="recipe.pictures" />:
<br>
<jstl:forEach var="picture" items="${pictures}">
	<img  height="200px" width="250px" alt="La imagen no está disponible" src="<jstl:out value="${picture}"/>" />
</jstl:forEach>
<br>
<spring:message  code="recipe.isCopy" />: <jstl:out value="${recipe.isCopy}"/>
<br>
<spring:message  code="recipe.parentTicker" />: <jstl:out value="${recipe.parentTicker}"/>
<br>
<spring:message  code="recipe.user.name" />: <jstl:out value="${recipe.user.name}"/>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="quantities" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="quantity.ingredient" var="ingredientHeader" />
	<display:column property="ingredient.name" title="${ingredientHeader}" sortable="true" />
	
	<spring:message code="quantity.value" var="valueHeader" />
	<display:column property="value" title="${valueHeader}" sortable="true" />

	<spring:message code="quantity.unit" var="unitHeader" />
	<display:column property="unit" title="${unitHeader}" sortable="true" />

</display:table>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="belongs" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="belongs.category.name" var="categorynameHeader" />
	<display:column property="category.name" title="${categorynameHeader}" sortable="true" />

</display:table>
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="steps" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="steps.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<spring:message code="steps.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}" sortable="true" />
</display:table>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="recipeHints" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="recipeHints.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" sortable="true" />

</display:table>
<br>

