
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
	<form:hidden path="tags" />
	<form:hidden path="subCategory" />
	<form:hidden path="Belongs" />
	
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

	<input type="submit" name="Accept"
		value="<spring:message code="category.accept" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
			onclick="javascript:window.location.href='category/list.do'" />
		
	<br />


</form:form>
