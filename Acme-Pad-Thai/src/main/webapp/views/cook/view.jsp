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



<!-- socialIdentities -->
	<spring:message  code="socialIdentity.socialIdentity" />:

<br>
<jstl:if test="${cook.socialIdentities ne '[]'}">
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="${requestURI}"
	name="socialIdentities" uid="social">
	
	<!-- Action links -->
	<!-- tendremos acceso al curriculum y al usuario -->
	
	<display:column>
			<a href="socialIdentity/view.do?socialIdentityId=${social.id}">
				<spring:message	code="socialIdentity.view" />
			</a>
	</display:column>			
	<jstl:if test="${principal}">
	<display:column>
	<a href="socialIdentity/edit.do?socialIdentityId=${social.id}">
				<spring:message	code="socialIdentity.edit" />
			</a>
	</display:column>			
	</jstl:if>
	
	<!-- Attributes -->
	<!-- veremos como propiedad de la tabla el ususario propietario del curriculum -->
		<spring:message code="socialIdentity.nick" var="nicHeader" />
		<display:column property="nick" title="${nicHeader}" sortable="false" />
		<spring:message code="socialIdentity.socialNetwork" var="SocialNetworkHeader" />
		<display:column property="socialNetwork" title="${SocialNetworkHeader}" sortable="false" />
		<spring:message code="socialIdentity.link" var="linkHeader" />
		<display:column property="link" title="${linkHeader}" sortable="false" />
	</display:table>
	</jstl:if>
	<jstl:if test="${principal}">
	
	<a href="socialIdentity/create.do">
			<spring:message	code="socialIdentity.create" />
	</a>
	<br>
	</jstl:if>
	<br>
