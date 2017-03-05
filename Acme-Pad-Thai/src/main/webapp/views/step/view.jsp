

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message code="step.description" />:	<jstl:out value="${step.description}"/>
<br/>
<spring:message code="step.number" />:	<jstl:out value="${step.number}"/>
<br/>
<img  height="200px" width="250px" alt="La imagen no está disponible" src="<jstl:out value="${step.picture}"/>" />

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="stepHints"  id="row">
	
	<!-- Action links -->
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<a href="stephint/user/edit.do?stephintId=${row.id}">
				<spring:message	code="step.stepHint.edit" />
			</a>
		</display:column>		
	</security:authorize>
			
		
	<!-- Attributes -->
	
	<spring:message code="step.hint" var="hintHeader" />
	<display:column property="hint" title="${hintHeader}" sortable="true" />

	
</display:table>

<security:authorize access="hasRole('USER')">
<a href="stephint/user/create.do?stepId=<jstl:out value='${step.id}' />"><spring:message  code="step.create.stepHint" /></a>
<br>
</security:authorize>
