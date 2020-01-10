<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<div align="center" style="margin: 20px">
    <table border="1" >

        <%--				cellpadding="5" cellspacing="5"--%>
        <%--For displaying Previous link except for the 1st page --%>
        <tr>
            <c:if test="${currentPage != 1}">
                <td><a href="/books/${param.callingPage}?currentPage=${currentPage - 1}">Previous</a></td>
            </c:if>

            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
            <c:forEach begin="1" end="${numberOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <td style="background-color: yellow">${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="/books/${param.callingPage}?currentPage=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <%--For displaying Next link --%>
            <c:if test="${currentPage lt numberOfPages}">
                <td><a href="/books/${param.callingPage}?currentPage=${currentPage + 1}">Next</a></td>
            </c:if>
        </tr>
    </table>
</div>
</html>
