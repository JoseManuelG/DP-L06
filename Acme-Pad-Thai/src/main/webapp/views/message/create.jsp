
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="message/create.do" modelAttribute="messag">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folder" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	<form:hidden path="sendingMoment" />
	
	<form:label path="subject">
		<spring:message code="messag.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	
	<form:label path="priority">
		<spring:message code="messag.priority" />:
	</form:label>
	<form:select id="priorityDropdown" path="priority">
		<form:option value="HIGH" label="HIGH"/>
   		<form:option value="NEUTRAL" label="NEUTRAL"/>
   		<form:option value="LOW" label="LOW"/>
	</form:select>
	
	<form:label path="body">
		<spring:message code="messag.body" />:
	</form:label>
	<form:input path="body" />
	<form:errors cssClass="error" path="body" />
	

	<input type="submit" name="save"
		value="<spring:message code="messag.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="messag.cancel" />"
		onclick=" javascript:window.location.href='folder/list.do'" />	<br />
	<br />


</form:form>
