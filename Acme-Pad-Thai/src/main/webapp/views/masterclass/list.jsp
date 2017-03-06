
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="masterClasses" requestURI="masterclass/list.do" uid="masterclass">

	<!-- Action links -->
	<jstl:if test="${requestURI }">
		<security:authorize access="hasRole('COOK')">
			<spring:message code="masterclass.edit" var="editHeader" />

			<display:column title="${editHeader}" sortable="true">
				<a href="masterclass/cook/edit.do?masterClassId=${masterclass.id}"> <spring:message
						code="masterclass.edit" />
				</a>
			</display:column>
		</security:authorize>
	</jstl:if>

	<security:authorize access="hasRole('ADMIN')">

		<spring:message code="masterclass.promote" var="promoteHeader" />
		<display:column title="${promoteHeader}" sortable="true">




			<jstl:if test="${masterclass.promoted}">
				<a href="masterclass/administrator/unpromote.do?masterclassId=${masterclass.id}"> <spring:message
						code="masterclass.unpromote" />
				</a>
				<jstl:set var="attended" value="${false}" />
			</jstl:if>

			<jstl:if test="${!masterclass.promoted}">

				<a href="masterclass/administrator/promote.do?masterclassId=${masterclass.id}"> <spring:message
						code="masterclass.promote" />
				</a>
			</jstl:if>

		</display:column>

	</security:authorize>



	<jstl:if test="${!requestURI}">
	<security:authorize access="hasAnyRole('USER','NUTRITIONIST','SPONSOR','ADMIN','COOK')" var="isAuthenticated" />
	<jstl:if test="${isAuthenticatmasterclass">
	
		<spring:message code="masterclass.attend" var="attendantsHeader" />
		<display:column title="${attendantsHeader}" sortable="true">



			<jstl:set var="attended" value="${true}" />

			<jstl:forEach items="${attendClasses}" var="attend">
				<jstl:if test="${attend eq masterclass.id}">
					<a href="masterclass/unattend.do?masterclassId=${masterclass.id}"> <spring:message
							code="masterclass.unattend" />
					</a>
					<jstl:set var="attended" value="${false}" />
				</jstl:if>
			</jstl:forEach>

			<jstl:if test="${attended}">

				<a href="masterclass/attend.do?masterclassId=${masterclass.id}"> <spring:message
						code="masterclass.attend" />
				</a>
			</jstl:if>
			<jstl:set var="attended" value="${true}" />

		</display:column>
	</jstl:if>
	</jstl:if>

	<jstl:if test="${!requestURI}">
		<spring:message code="masterclass.view" var="viewHeader" />
		<display:column title="${viewHeader}" sortable="true">



			<jstl:set var="attended" value="${true}" />

			<jstl:forEach items="${attendClasses}" var="attend">
				<jstl:if test="${attend eq masterclass.id }">
					<a href="masterclass/view.do?masterClassId=${masterclass.id}"> <spring:message
							code="masterclass.view" />
					</a>
					<jstl:set var="attended" value="${false}" />
				</jstl:if>
			</jstl:forEach>

			<jstl:if test="${attended }">
				<spring:message code="masterclass.noView" />

			</jstl:if>
			<jstl:set var="attended" value="${true}" />


		</display:column>
	</jstl:if>
	<jstl:if test="${requestURI}">
		<spring:message code="masterclass.view" var="viewHeader" />

		<display:column title="${viewHeader}" sortable="true">

			<a href="masterclass/view.do?masterClassId=${masterclass.id}"> <spring:message
					code="masterclass.view" />
			</a>
		</display:column>
	</jstl:if>
	<!-- Attributes -->

	<spring:message code="masterclass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="masterclass.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="false" />

	<spring:message code="masterclass.cook" var="cookHeader" />
	<display:column property="cook.name" title="${cookHeader}"
		sortable="true" />


</display:table>
<jstl:if test="${requestURI }">
	<security:authorize access="hasRole('COOK')">
		<a href="masterclass/cook/create.do"><spring:message
				code="masterclass.create" /></a>
		<br>
	</security:authorize>
</jstl:if>