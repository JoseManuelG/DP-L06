
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
	name="bills" requestURI="requestURI" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('SPONSOR')">
		<display:column>
			<a href="bill/sponsor/view.do?billId=${row.id}">
				<spring:message	code="bill.view" />
			</a>
		</display:column>
		
	</security:authorize>
	
	<!-- Attributes -->
	
	<spring:message code="bill.dateOfCreation" var="dateOfCreationHeader" />
	<display:column property="dateOfCreation" title="${dateOfCreationHeader}" sortable="false" format="{0,date,yyyy/MM/dd}"/>

	<spring:message code="bill.cost" var="costHeader" />
	<display:column property="cost" title="${costHeader}" sortable="false"/>
	
	<spring:message code="bill.dateOfPay" var="dayOfPayHeader" />
	<display:column property="dateOfPay" title="${dayOfPayHeader}" sortable="false" format="{0,date,yyyy/MM/dd}"/>


</display:table>
