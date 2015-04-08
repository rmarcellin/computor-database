<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<c:url var="thisPage" value="/Dashboard" />
			<a class="navbar-brand" href="${ thisPage }"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: 
						<c:out value="${ id }" />
					</div>
					<h1>Edit Computer</h1>

					<form action="EditComputer" method="POST">
						<input type="hidden" id="id" name="id" value="${ id }" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName" name="computerName"
									placeholder="Computer name" value="${ name }">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced" name="introduced"
									placeholder="Introduced date"
									value="${ intro }">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date"
									value="${ disco }">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> 
								<select class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach var="company" items="${ companies }">
                                    	<option value="${ company.id }">
                                    		<c:out value="${ company.name }" />
                                    	</option>
                                    </c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="${ thisPage }" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>