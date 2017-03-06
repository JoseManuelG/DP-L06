<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>


<div>
	<a href="<spring:url value='/' />">
		<img src="images/logo.jpg" width="363" height="130" alt="Acme-Pad-Thai Co., Inc." />
	</a>
</div>


<div>
	<c:set var="user" scope="session"
		value="${SecurityContextHolder.getContext().getAuthentication().getPrincipal()}" />
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->

		<li><a class="fNiv"><spring:message code="master.page.recipe" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="category/listrecipefinal.do"><spring:message
							code="master.page.recipe.list" /></a></li>
				<li><a href="recipe/search.do"><spring:message
							code="master.page.recipe.search" /></a></li>
				<li><a href="ajax.do"><spring:message
							code="master.page.recipe.ajax" /></a></li>
			
			</ul></li>
		<li><a class="fNiv"><spring:message code="master.page.user" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="user/list.do"><spring:message
							code="master.page.user.list" /></a></li>
				<li><a href="user/search.do"><spring:message
							code="master.page.user.search" /></a></li>
				<li><a href="nutritionist/list.do"><spring:message
							code="master.page.nutritionist.list" /></a></li>
				<li><a href="nutritionist/search.do"><spring:message
							code="master.page.nutritionist.search" /></a></li>
				<li><a href="cook/list.do"><spring:message
							code="master.page.cook.list" /></a></li>
				<li><a href="cook/search.do"><spring:message
							code="master.page.cook.search" /></a></li>
				<security:authorize access="hasRole('ADMIN')">
					<li><a href="sponsor/list.do"><spring:message
								code="master.page.sponsor.list" /></a></li>
					<li><a href="sponsor/search.do"><spring:message
								code="master.page.sponsor.search" /></a></li>
				</security:authorize>
			</ul></li>
		<li><a class="fNiv"><spring:message
					code="master.page.contest" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="contest/list.do"><spring:message
							code="master.page.contest.list" /></a></li>
			</ul></li>
		<li><a class="fNiv"><spring:message
					code="master.page.masterclass" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="masterclass/list.do"><spring:message
							code="master.page.masterclass.list" /></a></li>
			</ul></li>
		<li><a class="fNiv"><spring:message
					code="master.page.category" /></a>
			<ul>
				<li class="arrow"></li>
				<li><a href="category/list.do"><spring:message
							code="master.page.category.list" /></a></li>
			</ul></li>
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/view.do"><spring:message
								code="master.page.user.view" /></a></li>
					<li><a href="user/edit.do"><spring:message
								code="master.page.user.data.edit" /></a></li>
					<li><a href="recipe/user/myRecipes.do"><spring:message
								code="master.page.user.recipes" /></a></li>
					<li><a href="recipe/user/listcontest.do"><spring:message
								code="master.page.customer.listcontest" /></a></li>
					<li><a href="recipe/latestRecipes.do"><spring:message
								code="master.page.customer.latestrecipes" /></a></li>
					<li><a href="folder/list.do"><spring:message
								code="master.page.sponsor.folders" /></a></li>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('NUTRITIONIST')">
			<li><a class="fNiv"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="nutritionist/view.do"><spring:message
								code="master.page.nutritionist.view" /></a></li>
					<li><a href="nutritionist/edit.do"><spring:message
								code="master.page.nutritionist.data.edit" /></a></li>
					<li><a href="curriculum/nutritionist/view.do"><spring:message
								code="master.page.nutritionist.curriculum" /></a></li>
					<li><a href="folder/list.do"><spring:message
								code="master.page.sponsor.folders" /></a></li>
					<li><a href="recipe/latestRecipes.do"><spring:message
								code="master.page.customer.latestrecipes" /></a></li>
					<li><a href="ingredient/nutritionist/list.do"><spring:message
								code="master.page.nutritionist.ingredient" /></a></li>
					<li><a href="property/nutritionist/list.do"><spring:message
								code="master.page.nutritionist.property" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('COOK')">
			<li><a class="fNiv"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="cook/view.do"><spring:message
								code="master.page.cook.view" /></a></li>
					<li><a href="cook/edit.do"><spring:message
								code="master.page.cook.data.edit" /></a></li>
					<li><a href="masterclass/listByCook.do"><spring:message
								code="master.page.cook.masterclass" /></a></li>
					<li><a href="folder/list.do"><spring:message
								code="master.page.sponsor.folders" /></a></li>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsor/view.do"><spring:message
								code="master.page.sponsor.view" /></a></li>
					<li><a href="sponsor/edit.do"><spring:message
								code="master.page.sponsor.data.edit" /></a></li>
					<li><a href="campaign/sponsor/list.do"><spring:message
								code="master.page.sponsor.campaigns" /></a></li>
					<li><a href="bill/sponsor/list.do"><spring:message
								code="master.page.sponsor.bills" /></a></li>
					<li><a href="folder/list.do"><spring:message
								code="master.page.sponsor.folders" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message
								code="master.page.configuration.dashboard" /></a></li>
					<li><a href="configuration/admin/edit.do"><spring:message
								code="master.page.configuration.edit" /></a></li>
					<li><a href="bill/admin/listAll.do"><spring:message
								code="master.page.configuration.bills" /></a></li>
					<li><a href="folder/list.do"><spring:message
								code="master.page.sponsor.folders" /></a></li>
					<li><a href="cook/administrator/create.do"><spring:message
								code="master.page.configuration.registerCook" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
					

				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv"><spring:message
						code="master.page.security" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="security/login.do"><spring:message
								code="master.page.login" /></a></li>
					<li><a href="security/register.do"><spring:message
								code="master.page.security.register" /></a></li>
				</ul></li>
		</security:authorize>

	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
	<!--  <a href="${requestScope['javax.servlet.forward.request_uri']}?language=en">en</a> | <a href="${requestScope['javax.servlet.forward.request_uri']}?language=es">es</a> -->
</div>

