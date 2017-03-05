

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<spring:message code="socialIdentity.picture" />:
<br/>
<img src="${socialIdentity.picture}" alt="<spring:message code="socialIdentity.notIMG" /> "/>
<br/>


<spring:message code="socialIdentity.nick" />:	<jstl:out value="${socialIdentity.nick}"/>
<br/>
<spring:message code="socialIdentity.socialNetwork" />:<jstl:out value="${socialIdentity.socialNetwork}"/>
<br/>
<a href="${socialIdentity.link}"><spring:message code="socialIdentity.link" /></a>
