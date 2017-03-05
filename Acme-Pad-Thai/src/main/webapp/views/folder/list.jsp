
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

<display:table pagesize="10" class="displaytag" keepStatus="false"
	name="folders" requestURI="requestURI" id="row">
	
	<!-- Action links -->

		<display:column>
		<jstl:if test="${not(isBasic)}">
			<a href="folder/edit.do?folderId=${row.id}">
				<spring:message	code="folder.edit" />
			</a>
		</jstl:if>
		</display:column>	
		<display:column>
			<a href="folder/view.do?folderId=${row.id}">
				<spring:message	code="folder.view" />
			</a>
		</display:column>	
	
	<!-- Attributes -->
	
	<spring:message code="folder.name" var="name" />
	<display:column property="name" title="${name}" sortable="false"/>

	<spring:message code="folder.isBasic" var="isBasic" />
	<display:column property="isBasic" title="${isBasic}" sortable="false" />

</display:table>

<a href="folder/create.do">
	<spring:message	code="folder.create" />
</a>
