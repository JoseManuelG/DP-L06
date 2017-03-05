<%--
 * edit.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="Belongs" />
	<form:hidden path="subCategory" />
	<form:hidden path="tags" />
	

	<form:label path="name">
		<spring:message code="category.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	
	<form:label path="description">
		<spring:message code="category.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="picture">
		<spring:message code="category.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors cssClass="error" path="picture" />
	<br />
	
	
	
	<form:label path="parentCategory">
			<spring:message code="category.parentCategory" />:
	</form:label>
		<form:select id="parentCategoryDropdown" path="parentCategory">
			<form:option value="0" label="----"/>
			<form:options items="${categories}" itemValue="id" itemLabel="name" />
			<form:errors cssClass="error" path="parentCategory" />
			<br />
	</form:select>
	
	<jstl:if test = "${category.id!=0 }">
	<spring:message  code="category.tags" />:
	<br>


	<jstl:set var="indice" value="${-1}"/>
	<table>
	<thead>
 		<tr>
 		<spring:message code="category.tags" var="tagheader" />
    	<th>${tagheader}</th>
    	<th>${tagheader}</th>
  		</tr>
 	</thead>
	<jstl:forEach var="tag" items="${tags}" varStatus="counter">
		<jstl:if test="${(counter.count mod 2) == 0}">
			<jstl:set value="even" var="cssClass"></jstl:set>
		</jstl:if>
		<jstl:if test="${(counter.count mod 2) == 1}">
			<jstl:set value="odd" var="cssClass"></jstl:set>
		</jstl:if>
			<tr class="${cssClass}">
		<td>
	<jstl:set var="position" value="${indice +1}"/>
		<a href="category/administrator/deleteTag.do?categoryId=${category.id}&tagIndex=${position}">
		<spring:message	code="category.tag.delete" />
		</a>
		</td>
		<td>
		<jstl:out value="${tag}"/>
		</td>
	</tr>
	<jstl:set var="indice" value="${indice +1}"/>
	</jstl:forEach>
</table>
<a href="category/administrator/createtag.do?categoryId=${category.id}" >
	<spring:message code="category.tag.create" />
</a>
<br>

</jstl:if>
<br>
	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />&nbsp; 
	<jstl:if test="${recipe.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
	onclick="javascript:window.location.href='category/list.do'" />
		<br />

	

</form:form>
