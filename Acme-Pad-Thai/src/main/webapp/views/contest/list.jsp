<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="contests" requestURI="contest/list.do" id="row">

	<!-- Action links -->
	<spring:message code="contest.view" var="viewHeader" />

	<display:column title="${viewHeader}">
		<a href="contest/view.do?contestId=${row.id}"> <spring:message
				code="contest.view" />
		</a>
	</display:column>


	<input type="submit" name="Submit"
		value="<spring:message code="contest.accept" />"
		onclick="return confirm('<spring:message code="contest.confirm.submit" />')" />&nbsp;
	
	
		<spring:message code="contest.submit" var="viewSubmitHeader" />
	<security:authorize access="hasRole('USER')">
		<display:column title="${viewSubmitHeader}">
			<jsp:useBean id="now" class="java.util.Date" />
			<jstl:if test="${row.openingTime lt now }">
				<jstl:if test="${now lt row.closingTime }">
					<a href="contest/submit.do?contestId=${row.id}"> <spring:message
							code="contest.submit" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<spring:message code="contest.edit" var="viewEditHeader" />
	<security:authorize access="hasRole('ADMIN')">
		<display:column title="${viewEditHeader}">
			<a href="contest/administrator/edit.do?contestId=${row.id}"> <spring:message
					code="contest.edit" />
			</a>
		</display:column>
	</security:authorize>
	<security:authorize access="hasRole('ADMIN')">

		<spring:message code="contest.winner.compute" var="winnerHeader" />
		<display:column title="${winnerHeader}">
			<a href="contest/administrator/compute.do?contestId=${row.id}"> <spring:message
					code="contest.winner.compute" />
			</a>
		</display:column>
	</security:authorize>
	<!-- Attributes -->
	<spring:message code="contest.tittle" var="tittleHeader" />
	<display:column property="tittle" title="${tittleHeader}"
		sortable="true" />

	<spring:message code="contest.openingTime" var="openingTimeHeader" />
	<display:column property="openingTime" title="${openingTimeHeader}"
		sortable="true" />

	<spring:message code="contest.closingTime" var="closingTimeHeader" />
	<display:column property="closingTime" title="${closingTimeHeader}"
		sortable="true" />


</display:table>
	<security:authorize access="hasRole('ADMIN')">
		<a href="contest/administrator/create.do"><spring:message  code="contest.create" /></a>
		<br>
	</security:authorize>
