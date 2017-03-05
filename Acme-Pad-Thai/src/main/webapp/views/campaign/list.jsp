
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="campaigns" requestURI="requestURI" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a href="campaign/sponsor/edit.do?campaignId=${row.id}">
				<spring:message	code="campaign.edit" />
			</a>
		</display:column>	
		<display:column>
			<a href="campaign/sponsor/view.do?campaignId=${row.id}">
				<spring:message	code="campaign.view" />
			</a>
		</display:column>	
	</security:authorize>
	
	<!-- Attributes -->
	
	<spring:message code="campaign.dateOfStart" var="dateOfStartHeader" />
	<display:column property="dateOfStart" title="${dateOfStartHeader}" sortable="false" format="{0,date,yyyy/MM/dd}"/>

	<spring:message code="campaign.dateOfEnd" var="dateOfEndHeader" />
	<display:column property="dateOfEnd" title="${dateOfEndHeader}" sortable="false" format="{0,date,yyyy/MM/dd}" />

	<spring:message code="campaign.starCampaign" var="starCampaignHeader" />
	<display:column property="starCampaign" title="${starCampaignHeader}" sortable="false" />
	
	<spring:message code="campaign.maxNumOfDisplays" var="maxNumOfDisplaysHeader" />
	<display:column property="maxNumOfDisplays" title="${maxNumOfDisplaysHeader}" sortable="false" />
	
	<spring:message code="campaign.numOfDisplays" var="displaysHeader" />
	<display:column property="numOfDisplays" title="${displaysHeader}" sortable="false" />

</display:table>

<a href="campaign/sponsor/create.do">
	<spring:message	code="campaign.create" />
</a>
