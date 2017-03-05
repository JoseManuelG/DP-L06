

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<img src="${ingredient.picture}" alt="<spring:message code="ingredient.picture.notIMG" /> "/>
<br/>
<spring:message code="ingredient.picture" />:	<jstl:out value="${curriculum.picture}"/>
<br/>

<spring:message code="ingredient.name" />:	<jstl:out value="${ingredient.name}"/>
<br/>
<spring:message code="ingredient.description" />:<jstl:out value="${ingredient.description}"/>
<br/>
<spring:message code="ingredient.propertyValues" />
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="ingredient/nutritionist/view.do"
	name="propertyValues" id="row">
	
	<!-- Action links -->
	
	
		<display:column>
			<a href="propertyValue/nutritionist/delete.do?propertyValueId=${row.id}">
				<spring:message	code="propertyValue.delete" />
			</a>
		</display:column>		
		
	<!-- Attributes -->
	
	<spring:message code="ingredient.propertyValue.property" var="propertyHeader" />
	<display:column property="property.name" title="${propertyHeader}" sortable="false" />

	

	
</display:table>
<br/>

		<a href="propertyValue/nutritionist/create.do?ingredientId=${ingredient.id }"><spring:message  code="ingredient.propertyValue.property.create" /></a>
		<br>
	
<br/>
