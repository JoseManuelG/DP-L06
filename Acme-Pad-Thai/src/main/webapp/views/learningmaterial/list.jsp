
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="learningMaterials" requestURI="learninMaterial/list.do" id="row">
	
	<!-- Action links -->
	
	<security:authorize access="hasRole('COOK')">
		<display:column>
			<a href="learningMaterial/cook/edit.do?learningMaterialId=${row.id}">
				<spring:message	code="learningmaterial.edit" />
			</a>
		</display:column>		
	</security:authorize>
		
		<display:column>		
				<a href="learningMaterial/actor/view.do?learningMaterialId=${row.id}">
					<spring:message	code="learningmaterial.view" />
				</a>
		</display:column>		
		
	<!-- Attributes -->
	
	<spring:message code="learningmaterial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="learningmaterial.type" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />

	
</display:table>
