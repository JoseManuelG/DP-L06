
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message	code="campaign.dateOfStart" />: &nbsp; <jstl:out value="${campaign.dateOfStart}" />
<br>
<spring:message	code="campaign.dateOfEnd" />: &nbsp; <jstl:out value="${campaign.dateOfEnd}"/>
<br>
<spring:message	code="campaign.maxNumOfDisplays" />: &nbsp; <jstl:out value="${campaign.maxNumOfDisplays}"/>
<br>
<spring:message	code="campaign.numOfDisplays" />: &nbsp; <jstl:out value="${campaign.numOfDisplays}"/>
<br>

<jstl:set var="index" value="${-1}"/>
<display:table pagesize="5" class="displaytag" keepStatus="false"
		name="banners" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->
	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a href="banner/sponsor/edit.do?bannerId=${row.id}">
				<spring:message	code="banner.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<!-- Attributes -->

	<spring:message code="banner.picture" var="picture" />
	<display:column title="${picture}" sortable="true">
	<jstl:set var="index" value="${index+1 }"/>
	<img height="100px" width="500px" alt="<spring:message code="banner.notImage"/>" src="${banners[index].picture}"/>
	</display:column>
</display:table>
<a href="banner/sponsor/create.do?campaignId=${campaign.id}">
	<spring:message	code="banner.create" />
</a>
