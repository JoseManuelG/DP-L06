<%--
 * edit.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="recipe">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="authorMoment" />
	<form:hidden path="lastUpdate" />
	<form:hidden path="pictures" />
	<form:hidden path="isCopy" />
	<form:hidden path="parentTicker" />
	<form:hidden path="quantities" />
	<form:hidden path="belongs" />
	<form:hidden path="recipeHints" />
	<form:hidden path="comments" />
	<form:hidden path="user" />
	<form:hidden path="steps" />
	<form:hidden path="qualifieds" />
	
	<form:hidden path="qualifications" />
	<form:label path="title">
		<spring:message code="recipe.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="summary">
		<spring:message code="recipe.summary" />:
	</form:label>
	<form:textarea path="summary" cols="30" rows="5" />
	<form:errors cssClass="error" path="summary" />
	<br />
	<input type="submit" name="save" value="<spring:message code="recipe.save" />" />&nbsp; 
	<jstl:if test="${recipe.id ne 0}">
		<input type="submit" name="delete"
			value="<spring:message code="recipe.delete" />"
			onclick="return confirm('<spring:message code="recipe.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="recipe.cancel" />"
		onclick="javascript:window.location.href='recipe/user/myRecipes.do'" />
	<br />


</form:form>
<jstl:if test="${recipe.id ne 0}">
<spring:message  code="recipe.pictures" />:
<br>


<jstl:set var="indice" value="${-1}"/>
<table>
<thead>
 <tr>
 	<spring:message code="recipe.pictures" var="picheader" />
    <th>${picheader}</th>
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
		<jstl:set var="position" value="${indice +1}"/>
			<a href="recipe/user/deletePicture.do?recipeId=${recipe.id}&pictureIndex=${position}">
			<spring:message	code="recipe.picture.delete" />
			</a>
		</td>

		<td>
		<img  height="200px" width="250px" alt="La imagen no está disponible" src="<jstl:out value="${picture}"/>" />
		</td>
	</tr>
	<jstl:set var="indice" value="${indice+1}"/>
	</jstl:forEach>
</table>
<br>
<a href="recipe/user/createpicture.do?recipeId=${recipe.id}" >
	<spring:message code="recipe.picture.create" />
</a>
<br>
<spring:message  code="recipe.likes" />:<jstl:out value="${likes}"/>
<br>
<spring:message  code="recipe.dislikes" />:<jstl:out value="${dislikes}"/>
<br>
<spring:message  code="recipe.quantities" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="quantities" requestURI="recipe/view.do" uid="quantity">
	
	<!-- Action links -->
			<spring:message code="recipe.edit.quantity" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="quantity/user/edit.do?quantityId=${quantity.id}">
				<spring:message	code="recipe.edit" />
				</a>
				
			</display:column>
	<!-- Attributes -->

	<spring:message code="quantity.ingredient" var="ingredientHeader" />
	<display:column property="ingredient.name" title="${ingredientHeader}" sortable="true" />
	
	<spring:message code="quantity.value" var="valueHeader" />
	<display:column property="value" title="${valueHeader}" sortable="true" />

	<spring:message code="quantity.unit" var="unitHeader" />
	<display:column property="unit" title="${unitHeader}" sortable="true" />

</display:table>

<security:authorize access="hasRole('USER')">
<a href="quantity/user/create.do?recipeId=<jstl:out value='${recipe.id}' />"><spring:message  code="recipe.quantity.create" /></a>
<br>
</security:authorize>

<br>
<spring:message  code="recipe.belongs" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="belongs" requestURI="recipe/view.do" uid="brlong">
	<!-- Action links -->
			<spring:message code="recipe.edit.belongs" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="belongs/user/edit.do?belongsId=${brlong.id}">
				<spring:message	code="recipe.edit" />
				</a>
				
			</display:column>
	<!-- Attributes -->

	<spring:message code="belongs.category.name" var="categorynameHeader" />
	<display:column property="category.name" title="${categorynameHeader}" sortable="true" />

</display:table>

<security:authorize access="hasRole('USER')">
<a href="belongs/user/create.do?recipeId=<jstl:out value='${recipe.id}' />"><spring:message  code="recipe.belongs.create" /></a>
<br>
</security:authorize>
<br>
<br>
<spring:message  code="recipe.steps" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="steps" requestURI="recipe/view.do" uid="step">
	<!-- Action links -->
			<spring:message code="recipe.edit.step" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="step/user/edit.do?stepId=${step.id}">
				<spring:message	code="recipe.edit" />
				</a>
				
			</display:column>
	<!-- Attributes -->

	<spring:message code="step.number" var="stepNumberHeader" />
	<display:column property="number" title="${stepNumberHeader}" sortable="true" />
	
	<spring:message code="step.description" var="stepDescriptionHeader" />
	<display:column property="description" title="${stepDescriptionHeader}" sortable="false" />
	
	
</display:table>

<security:authorize access="hasRole('USER')">
<a href="step/user/create.do?recipeId=<jstl:out value='${recipe.id}' />"><spring:message  code="recipe.step.create" /></a>
<br>
</security:authorize>
<br>
<br>
<br>
<spring:message  code="recipe.recipeHints" />:
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="recipeHints" requestURI="recipe/view.do" uid="recipeHint">
	<!-- Action links -->
			<spring:message code="recipe.edit.recipeHint" var="editHeader" />
			<display:column title="${editHeader}">
				<a href="recipehint/user/edit.do?recipeHintId=${recipeHint.id}">
				<spring:message	code="recipe.edit" />
				</a>
				
			</display:column>
	<!-- Attributes -->

	<spring:message code="recipeHint.hint" var="recipeHintsHintHeader" />
	<display:column property="hint" title="${recipeHintsHintHeader}" sortable="true" />

</display:table>

<security:authorize access="hasRole('USER')">
<a href="recipehint/user/create.do?recipeId=<jstl:out value='${recipe.id}' />"><spring:message  code="recipe.recipehint.create" /></a>
<br>
</security:authorize>
<br>
<br>
</jstl:if>
