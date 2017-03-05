

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<img src="${curriculum.picture}" alt="<spring:message code="curriculum.notIMG" /> "/>
<br/>
<spring:message code="curriculum.picture" />:	<jstl:out value="${curriculum.picture}"/>
<br/>

<spring:message code="curriculum.education" />:	<jstl:out value="${curriculum.educationSection}"/>
<br/>
<spring:message code="curriculum.experience" />:<jstl:out value="${curriculum.experienceSection}"/>
<br/>
<spring:message code="curriculum.hobbies" />:<jstl:out value="${curriculum.hobbiesSection}"/>
<br/>
<jstl:if test="${not empty curriculum.hobbiesSection }">
<spring:message code="curriculum.endorser" />
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="endorsers" id="row">
	
	<!-- Action links -->
	
	
		<display:column>
			<a href="endorser/nutritionist/edit.do?endorserId=${row.id}">
				<spring:message	code="curriculum.endorser.edit" />
			</a>
		</display:column>		
		
	<!-- Attributes -->
	
	<spring:message code="curriculum.endorser.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="false" />

	<spring:message code="curriculum.endorser.homepage" var="homepageHeader" />
	<display:column property="homepage" title="${homepageHeader}" sortable="false" />

	
</display:table>
<br/>

		<a href="endorser/nutritionist/create.do"><spring:message  code="curriculum.createEndorser" /></a>
		<br>
	
</jstl:if>
<br/>

	<security:authorize access="hasRole('NUTRITIONIST')">
		<a href="curriculum/nutritionist/edit.do"><spring:message  code="curriculum.edit" /></a>
		<br>
	</security:authorize>
