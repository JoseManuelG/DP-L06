

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message code="learningmaterial.title" />:	<jstl:out value="${learningMaterial.title}"/>
<br/>
<spring:message code="learningmaterial.type" />:	<jstl:out value="${learningMaterial.type}"/>
<br/>
<spring:message code="learningmaterial.summary" />:	<jstl:out value="${learningMaterial.summary}"/>
<br/>
<spring:message code="learningmaterial.data" />:	<jstl:out value="${learningMaterial.data}"/>
<br/>


<spring:message code="learningmaterial.attachments" />:
<br/>
<jstl:set var="indice" value="${-1}"/>
<table>
<thead>
 <tr>
    <security:authorize access="hasRole('COOK')">
		<jstl:if test="${myAttachment}">
			<spring:message code="learningmaterial.attachment.delete" var="deleteHeader" />
    		<th>${deleteHeader}</th>
    	</jstl:if>
    </security:authorize>
    <spring:message code="learningmaterial.attachments" var="attachmentsHeader" />
    <th>${attachmentsHeader}</th>
  </tr>
 </thead>
	<jstl:forEach var="attachment" items="${attachments}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<security:authorize access="hasRole('COOK')">
			<jstl:if test="${myAttachment}">
				<td>
				<jstl:set var="position" value="${indice +1}"/>
					<a href="learningmaterial/cook/deleteAttachment.do?learningMaterialId=${learningMaterial.id}&attachmentIndex=${position}">
					<spring:message	code="learningmaterial.delete" />
					</a>
				</td>
			</jstl:if>
		</security:authorize>
		<td>
		<a href="<jstl:out value="${attachment}"/>" ><jstl:out value="${attachment}"/></a>
		</td>
	</tr>
	<jstl:set var="indice" value="${indice +1}"/>
	</jstl:forEach>
</table>
<security:authorize access="hasRole('COOK')">
	<jstl:if test="${myAttachment}">
		<br>
		<a href="learningmaterial/cook/addAttachment.do?learningMaterialId=${learningMaterial.id}" >
			<spring:message code="learningmaterial.attachment.create" />
		</a>
		<br>
	</jstl:if>
</security:authorize>