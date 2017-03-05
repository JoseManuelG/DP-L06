

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message code="masterclass.title" />:	<jstl:out value="${masterclass.title}"/>
<br/>
<spring:message code="masterclass.promoted" />:	<jstl:out value="${masterclass.promoted}"/>
<br/>
<spring:message code="masterclass.description" />:	<jstl:out value="${masterclass.description}"/>
<br/>

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="learningmaterials"  id="row">
	
	<!-- Action links -->
	
	<security:authorize access="hasRole('COOK')">
			<jstl:if test="${row.masterClass.cook.id == ownerCook.id}">
				<display:column>
		
				<a href="learningmaterial/cook/edit.do?learningMaterialId=${row.id}">
					<spring:message	code="masterclass.learningmaterial.edit" />
				</a>
				</display:column>
			</jstl:if>
				
	</security:authorize>
		
		<display:column>		
				<a href="learningmaterial/view.do?learningMaterialId=${row.id}">
					<spring:message	code="masterclass.view" />
				</a>
		</display:column>		
		
	<!-- Attributes -->
	
	<spring:message code="masterclass.learningmaterial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="masterclass.learningmaterial.type" var="typeHeader" />
	<display:column property="type" title="${typeHeader}" sortable="false" />

	
</display:table>
<br>
<jstl:if test="${true }">
	<security:authorize access="hasRole('COOK')">
		<jstl:if test="${masterclass.cook.id == ownerCook.id}">
			<a href="learningmaterial/cook/create.do?masterClassId=${masterclass.id}"><spring:message  code="learningmaterial.create" /></a>
			<br>
		</jstl:if>
	</security:authorize>
</jstl:if>