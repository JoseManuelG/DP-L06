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

<form:form action="${requestURI}" modelAttribute="quantity">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipe" />
	
	<jstl:if test="${requestURI == 'quantity/user/edit.do'}">
		<form:hidden path="ingredient" />
		
	</jstl:if>
	<jstl:if test="${requestURI == 'quantity/user/create.do'}">

		
		<form:label path="ingredient">
			<spring:message code="quantity.ingredient" />:
			</form:label>
		<form:select id="ingredientDropdown" path="ingredient">
			<form:option value="0" label="----"/>
			<form:options items="${ingredients}" itemValue="id" itemLabel="name" />
			
			<br />
		</form:select>
		<form:errors cssClass="error" path="ingredient" />
	</jstl:if>
	<br />
	<form:label path="unit">
		<spring:message code="quantity.unit" />:
	</form:label>
	<form:select id="unitDropdown" path="unit">
		<spring:message code="quantity.unit" />:
		<form:option value="0" label="----"/>
		<form:options items="${units}" />
	</form:select>
	<form:errors cssClass="error" path="unit" />
	<br>
	
	<form:label path="value">
		<spring:message code="quantity.value" />:
	</form:label>
	<form:input type="number" step="any" path="value" />
	<form:errors cssClass="error" path="value" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="quantity.save" />" />&nbsp; 
	<jstl:if test="${quantity.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="quantity.delete" />"
			onclick="return confirm('<spring:message code="quantity.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="quantity.cancel" />"
		onclick="javascript:window.location.href='recipe/user/edit.do?recipeId=${urlID}'" />
	<br />
</form:form>
