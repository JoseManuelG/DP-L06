
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<spring:message	code="bill.dateOfCreation" />: &nbsp; <jstl:out value="${bill.dateOfCreation}" />
<br>
<spring:message	code="bill.cost" />: &nbsp; <jstl:out value="${bill.cost}"/>
<br>
<spring:message	code="bill.dateOfPay" />: &nbsp; <jstl:out value="${bill.dateOfPay}"/>
<br>
<spring:message	code="bill.description" />: &nbsp; <jstl:out value="${bill.description}"/>
<br>
<jstl:if test="${unPaid}">
		<a href="bill/sponsor/edit.do?billId=${bill.id}">
			<spring:message	code="bill.edit" />
		</a>
</jstl:if>