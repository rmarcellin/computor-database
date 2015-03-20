<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<meta charset="UTF-8">
<title>computer database</title>
</head>
<body>
	<p>Hello Mars Rgo <br/> Quelques modifs </p>
	<c:out value="Hello Mars" /> <br/>
	<c:set var="salary" scope="session" value="${2000*2}" />
	<c:if test="${salary > 2000}">
		<p>	My salary is: <c:out value="${salary}" /><p>
	</c:if>	
	<a href="WEB-INF/views/dashboard.html">Try me</a>
	
</body>
</html>