
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message	code="folder.name" />: &nbsp; <jstl:out value="${folder.name}" />
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false" requestURI="folder/view.do"
	name="messages" id="row">
	
	<!-- Action links -->
	
		<display:column>
			<a href="message/view.do?messageId=${row.id}">
				<spring:message	code="message.view" />
			</a>
		</display:column>	
	
	<!-- Attributes -->
	
	<spring:message code="message.sender" var="sender" />
	<display:column property="sender" title="${sender}" sortable="false"/>

	<spring:message code="message.recipient" var="recipient" />
	<display:column property="recipient" title="${recipient}" sortable="false" />
	
	<spring:message code="message.sendingMoment" var="sendingMoment" />
	<display:column property="sendingMoment" title="${sendingMoment}" sortable="false" format="{0,date,yyyy/MM/dd}"/>
	
	<spring:message code="message.subject" var="subject" />
	<display:column property="subject" title="${subject}" sortable="false" />
	
	<spring:message code="message.priority" var="priority" />
	<display:column property="priority" title="${priority}" sortable="false" />

</display:table>

