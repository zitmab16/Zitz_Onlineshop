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
                <th>
                    ID
                </th>
                <th>
                    Type
                </th>
                <th>
                    Price
                </th>
                <th>
                    Current Amount
                </th>
                <th>
                    Increase
                </th>
                <th>
                    Decrease
                </th>
            </tr>
            <c:forEach items="${requestScope.alpacas}" var="alpaca">
                <tr>
                    <td>
                        <c:out value="${alpaca.id}"/>
                        <link href="Design.css" rel="stylesheet" type="text/css"/>
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
        <form method="POST" >
            <input type="submit" value="order"  class="button" formaction="/OnOrderServlet"/>
            <input type="submit" value="show Orders" class="button" formaction="/ShowOrdersServlet"/>
        </form>
        <script src="shop.js" type="text/javascript"></script>
    </body>
    <link href="Design.css" rel="stylesheet" type="text/css"/>
</html>
