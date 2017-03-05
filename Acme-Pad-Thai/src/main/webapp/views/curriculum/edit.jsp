
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="curriculum/nutritionist/edit.do" modelAttribute="curriculum">

	<form:hidden path="id" />
		<form:hidden path="version" />
	<form:hidden path="endorsers" />
	
	<form:label path="picture">
		<spring:message code="curriculum.picture" />:
	</form:label>
	<form:input path="picture" />
	<form:errors cssClass="error" path="picture" />
	<br />
	
	<form:label path="educationSection">
		<spring:message code="curriculum.education" />:
	</form:label>
	<form:input path="educationSection" />
	<form:errors cssClass="error" path="educationSection" />
	<br />

	<form:label path="experienceSection">
		<spring:message code="curriculum.experience" />:
	</form:label>
	<form:input path="experienceSection" />
	<form:errors cssClass="error" path="experienceSection" />
	<br />

	<form:label path="hobbiesSection">
		<spring:message code="curriculum.hobbies" />:
	</form:label>
	<form:input path="hobbiesSection" />
	<form:errors cssClass="error" path="hobbiesSection" />
	<br />
	

<input type="submit" name="save"
		value="<spring:message code="curriculum.save" />" />&nbsp; 
		
	<jstl:if test="${curriculum.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="curriculum.delete" />"
			onclick="return confirm('<spring:message code="curriculum.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="curriculum.cancel" />"
		onclick="javascript:window.location.href='curriculum/nutritionist/view.do'" />	<br />

</form:form>
