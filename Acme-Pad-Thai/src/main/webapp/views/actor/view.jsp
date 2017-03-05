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

<spring:message  code="actor.name" />: <jstl:out value="${actor.name}" />
<br>
<spring:message  code="actor.surname" />: <jstl:out value="${actor.surname}"/>
<br>
<spring:message  code="actor.email" />: <jstl:out value="${actor.email}"/>
<br>
<spring:message  code="actor.phone" />: <jstl:out value="${actor.phone}"/>
<br>
<spring:message  code="actor.address" />: <jstl:out value="${actor.address}"/>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="recipes" requestURI="recipe/listByUser.do" id="row">
	
	<!-- Action links -->
	<spring:message code="recipe.view.title" var="viewTitleHeader" />
	<display:column title="${viewTitleHeader}">
			<a href="recipe/view.do?recipeId=${row.id}">
				<spring:message	code="recipe.view" />
			</a>
	</display:column>	
	
	<!-- Attributes -->

	<spring:message code="recipe.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" sortable="true" />
	
	<spring:message code="recipe.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="recipe.summary" var="summaryHeader" />
	<display:column property="summary" title="${summaryHeader}" sortable="true" />

	<spring:message code="recipe.lastUpdate" var="lastUpdateHeader" />
	<display:column property="lastUpdate" title="${lastUpdateHeader}" sortable="true" />

</display:table>
