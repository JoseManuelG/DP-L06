
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="contest/create.do" modelAttribute="contest">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="qualifieds" />
	
	<form:label path="tittle">
		<spring:message code="contest.tittle" />:
	</form:label>
	<form:input path="tittle" />
	<form:errors cssClass="error" path="tittle" />
	<br />
	
	<form:label path="openingTime">
		<spring:message code="contest.openingTime" />:
	</form:label>
	<form:input path="openingTime" value="${format}"/>
	<form:errors cssClass="error" path="openingTime" />
	<br />
	
		<form:label path="closingTime" value="${format}">
		<spring:message code="contest.closingTime" />:
	</form:label>
	<form:input path="closingTime" />
	<form:errors cssClass="error" path="closingTime" />
	<br />

	<input type="submit" name="Accept"
		value="<spring:message code="contest.accept" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="contest.accept" />"
			onclick="javascript:window.location.href='contest/administrator/list.do'" />
	<br />


</form:form>
