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

<spring:message  code="sponsor.name" />: <jstl:out value="${sponsor.name}" />
<br>
<spring:message  code="sponsor.surname" />: <jstl:out value="${sponsor.surname}"/>
<br>
<spring:message  code="sponsor.email" />: <jstl:out value="${sponsor.email}"/>
<br>
<spring:message  code="sponsor.phone" />: <jstl:out value="${sponsor.phone}"/>
<br>
<spring:message  code="sponsor.address" />: <jstl:out value="${sponsor.address}"/>
<br>

<spring:message  code="sponsor.companyname" />: <jstl:out value="${sponsor.companyName}"/>
<br>

<security:authorize access="isAuthenticated()">
		<a href="message/create.do?recipientId=<jstl:out value='${sponsor.id}' />"><spring:message  code="user.sendMessage" /></a>
</security:authorize>
<br>

<br>
<jstl:if test="${sponsor.campaigns!= '[]'}">
	<spring:message  code="sponsor.campaigns" />

<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="sponsor/view.do"
  name="campaigns" uid="campaign">
  
  
  <spring:message code="sponsor.campaign.dateOfStart" var="dateOfStartHeader" />
  <display:column property="dateOfStart" title="${dateOfStartHeader}" sortable="false" format="{0,date,yyyy/MM/dd}"/>

  <spring:message code="sponsor.campaign.dateOfEnd" var="dateOfEndHeader" />
  <display:column property="dateOfEnd" title="${dateOfEndHeader}" sortable="false" format="{0,date,yyyy/MM/dd}" />

  <spring:message code="sponsor.campaign.starCampaign" var="starCampaignHeader" />
  <display:column property="starCampaign" title="${starCampaignHeader}" sortable="false" />
  
  <spring:message code="sponsor.campaign.maxNumOfDisplays" var="maxNumOfDisplaysHeader" />
  <display:column property="maxNumOfDisplays" title="${maxNumOfDisplaysHeader}" sortable="false" />
  
  <spring:message code="sponsor.campaign.numOfDisplays" var="displaysHeader" />
  <display:column property="numOfDisplays" title="${displaysHeader}" sortable="false" />

</display:table>
</jstl:if>
<!-- Listing grid -->
	<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="sponsor/view.do"
  name="creditCards" uid="creditCard">
  <jstl:if test="${sponsor.creditCards!= '[]'}">
	<spring:message  code="sponsor.creditCards" />
	</jstl:if>
 		<display:column>
			<a href="creditCard/sponsor/edit.do?creditCardId=${creditCard.id}">
				<spring:message	code="folder.edit" />
			</a>
		</display:column>	
  
  <spring:message code="sponsor.creditCard.holderName" var="holderName" />
  <display:column property="holderName" title="${holderName}" sortable="false"/>

  <spring:message code="sponsor.creditCard.brandName" var="brandName" />
  <display:column property="brandName" title="${brandName}" sortable="false"/>

  <spring:message code="sponsor.creditCard.expirationMonth" var="expirationMonth" />
  <display:column property="expirationMonth" title="${expirationMonth}" sortable="false" />
  
  <spring:message code="sponsor.creditCard.expirationYear" var="expirationYear" />
  <display:column property="expirationYear" title="${expirationYear}" sortable="false" />
  
  <spring:message code="sponsor.creditCard.number" var="number" />
  <display:column property="number" title="${number}" sortable="false" />
  
  <spring:message code="sponsor.creditCard.cvvCode" var="cvvCode" />
  <display:column property="cvvCode" title="${cvvCode}" sortable="false" />

</display:table>

<a href="creditCard/sponsor/create.do">
			<spring:message	code="sponsor.creditCard.create" />
	</a>
<br>
<br>
<jstl:if test="${nutritionist.socialIdentity ne '[]'}">
	<spring:message  code="socialIdentity.socialIdentity" />
</jstl:if>
<br>

<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="sponsor/view.do"
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
	