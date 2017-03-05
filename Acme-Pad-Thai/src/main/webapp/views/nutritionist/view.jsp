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

<spring:message  code="nutritionist.name" />: <jstl:out value="${nutritionist.name}" />
<br>
<spring:message  code="nutritionist.surname" />: <jstl:out value="${nutritionist.surname}"/>
<br>
<spring:message  code="nutritionist.email" />: <jstl:out value="${nutritionist.email}"/>
<br>
<spring:message  code="nutritionist.phone" />: <jstl:out value="${nutritionist.phone}"/>
<br>
<spring:message  code="nutritionist.address" />: <jstl:out value="${nutritionist.address}"/>
<br>

<br>



<!-- Mandar Mensajes -->
<security:authorize access="isAuthenticated()">
		<a href="message/create.do?recipientId=<jstl:out value='${nutritionist.id}' />"><spring:message  code="user.sendMessage" /></a>
</security:authorize>
<br>

<spring:message code="user.followeds" />: 


<!-- Followeds -->
<jstl:if test="${followeds ne '[]'}">
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="${requestURI}"
	name="followeds" uid="followed">
	<!-- Attributes -->
	<spring:message code="user.name" var="followedsHeader" />
	<display:column property="followed.name" title="${followedsHeader}" sortable="true" />
</display:table>
</jstl:if>
<br>





<spring:message code="user.followers" />: 

<jstl:if test="${followers ne '[]'}">
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="${requestURI}"
	name="followers" uid="followers">
	<!-- Attributes -->
	<spring:message code="user.name" var="followersHeader" />
	<display:column property="follower.name" title="${followersHeader}" sortable="true" />
</display:table>
</jstl:if>

<jstl:if test="${!esMiPerfil}">
	<jstl:choose>
		<jstl:when test="${follow eq 1}">
			<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
			<a href="nutritionist/follow.do?nutritionistId=<jstl:out value='${nutritionist.id}' />"><spring:message  code="user.follow" /></a>
			<br>
			</security:authorize>
		</jstl:when>
		<jstl:when test="${follow eq 2}">
			<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
			<a href="nutritionist/unfollow.do?nutritionistId=<jstl:out value='${nutritionist.id}' />"><spring:message  code="user.unfollow" /></a>
			<br>
			</security:authorize>
		</jstl:when>
	</jstl:choose>
</jstl:if>

<br>




<!-- socialIdentities -->
	<spring:message  code="socialIdentity.socialIdentity" />:

<br>
<jstl:if test="${nutritionist.socialIdentities ne '[]'}">
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="${requestURI}"
	name="socialIdentities" uid="social">
	
	<!-- Action links -->
	<!-- tendremos acceso al curriculum y al usuario -->
	
	<display:column>
			<a href="socialIdentity/edit.do?socialIdentityId=${social.id}">
				<spring:message	code="socialIdentity.view" />
			</a>
	</display:column>			
	<jstl:if test="${principal}">
	<display:column>
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
<jstl:if test="${not empty curriculum.picture }">
<br>
Curriculum:
<br>
<img src="${curriculum.picture}" alt="<spring:message code="nutritionist.curriculum.notIMG" /> "/>
<br/>
<spring:message code="nutritionist.curriculum.picture" />:	<jstl:out value="${curriculum.picture}"/>
<br/>

<spring:message code="nutritionist.curriculum.education" />:	<jstl:out value="${curriculum.educationSection}"/>
<br/>
<spring:message code="nutritionist.curriculum.experience" />:<jstl:out value="${curriculum.experienceSection}"/>
<br/>
<spring:message code="nutritionist.curriculum.hobbies" />:<jstl:out value="${curriculum.hobbiesSection}"/>
<br/>
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="${requestURI}"
	name="endorsers" uid="endorser">
	
	<!-- Attributes -->
	
	<spring:message code="nutritionist.curriculum.endorser.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="nutritionist.curriculum.endorser.homepage" var="homepageHeader" />
	<display:column property="homepage" title="${homepageHeader}" sortable="false" />

	
</display:table>
</jstl:if>
<br>


	