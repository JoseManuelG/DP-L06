
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message	code="messag.sender" />: &nbsp; <jstl:out value="${messag.sender}" />
<br>
<spring:message	code="messag.recipient" />: &nbsp; <jstl:out value="${messag.recipient}" />
<br>
<spring:message	code="messag.sendingMoment" />: &nbsp; <jstl:out value="${messag.sendingMoment}" />
<br>
<spring:message	code="messag.subject" />: &nbsp; <jstl:out value="${messag.subject}" />
<br>
<spring:message	code="messag.priority" />: &nbsp; <jstl:out value="${messag.priority}" />
<br>
<spring:message	code="messag.body" />: &nbsp; <jstl:out value="${messag.body}" />
<br>

<form:form action="message/view.do" modelAttribute="messag">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	<form:hidden path="sendingMoment" />
	<form:hidden path="subject" />
	<form:hidden path="priority" />
	<form:hidden path="body" />
	
	<form:label path="folder">
    <spring:message code="messag.folder" />:
  	</form:label>
 	<form:select id="foldersDropdown" path="folder">
 		<spring:message code="messag.folder" />:
 		<form:options items="${folders}" itemValue="id" itemLabel="name"/>
 		<form:errors cssClass="error" path="folder" />
 	</form:select>
	<br />
	
	<input type="submit" name="save2"
		value="<spring:message code="messag.save" />" />&nbsp; 
	<input type="submit" name="delete"
		value="<spring:message code="messag.delete" />"
		onclick="return confirm('<spring:message code="messag.confirm.delete" />')" />&nbsp;
	<input type="button" name="cancel"
		value="<spring:message code="messag.cancel" />"
		onclick=" javascript:window.location.href='folder/list.do'" />	<br />

</form:form>
