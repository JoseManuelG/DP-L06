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

<spring:message  code="user.name" />: <jstl:out value="${user.name}" />
<br>
<spring:message  code="user.surname" />: <jstl:out value="${user.surname}"/>
<br>
<spring:message  code="user.email" />: <jstl:out value="${user.email}"/>
<br>
<spring:message  code="user.phone" />: <jstl:out value="${user.phone}"/>
<br>
<spring:message  code="user.address" />: <jstl:out value="${user.address}"/>
<br>

<security:authorize access="isAuthenticated()">
		<a href="message/create.do?recipientId=<jstl:out value='${user.id}' />"><spring:message  code="user.sendMessage" /></a>
</security:authorize>
<br>
<spring:message code="user.followeds" />: 
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="user/view.do"
	name="followeds" uid="followed">
	<!-- Attributes -->
	<spring:message code="user.name" var="followedsHeader" />
	<display:column property="followed.name" title="${followedsHeader}" sortable="true" />
</display:table>
<br>
<br>
<spring:message code="user.followers" />: 
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="user/view.do"
	name="followers" uid="follower">
	<!-- Attributes -->
	<spring:message code="user.name" var="followersHeader" />
	<display:column property="follower.name" title="${followersHeader}" sortable="true" />
</display:table>
<jstl:if test="${!esMiPerfil}">
<jstl:choose>
	<jstl:when test="${follow eq false}">
		<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
		<a href="user/follow.do?userId=<jstl:out value='${user.id}' />"><spring:message  code="user.follow" /></a>
		<br>
		</security:authorize>
	</jstl:when>
	<jstl:when test="${follow eq true}">
		<security:authorize access="hasAnyRole('USER', 'NUTRITIONIST')">
		<a href="user/unfollow.do?userId=<jstl:out value='${user.id}' />"><spring:message  code="user.unfollow" /></a>
		<br>
		</security:authorize>
	</jstl:when>
</jstl:choose>
</jstl:if>
	<br>
<jstl:if test="${user.recipes != '[]'}">
	<spring:message  code="user.recipes" />
</jstl:if>
<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="user/view.do"
		name="recipes"  uid="recipe">
		
		<!-- Action links -->
		<spring:message code="recipe.view.title" var="viewTitleHeader" />
		<display:column title="${viewTitleHeader}">
				<a href="recipe/view.do?recipeId=${row.id}">
					<spring:message	code="recipe.view" />
				</a>
		</display:column>	
		
		<!-- Attributes -->
	
		<spring:message code="recipe.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}" sortable="true" />
		
		<spring:message code="recipe.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
	
		<spring:message code="recipe.summary" var="summaryHeader" />
		<display:column property="summary" title="${summaryHeader}" sortable="true" />
	
		<spring:message code="recipe.lastUpdate" var="lastUpdateHeader" />
		<display:column property="lastUpdate" title="${lastUpdateHeader}" sortable="true" />
	
	</display:table>
<jstl:if test="${user.socialIdentities ne '[]'}">
	<spring:message  code="socialIdentity.socialIdentity" />
</jstl:if>
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="socialIdentities" requestURI= "user/view.do" uid="social">
	
	<!-- Action links -->
	<!-- tendremos acceso al curriculum y al usuario -->
	
	<display:column>
			<a href="socialIdentity/edit.do?socialIdentityId=${social.id}">
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
	<jstl:if test="${principal}">
	
	<a href="socialIdentity/create.do">
			<spring:message	code="socialIdentity.create" />
	</a>
	<br>
	</jstl:if>
	
	