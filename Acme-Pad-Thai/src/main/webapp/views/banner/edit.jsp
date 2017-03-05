
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="banner/sponsor/edit.do" modelAttribute="banner">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="campaign" />

	<form:label path="picture">
		<spring:message code="banner.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors cssClass="error" path="picture" />

	<input type="submit" name="save"
		value="<spring:message code="banner.save" />" />&nbsp; 
	<jstl:if test="${banner.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="banner.delete" />"
			onclick="return confirm('<spring:message code="banner.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="banner.cancel" />"
		onclick="javascript:window.location.href='campaign/sponsor/view.do?campaignId=${banner.campaign.id}'" />
	<br />


</form:form>
