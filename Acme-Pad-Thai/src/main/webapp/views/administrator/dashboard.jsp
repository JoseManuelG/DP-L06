<%--
 * action-1.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<!--  <spring:message  code="administrator.avgOfPromotedClass" />: <jstl:out value="${avgOfPromotedClass}" />-->
<br>
<!--  <spring:message  code="administrator.avgOfDemotedClass" />: <jstl:out value="${avgOfDemotedClass}"/>-->
<spring:message  code="administrator.avgOfPromotedClass" />
<br>
<jstl:set var="index" value="${-1}"/>
<jstl:forEach var="avgOfPromoted" items="${avgOfPromotedClass}" >
	<jstl:set var="index" value="${index+1}"/>
	<jstl:out value="${cooksOfPromotedClass[index].name}"/> : 
	<jstl:out value="${avgOfPromoted}"/>
</jstl:forEach>
<br/>
<spring:message  code="administrator.avgOfDemotedClass" />
<br>
<jstl:set var="index" value="${-1}"/>
<jstl:forEach var="avgOfDemoted" items="${avgOfDemotedClass}" >
	<jstl:set var="index" value="${index+1}"/>
	<jstl:set var="cook" value="${cooksOfDemotedClass[index]}"/>
	<jstl:out value="${cook.name}"/> : 
	<jstl:out value="${avgOfDemoted}"/>
</jstl:forEach>
<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="listOfCooksByPromotedMasterClass" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.cook.name" var="cookName" />
	<display:column property="name" title="${cookName}" sortable="false"/>

</display:table>
<br>
<spring:message  code="administrator.numberOfMasterClassesPromoted" />: <jstl:out value="${numberOfMasterClassesPromoted}"/>
<br>
<spring:message  code="administrator.avgOfLearningMaterialPerMasterClassByPresentation" />: <jstl:out value="${avgOfLearningMaterialPerMasterClassByPresentation}"/>
<br>
<spring:message  code="administrator.avgOfLearningMaterialPerMasterClassByText" />: <jstl:out value="${avgOfLearningMaterialPerMasterClassByText}"/>
<br>
<spring:message  code="administrator.avgOfLearningMaterialPerMasterClassByVideo" />: <jstl:out value="${avgOfLearningMaterialPerMasterClassByVideo}"/>
<br>
<spring:message  code="administrator.stddevOfMasterClassPerCook" />: <jstl:out value="${stddevOfMasterClassPerCook}"/>
<br>
<spring:message  code="administrator.avgOfMasterClassPerCook" />: <jstl:out value="${avgOfMasterClassPerCook}"/>
<br>
<spring:message  code="administrator.minOfMasterClassPerCook" />: <jstl:out value="${minOfMasterClassPerCook}"/>
<br>
<spring:message  code="administrator.maxOfMasterClassPerCook" />: <jstl:out value="${maxOfMasterClassPerCook}"/>
<br>

<table>
<thead>
 <tr>
 	<spring:message code="administrator.listOfCompaniesThatSpentMoreThan90" var="companiesThatSpentMoreThan90Header" />
    <th>${companiesThatSpentMoreThan90Header}</th>
  </tr>
 </thead>
	<jstl:forEach var="companyThatSpentMoreThan90" items="${listOfCompaniesThatSpentMoreThan90}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<td>
		<jstl:out value="${companyThatSpentMoreThan90}"/>
		</td>
	</tr>
	</jstl:forEach>
</table>
<br>
<table>
<thead>
 <tr>
 	<spring:message code="administrator.listOfCompaniesThatSpentLessThanTheAvg" var="companiesThatSpentLessThanTheAvgHeader" />
    <th>${companiesThatSpentLessThanTheAvgHeader}</th>
  </tr>
 </thead>
	<jstl:forEach var="companyThatSpentLessThanTheAvg" items="${listOfCompaniesThatSpentLessThanTheAvg}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<td>
		<jstl:out value="${companyThatSpentLessThanTheAvg}"/>
		</td>
	</tr>
	</jstl:forEach>
</table>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="sponsorsWithNoCampaignOnThreeMonths" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.sponsor.name" var="sponsorName" />
	<display:column property="name" title="${sponsorName}" sortable="false"/>

