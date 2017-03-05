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

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="nutritionists" requestURI="curriculum/list.do" id="row">
	
	<!-- Action links -->
	<!-- tendremos acceso al curriculum y al usuario -->
	<display:column>
			<a href="curriculum/nutritionist/view.do?curriculumId=${row.id}">
				<spring:message	code="curriculum.view" />
			</a>
	</display:column>
	
	<display:column>
			<a href="nutritionist/view.do?curriculumId=${row.id}">
				<spring:message	code="nutritionist.view" />
			</a>
	</display:column>			
	
	
	<!-- Attributes -->
	<!-- veremos como propiedad de la tabla el ususario propietario del curriculum -->
	<spring:message code="curriculum.NutritionistUserName" var="nutritionistUserName" />
	<display:column property="name" title="${nutritionistUserName}" sortable="true" />
	


</display:table>
