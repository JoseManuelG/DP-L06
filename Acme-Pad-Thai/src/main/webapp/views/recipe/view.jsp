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

<img  height="100px" width="500px" alt="La imagen no está disponible" src="<jstl:out value="${banner.picture}"/>" />
<br>
<br>
<br>

<spring:message  code="recipe.title" />: <jstl:out value="${recipe.title}" />
<br>
<spring:message  code="recipe.ticker" />: <jstl:out value="${recipe.ticker}"/>
<br>
<spring:message  code="recipe.summary" />: <jstl:out value="${recipe.summary}"/>
<br>
<spring:message  code="recipe.authorMoment" />: <jstl:out value="${recipe.authorMoment}"/>
<br>
<spring:message  code="recipe.lastUpdate" />: <jstl:out value="${recipe.lastUpdate}"/>
<br>
<spring:message  code="recipe.user.name" />: <jstl:out value="${recipe.user.name}"/>
<br>
<spring:message  code="recipe.pictures" />:
<br>

<br>

<br>
<jstl:if test="${recipe.id ne 0}">
<spring:message  code="recipe.pictures" />:
<br>


<jstl:set var="indice" value="${-1}"/>
<table>
<thead>
 <tr>
 	<spring:message code="recipe.pictures" var="picheader" />
    
    <th>${picheader}</th>
  </tr>
 </thead>
	<jstl:forEach var="picture" items="${pictures}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		

		<td>
		<img  height="200px" width="250px" alt="La imagen no está disponible" src="<jstl:out value="${picture}"/>" />
		</td>
	</tr>
	<jstl:set var="indice" value="${indice+1}"/>
	</jstl:forEach>
</table>
</jstl:if>



<br>
<br>
<spring:message  code="recipe.likes" />:<jstl:out value="${likes}"/>
<br>
<spring:message  code="recipe.dislikes" />:<jstl:out value="${dislikes}"/>
<br>
<jstl:if test="${!esMiReceta}">
	<jstl:choose>
		<jstl:when test="${like eq 3}">
			<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
			<a href="recipe/user/like.do?recipeId=<jstl:out value='${recipe.id}' />"><img src="images/like.png" alt="<spring:message  code="recipe.like" />"></a>
			<br>
			</security:authorize>
		</jstl:when>
		<jstl:when test="${like eq 2}">
			<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
			<a href="recipe/user/dislike.do?recipeId=<jstl:out value='${recipe.id}' />"><img src="images/dislike.png" alt="<spring:message  code="recipe.dislike" />"></a>
			<br>
			</security:authorize>
		</jstl:when>
		<jstl:when test="${like eq 1}">
			<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
			<a href="recipe/user/like.do?recipeId=<jstl:out value='${recipe.id}' />"><img src="images/like.png" alt="<spring:message  code="recipe.like" />"></a>
			<a href="recipe/user/dislike.do?recipeId=<jstl:out value='${recipe.id}' />"><img src="images/dislike.png" alt="<spring:message  code="recipe.dislike" />"></a>
			<br>
			</security:authorize>
		
		</jstl:when>
	</jstl:choose>
</jstl:if>
<br>
<spring:message  code="recipe.comments" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="comments" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="comments.title" var="commentTitleHeader" />
	<display:column property="title" title="${commentTitleHeader}" sortable="true" />
	
	<spring:message code="comments.text" var="commentTextHeader" />
	<display:column property="text" title="${commentTextHeader}" sortable="true" />
	
	<spring:message code="comments.stars" var="commentStarsHeader" />
	<display:column property="stars" title="${commentStarsHeader}" sortable="true" />
	
	<spring:message code="comments.dateCreation" var="commentDateCreationHeader" />
	<display:column property="dateCreation" title="${commentDateCreationHeader}" sortable="true" />

</display:table>
<br>

<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
<a href="comment/create.do?recipeId=<jstl:out value='${recipe.id}' />"><spring:message  code="recipe.comment" /></a>
<br>
</security:authorize>
<br>

<spring:message  code="recipe.quantities" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="quantities" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="quantity.ingredient" var="ingredientHeader" />
	<display:column property="ingredient.name" title="${ingredientHeader}" sortable="true" />

	<spring:message code="quantity.unit" var="unitHeader" />
	<display:column property="unit" title="${unitHeader}" sortable="true" />
		
	<spring:message code="quantity.value" var="valueHeader" />
	<display:column property="value" title="${valueHeader}" sortable="true" />

</display:table>
<br>
<spring:message  code="recipe.belongs" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="belongs" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="belongs.category.name" var="categorynameHeader" />
	<display:column property="category.name" title="${categorynameHeader}" sortable="true" />

</display:table>
<br>
<spring:message  code="recipe.steps" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="steps" requestURI="recipe/view.do" id="row">
	
	<!-- Action links -->
			<spring:message code="recipe.view.step" var="viewHeader" />
			<display:column title="${viewHeader}">
				<a href="step/view.do?stepId=${row.id}">
				<spring:message	code="recipe.view" />
				</a>
				
			</display:column>
	<!-- Attributes -->
	
	<spring:message code="steps.number" var="numberHeader" />
	<display:column property="number" title="${numberHeader}" sortable="true" />

	<spring:message code="steps.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
</display:table>
<br>
<spring:message  code="recipe.recipeHints" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="recipeHints" requestURI="recipe/view.do" id="row">
	
	<!-- Attributes -->

	<spring:message code="recipeHints.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" sortable="true" />

</display:table>
<br>

