
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="configuration/admin/edit.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="basicFolders" />
	<form:hidden path="keyWords" />

	<form:label path="bannerCost">
		<spring:message code="configuration.bannerCost" />:
	</form:label>
	<form:input type="number" step="any" path="bannerCost" />
	<form:errors cssClass="error" path="bannerCost" />
	<br />
	
	
	
	<jstl:set var="indice" value="${-1}"/>
	<table>
	<thead>
 	<tr>
 		<spring:message code="configuration.keyWords" var="picheader" />
    	<th></th>
    	<th>${picheader}</th>
  	</tr>
 	</thead>
 	<tbody>
		<jstl:forEach var="keyWord" items="${keyWords}" varStatus="counter">
		<jstl:if test="${(counter.count mod 2) == 0}">
		<jstl:set value="even" var="cssClass"></jstl:set>
		</jstl:if>
		<jstl:if test="${(counter.count mod 2) == 1}">
		<jstl:set value="odd" var="cssClass"></jstl:set>
		</jstl:if>
		<tr class="${cssClass}">
			<td>
			<jstl:set var="indice" value="${indice+1}"/>
			<jstl:set var="position" value="${indice}"/>
				<a href="configuration/admin/deleteKeyWord.do?keyWordIndex=${position}">
				<spring:message	code="configuration.delete" />
				</a>
			</td>	

			<td>
			<jstl:out value="${keyWord}"/>
			</td>
		</tr>
		</jstl:forEach>
	</tbody>
	</table>
	<a href="configuration/admin/createKeyWord.do">
		<spring:message	code="configuration.createKeyWord" />
	</a>
	<br>
	<br>
	
	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />&nbsp; 
	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="javascript:window.location.href=''" />
		
	<br />


</form:form>
