<%-- 
    Document   : error
    Created on : 01.12.2019, 15:33:33
    Author     : vizug
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <p>
            <c:out value="${requestScope.errors}"/>
        </p>
    </body>
</html>
