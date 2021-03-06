<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="page"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" 	rel="stylesheet" 	media="screen">
<link href="css/font-awesome.css" 	rel="stylesheet" 	media="screen">
<link href="css/main.css" 			rel="stylesheet" 	media="screen">
<link href="css/dashboard.css" 		rel="stylesheet" 	media="screen">
</head>
<body>

	<c:set scope="session" var="computers" value="" />
	<c:url var="thisPage" value="/Dashboard" />
	
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${ thisPage }"> Application -
				<spring:message code="computer.db"/> </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ page.size }" />
				<spring:message code="computers.found"/>
			</h1>
			<div id="actions" class="form-horizontal">
				<!-- /!\ SEARCH /!\  -->
				<div class="pull-left">
					<form id="searchForm" action="SearchServlet" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="search.name"/>" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="firlter.name"/>"
							class="btn btn-primary" />
					</form>					
				</div>
				<div class="pull-right">
					<c:url var="addComputer" value="/AddComputer" />
					<a class="btn btn-success" id="addComputer" href="${ addComputer }">
						<spring:message code="add.computer" />
					</a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">
						<spring:message code="edit" />
					</a>
				</div>
			</div>
		</div>

		<!-- /!\ DELETION /!\  -->
		<form id="deleteForm" action="DeleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>	
							<spring:message code="computer.name" /> &nbsp;
							<!-- /!\ ORDER-BY /!\  -->
							<c:url var="orderByName" value="/Dashboard">
								<c:param name="pageNum" value="${ page.page }" />
								<c:param name="startP" value="${ page.pageStart }" />
								<c:param name="sortKey" value="name"/>
							</c:url>				
							<a href="${ orderByName }">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 						
						</th>
						<th>
							<spring:message code="intro.date"/> &nbsp;
							<!-- /!\ ORDER-BY /!\  -->
							<c:url var="orderByIntroduced" value="/Dashboard">
								<c:param name="pageNum" value="${ page.page }" />
								<c:param name="startP" value="${ page.pageStart }" />
								<c:param name="sortKey" value="introduced"/>
							</c:url>			
							<a href="${ orderByIntroduced }">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 
							
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
							<spring:message code="disco.date"/> &nbsp;
							<!-- /!\ ORDER-BY /!\  -->
							<c:url var="orderByDiscontinued" value="/Dashboard">
								<c:param name="pageNum" value="${ page.page }" />
								<c:param name="startP" value="${ page.pageStart }" />
								<c:param name="sortKey" value="discontinued"/>
							</c:url>	
							<a href="${ orderByDiscontinued }">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 
						</th>
						<!-- Table header for Company -->
						<th>
							<spring:message code="company"/> &nbsp;
							<!-- /!\ ORDER-BY /!\  -->	
							<c:url var="orderByCompanyId" value="/Dashboard">
								<c:param name="pageNum" value="${ page.page }" />
								<c:param name="startP" value="${ page.pageStart }" />
								<c:param name="sortKey" value="company_id"/>
							</c:url>	
							<a href="${ orderByCompanyId }">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 
						</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">

					<c:forEach var="computer" items="${ page.pageElements }">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${ computer.id }"></td>
							<td>
								
								<c:url var="editComputer" 
										value="/EditComputer?
												id=${ computer.id }
												&name=${ computer.name }
												&intro=${ computer.introduced }
												&disco=${ computer.discontinued }
												&companyName=${ computer.companyName }
										" />
								<a href="${ editComputer }" onclick=""> 
									<c:out value="${ computer.name }" />
								</a>
							</td>
							<td><c:out value="${ computer.introduced }" /></td>
							<td><c:out value="${ computer.discontinued }" /></td>
							<td><c:out value="${ computer.companyName }" /></td>

						</tr>
					</c:forEach>

				</tbody>
			</table>
			<c:if test="${ errorsNbr != 0 }">
				<ul id="_errors">
					<c:forEach var="error" items="${ errors }">
						<li>
							<c:out value="${ error }" />
						</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<page:page pageNumbers="${ page.pageNumbers }"
			nextPage="${ page.nextPage }" previousPage="${ page.previousPage }"
			url="/Dashboard" page="${ page.page }"
			pageStart="${ page.pageStart }" />
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>