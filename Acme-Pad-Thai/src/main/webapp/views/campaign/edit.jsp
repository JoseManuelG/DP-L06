
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="campaign/sponsor/edit.do" modelAttribute="campaign">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="starCampaign" />
	<form:hidden path="bannerList" />
	<form:hidden path="sponsor" />
	<form:hidden path="numOfDisplays"/>
	
	<spring:message	code="campaign.dateFormat" var="format" />
	<br>
	<form:label path="dateOfStart">
		<spring:message code="campaign.dateOfStart" />:
	</form:label>
	<form:input path="dateOfStart" placeholder="${format}" />
	<form:errors cssClass="error" path="dateOfStart" />
	<br />

	<form:label path="dateOfEnd">
		<spring:message code="campaign.dateOfEnd" />:
	</form:label>
	<form:input path="dateOfEnd" placeholder="${format}"/>
	<form:errors cssClass="error" path="dateOfEnd" />
	<br />

	<form:label path="maxNumOfDisplays">
		<spring:message code="campaign.maxNumOfDisplays" />:
	</form:label>
	<form:input path="maxNumOfDisplays" />
	<form:errors cssClass="error" path="maxNumOfDisplays" />
	<br />


<%-- 	<!-- Listing grid -->
*
*	<display:table pagesize="5" class="displaytag" keepStatus="false"
*		name="banners" requestURI="${requestURI}" id="row">
*	
*		<!-- Action links -->
*
*		<security:authorize access="hasRole('SPONSOR')">
*			<display:column>
*				<a href="banner/sponsor/edit.do?bannerId=${row.id}">
*					<spring:message	code="banner.edit" />
*				</a>
*			</display:column>		
*		</security:authorize>
*	
*		<!-- Attributes -->
*	
*		<spring:message code="banner.picture" var="pictureHeader" />
*		<display:column property="picture" title="${pictureHeader}" sortable="true"/>
*		
*	</display:table>
*
--%>

	<input type="submit" name="save"
		value="<spring:message code="campaign.save" />" />&nbsp; 
	<jstl:if test="${campaign.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="campaign.delete" />"
			onclick="return confirm('<spring:message code="campaign.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="campaign.cancel" />"
		onclick="javascript:window.location.href='campaign/sponsor/list.do'" />
	<br />


</form:form>