</display:table>
<br>
<spring:message  code="administrator.avgOfUnpaidBills" />: <jstl:out value="${avgOfUnpaidBills}"/>
<br>
<spring:message  code="administrator.avgOfPaidBills" />: <jstl:out value="${avgOfPaidBills}"/>
<br>
<spring:message  code="administrator.sttdevOfUnpaidBills" />: <jstl:out value="${sttdevOfUnpaidBills}"/>
<br>
<spring:message  code="administrator.sttdevOfPaidBills" />: <jstl:out value="${sttdevOfPaidBills}"/>
<br>
<table>
<thead>
 <tr>
 	<spring:message code="administrator.listOfCompaniesByMonthlyBills" var="companiesByMonthlyBillsHeader" />
    <th>${companiesByMonthlyBillsHeader}</th>
  </tr>
 </thead>
	<jstl:forEach var="companyByMonthlyBills" items="${listOfCompaniesByMonthlyBills}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<td>
		<jstl:out value="${companyByMonthlyBills}"/>
		</td>
	</tr>
	</jstl:forEach>
</table>
<br>
<table>
<thead>
 <tr>
 	<spring:message code="administrator.listOfCompaniesByCampaignsOrganiced" var="companiesByCampaignsOrganiced" />
    <th>${companiesByCampaignsOrganiced}</th>
  </tr>
 </thead>
	<jstl:forEach var="companyByCampaignsOrganiced" items="${listOfCompaniesByCampaignsOrganiced}" varStatus="counter">
	<jstl:if test="${(counter.count mod 2) == 0}">
	<jstl:set value="even" var="cssClass"></jstl:set>
	</jstl:if>
	<jstl:if test="${(counter.count mod 2) == 1}">
	<jstl:set value="odd" var="cssClass"></jstl:set>
	</jstl:if>
	<tr class="${cssClass}">
		<td>
		<jstl:out value="${companyByCampaignsOrganiced}"/>
		</td>
	</tr>
	</jstl:forEach>
</table>
<br>
<spring:message  code="administrator.minOfActiveCampaignperSponsor" />: <jstl:out value="${minOfActiveCampaignperSponsor}"/>
<br>
<spring:message  code="administrator.maxOfActiveCampaignperSponsor" />: <jstl:out value="${maxOfActiveCampaignperSponsor}"/>
<br>
<spring:message  code="administrator.avgOfActiveCampaignperSponsor" />: <jstl:out value="${avgOfActiveCampaignperSponsor}"/>
<br>
<spring:message  code="administrator.minOfCampaignperSponsor" />: <jstl:out value="${minOfCampaignperSponsor}"/>
<br>
<spring:message  code="administrator.maxOfCampaignperSponsor" />: <jstl:out value="${maxOfCampaignperSponsor}"/>
<br>
<spring:message  code="administrator.avgOfCampaignperSponsor" />: <jstl:out value="${avgOfCampaignperSponsor}"/>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="usersForPopularityOfTheirRecipes" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.user.name" var="userName" />
	<display:column property="name" title="${userName}" sortable="false"/>

</display:table>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="usersForPoplarityDesc" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.user.name2" var="userName" />
	<display:column property="name" title="${userName}" sortable="false"/>

</display:table>
<br>
<spring:message  code="administrator.stdDevOfIngredientsPerRecipe" />: <jstl:out value="${stdDevOfIngredientsPerRecipe}"/>
<br>
<spring:message  code="administrator.avgOfIngredientsPerRecipe" />: <jstl:out value="${avgOfIngredientsPerRecipe}"/>
<br>
<spring:message  code="administrator.stdDevOfStepsForRecipes" />: <jstl:out value="${stdDevOfStepsForRecipes}"/>
<br>
<spring:message  code="administrator.avgOfStepsForRecipes" />: <jstl:out value="${avgOfStepsForRecipes}"/>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="contestsWithMoreRecipes" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.contest.title" var="contestTitle" />
	<display:column property="tittle" title="${contestTitle}" sortable="false"/>

</display:table>
<br>
<spring:message  code="administrator.minRecipesForContests" />: <jstl:out value="${minRecipesForContests}"/>
<br>
<spring:message  code="administrator.maxRecipesForContests" />: <jstl:out value="${maxRecipesForContests}"/>
<br>
<spring:message  code="administrator.avgRecipesForContests" />: <jstl:out value="${avgRecipesForContests}"/>
<br>
<display:table pagesize="5" class="displaytag" keepStatus="false"
	name="usersWithMoreRecipes" requestURI="/administrator/dashboard.do" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="administrator.user.name3" var="userName" />
	<display:column property="name" title="${userName}" sortable="false"/>

</display:table>
<br>
<spring:message  code="administrator.avgRecipesForUsers" />: <jstl:out value="${avgRecipesForUsers}"/>
<br>
<spring:message  code="administrator.minRecipesForUsers" />: <jstl:out value="${minRecipesForUsers}"/>
<br>
<spring:message  code="administrator.maxRecipesForUsers" />: <jstl:out value="${maxRecipesForUsers}"/>
<br>











