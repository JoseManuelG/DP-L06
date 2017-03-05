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

<form:form action="${requestURI}" modelAttribute="propertyValue">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ingredient" />
	
		<form:label path="property">
			<spring:message code="propertyValue.property" />:
			</form:label>
		<form:select id="propertyDropdown" path="property">
			<form:option value="0" label="----"/>
			<form:options items="${properties}" itemValue="id" itemLabel="name" />
			<form:errors cssClass="error" path="property" />
			<br />
		</form:select>
	<br />


	<input type="submit" name="save"
		value="<spring:message code="propertyValue.save" />" />&nbsp; 
	<jstl:if test="${belongs.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="propertyValue.delete" />"
			onclick="return confirm('<spring:message code="propertyValue.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel" value="<spring:message code="propertyValue.cancel" />"
		onclick="javascript:window.location.href='recipe/user/myRecipes.do'" />
	<br />
</form:form>
