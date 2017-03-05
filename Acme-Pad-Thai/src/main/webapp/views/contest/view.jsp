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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message  code="contest.tittle" />: <jstl:out value="${contest.tittle}" />
<br>

<spring:message  code="contest.openingTime" />: <fmt:formatDate pattern="yyyy-MM-dd"  value="${contest.openingTime}" />
<br>
<spring:message  code="contest.closingTime" />: <fmt:formatDate pattern="yyyy-MM-dd"  value="${contest.closingTime}" />
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="qualifieds" requestURI="contest/view.do" id="row">
	
	<!-- Action links -->

	<spring:message code="contest.view.recipe" var="viewContestHeader" />
	<display:column title="${viewContestHeader}">
			<a href="recipe/view.do?recipeId=${row.recipe.id}">
				<spring:message	code="contest.view.viewrecipe" />
			</a>
	</display:column>
	<!-- Attributes -->

	<spring:message code="contest.view.recipe" var="qualifiedtHeader" />
	<display:column property="recipe.title" title="${qualifiedtHeader}" sortable="true" />
	
	<spring:message code="contest.view.winner" var="winnerHeader" />
	<display:column property="winner" title="${winnerHeader}" sortable="true" />



</display:table>