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


<form:form action="learningmaterial/cook/edit.do"
	modelAttribute="learningMaterial">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="masterClass" />
	<form:hidden path="attachments" />
	

	<form:label path="title">
		<spring:message code="learningmaterial.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="summary">
		<spring:message code="learningmaterial.summary" />:
	</form:label>
	<form:input path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />
	<form:label path="type">
		<spring:message code="learningmaterial.type" />
	</form:label>
	
	<select id="type" name="type">
    	<option value="text">Text</option>
    	<option value="presentation">Presentation</option>
    	<option value="video">Video</option>
    </select>
	<br />
	<form:label path="data">
		<spring:message code="learningmaterial.data" />:
	</form:label>
	<form:input path="data" />
	<form:errors cssClass="error" path="data" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="learningmaterial.save" />" />&nbsp; 
	<jstl:if test="${announcement.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="learningmaterial.delete" />" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="masterclass.cancel" />"
		onclick="javascript:window.location.href='masterclass/view.do?masterClassId=${learningMaterial.masterClass.id}'" />
	<br />
</form:form>
