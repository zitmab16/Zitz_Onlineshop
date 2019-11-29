<%-- 
    Document   : shop
    Created on : 20.11.2019, 10:58:32
    Author     : vizug
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alpaca Shop</title>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    ID
                </td>
                <td>
                    Type
                </td>
                <td>
                    Price
                </td>
                <td>
                    Current Amount
                </td>
            </tr>
            <c:forEach items="${requestScope.alpacas}" var="alpaca">
                <tr>
                    <td>
                        <c:out value="${alpaca.id}"/>
                    </td>
                    <td>
                        <c:out value="${alpaca.typ}"/>
                    </td>
                    <td>
                        <c:out value="${alpaca.price}"/>
                    </td>
                    <td id="<c:out value="${alpaca.id}"/>">
                        <c:out value="${alpaca.amount}"/>
                    </td>
                    <td>
                        <input type="button" value="+" onClick="increaseAlpacaAmount(${alpaca.id})" />
                    </td>
                    <td>
                        <input type="button" value="-" onClick="decreaseAlpacaAmount(${alpaca.id})" />
                    </td>
                </tr>    
            </c:forEach>

        </table>
        <form></form>
        <script src="shop.js" type="text/javascript"></script>
    </body>
    
</html>
