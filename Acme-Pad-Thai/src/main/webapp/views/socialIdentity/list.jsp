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

<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="socialIdentity/list.do"
	name="socialIdentities" id="row">
	
	<!-- Action links -->
	<!-- tendremos acceso al curriculum y al usuario -->
	
	<display:column>
			<a href="socialIdentity/edit.do?socialIdentityId=${row.id}">
				<spring:message	code="socialIdentity.view" />
			</a>
	</display:column>			
	
	
	<!-- Attributes -->
	<!-- veremos como propiedad de la tabla el ususario propietario del curriculum -->
	<spring:message code="socialIdentity.nick" var="nicHeader" />
	<display:column property="nick" title="${nicHeader}" sortable="false" />
	<spring:message code="socialIdentity.socialNetwork" var="SocialNetworkHeader" />
	<display:column property="socialNetwork" title="${SocialNetworkHeader}" sortable="false" />
	<spring:message code="socialIdentity.link" var="linkHeader" />
	<display:column property="link" title="${linkHeader}" sortable="false" />
		


</display:table>
