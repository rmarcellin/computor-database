<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<style>
	.error {
	    color: #ff0000;
	    font-style: italic;
	    font-weight: bold;
	}
</style>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
        	<c:url var="allComputer" value="/Dashboard" />
            <a class="navbar-brand" href="${ allComputer }"> Application - <spring:message code="computer.db"/> </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.computer"/></h1>
                    
                    <form:form modelAttribute="computerForm" action="AddComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">
                                	<spring:message code="computer.name" />
                                	<c:set var="namePlaceholder">
                                		<spring:message code="computer.name" />
                                	</c:set>
                                </label>
                                <input type="text" class="form-control" id="computerName" name="name" placeholder="${ namePlaceholder }">
                                <form:errors path="name" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced">
                                	<spring:message code="intro.date"/>
                                	<c:set var="introPlaceholder">
                                		<spring:message code="intro.date" />
                                	</c:set>
                                </label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="${ introPlaceholder }">
                                <form:errors path="introduced" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">
                                	<spring:message code="disco.date"/>
                                	<c:set var="discoPlaceholder">
                                		<spring:message code="disco.date" />
                                	</c:set>                                
                                </label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="${ discoPlaceholder }">
                                <form:errors path="discontinued" cssClass="error"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId">
                                	<spring:message code="company"/>
                                	<c:set var="companyPlaceholder">
                                		<spring:message code="company" />
                                	</c:set>
                                </label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<c:forEach var="company" items="${ companies }">
                                    	<option value="${ company.id }">
                                    		<c:out value="${ company.name }" />
                                    	</option>
                                    </c:forEach>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="add"/>" class="btn btn-primary">
                            <spring:message code="or"/>
                            <a href="${ allComputer }" class="btn btn-default"><spring:message code="cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>