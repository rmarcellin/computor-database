<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="pageStart" required="true" type="java.lang.Integer" %>
<%@ attribute name="nextPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="previousPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="page" required="true" type="java.lang.Integer" %>
<%@ attribute name="pageNumbers" required="true" type="java.lang.Integer" %>

<!-- In this tag file, FOOTER_PAGE_SIZE equals to 5 -->

<div class="container text-center">
   	<ul class="pagination">
   		<!-- Page précédent -->
   		<li>
   			<c:url var="pageFooterLeft" value="${ url }?pageNum=${ page }&startP=${ previousPage }" />
       		<a href="${ pageFooterLeft }" aria-label="Previous">
         		<span aria-hidden="true">&laquo;</span>
     		</a>
     	</li>
     	<!-- Numéros des pages -->
     	<c:choose>
     		<c:when test="${ pageStart <= nextPage }">
     			<c:forEach var="pageNbr" begin="${ pageStart }" end="${ nextPage }" step="1">  
	           		<c:url var="urlNum" value="${ url }?pageNum=${ pageNbr }&startP=${ pageStart }" />
	           		<li><a href="${ urlNum }"> <c:out value="${ pageNbr }" /> </a></li>          
		    	</c:forEach>     		
     		</c:when>
     		<c:otherwise>		<!-- In this case, we are at the end of the page footer  -->
	        	<!-- From pageStart to the last pagefooter -->	   
	        	<c:forEach var="pageNbr" begin="${ pageStart }" end="${ pageNumbers }" step="1">
	        		<c:url var="urlNum" value="${ url }?pageNum=${ pageNbr }&startP=${ pageStart }" />
	        		<li><a href="${ urlNum }"> <c:out value="${ pageNbr }" /> </a></li>
	        	</c:forEach>		           		            
	 		</c:otherwise>
     	</c:choose>
     	<!-- Page suivant -->
     	<li>
        	<c:url var="pageFooterRight" value="${ url }?pageNum=${ page }&startP=${ nextPage }" />
            <a href="${ pageFooterRight }" aria-label="Next">
            	<span aria-hidden="true">&raquo;</span>
            </a>
        </li>
   	</ul>
   	<div class="btn-group btn-group-sm pull-right" role="group" >
        <button type="button" class="btn btn-default">10</button>
        <button type="button" class="btn btn-default">50</button>
        <button type="button" class="btn btn-default">100</button>
    </div>
</div>
