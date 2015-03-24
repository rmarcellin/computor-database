<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
</head>
<body>

	<c:set scope="session" var="computers" value="" />

    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${ page.size }" /> Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                	<c:url var="addComputer" value="/AddComputer" />
                    <a class="btn btn-success" id="addComputer" href="${ addComputer }">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                    
                	<c:forEach var="computer" items="${ page.pageElements }">                	
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer.html" onclick="">
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
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">        	
            <ul class="pagination">
                <li>
                	<c:url var="pageFooterLeft" value="/AllComputers?pageNum=${ page.page }&startP=${ page.previousPage }" />
                    <a href="${ pageFooterLeft }" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <c:choose>
	              <c:when test="${ page.pageStart <= page.nextPage}">
		              <c:forEach var="pageNbr" begin="${ page.pageStart }" end="${ page.nextPage }" step="1">  
		              		<c:url var="urlNum" value="/AllComputers?pageNum=${ pageNbr }&startP=${ page.pageStart }" />
		              		<li><a href="${ urlNum }"> <c:out value="${ pageNbr }" /> </a></li>          
		              </c:forEach>
	              </c:when>
	              <c:otherwise>		<!-- In this case, we are at the end of the page footer  -->
	              	<!-- From pageStart to the last pagefooter -->	           
	              	<c:forEach var="pageNbr" begin="${ page.pageStart }" end="${ page.pageNumbers }" step="1">  
	              		<c:url var="urlNum" value="/AllComputers?pageNum=${ pageNbr }&startP=${ page.pageStart }" />
	              		<li><a href="${ urlNum }"> <c:out value="${ pageNbr }" /> </a></li>          
		            </c:forEach>		           		            
	              </c:otherwise>
	              
              </c:choose>
              <li>
              	<c:url var="pageFooterRight" value="/AllComputers?pageNum=${ page.page }&startP=${ page.nextPage }" />
              	<a href="${ pageFooterRight }" aria-label="Next">
                   	<span aria-hidden="true">&raquo;</span>
               	</a>
            	</li>
        	</ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>

    </footer>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/dashboard.js"></script>

</body>
</html>