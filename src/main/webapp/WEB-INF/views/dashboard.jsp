<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="p"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<link href="css/dashboard.css" rel="stylesheet" media="screen">
</head>
<body>

	<c:set scope="session" var="computers" value="" />
	<c:url var="thisPage" value="/AllComputers" />
	
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${ thisPage }"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${ page.size }" />
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="SearchServlet" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>					
				</div>
				<div class="pull-right">
					<c:url var="addComputer" value="/AddComputer" />
					<a class="btn btn-success" id="addComputer" href="${ addComputer }">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
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
							Computer name &nbsp;				
							<a href="#">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 						
						</th>
						<th>
							Introduced date &nbsp;				
							<a href="#">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 
							
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
							Discontinued date &nbsp;				
							<a href="#">
								<span class="glyphicon glyphicon-sort"></span>
							</a> 
						</th>
						<!-- Table header for Company -->
						<th>
							Company &nbsp;				
							<a href="#">
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
		<p:page pageNumbers="${ page.pageNumbers }"
			nextPage="${ page.nextPage }" previousPage="${ page.previousPage }"
			url="/AllComputers" page="${ page.page }"
			pageStart="${ page.pageStart }" />
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>

</body>
</html>