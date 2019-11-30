<%-- 
    Document   : order
    Created on : 20.11.2019, 10:58:21
    Author     : vizug
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders</title>
    </head>
    <body>
        <table>
            <tr>
                <td>Datetime</td>
                <td>OrderID</td>
                <td>Details</td>
            </tr>
            <c:forEach items="${requestScope.orders}" var="order">
                <tr>
                    <td>
                        <c:out value="${order.datetime}"/>
                    </td>
                    <td>
                        <c:out value="${order.id}"/>
                    </td>
                    <td>
                        <input type="button" value="..." onClick="showDetails(<c:out value="${order.id}"/>)"/>
                    </td>
                </tr>


            </c:forEach>


        </table>

    <div id="result">

    </div>
    <script src="orderDetails.js" type="text/javascript"></script>
</body>
</html>
