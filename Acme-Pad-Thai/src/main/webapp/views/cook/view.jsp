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

<spring:message  code="cook.name" />: <jstl:out value="${cook.name}" />
<br>
<spring:message  code="cook.surname" />: <jstl:out value="${cook.surname}"/>
<br>
<spring:message  code="cook.email" />: <jstl:out value="${cook.email}"/>
<br>
<spring:message  code="cook.phone" />: <jstl:out value="${cook.phone}"/>
<br>
<spring:message  code="cook.address" />: <jstl:out value="${cook.address}"/>
<br>

<security:authorize access="isAuthenticated()">
		<a href="message/create.do?recipientId=<jstl:out value='${cook.id}' />"><spring:message  code="user.sendMessage" /></a>
</security:authorize>
<br>
<jstl:if test="${cook.masterClasses!= '[]'}">
	<spring:message  code="cook.masterClasses" />
</jstl:if>
<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="masterclasses" requestURI="cook/view.do" id="row">>
	
	<!-- Action links -->
	
	<security:authorize access="hasRole('COOK')">
		<display:column>
			<a href="masterclass/cook/edit.do?masterClassId=${row.id}">
				<spring:message	code="masterclass.edit" />
			</a>
		</display:column>		
	</security:authorize>
	
	
	<!-- Attributes -->
	
	<spring:message code="masterclass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="masterclass.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	<spring:message code="masterclass.cook" var="cookHeader" />
	<display:column property="cook.name" title="${cookHeader}" sortable="true"  />

	
</display:table>
