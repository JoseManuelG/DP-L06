
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="Steps" requestURI="/step/list.do" id="row">
	
	<!-- Action links -->
	<jstl:if test="${requestURI }">
	<security:authorize access="hasRole('USER')">
    <spring:message code="step.edit" var="editHeader" />
    
      <display:column title="${editHeader}" sortable="true">
			<a href="step/user/edit.do?stepId=${row.id}">
				<spring:message	code="step.edit" />
			</a>
		</display:column>		
	</security:authorize>
	</jstl:if>
	
	<!-- Attributes -->
	

	<spring:message code="step.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	<spring:message code="step.number" var="numberHeader" />
	<display:column property="step.number" title="${numberHeader}" sortable="true"  />

	
</display:table>
<jstl:if test="${requestURI }">
	<security:authorize access="hasRole('USER')">
		<a href="step/user/create.do"><spring:message  code="step.create" /></a>
		<br>
	</security:authorize>
</jstl:if>