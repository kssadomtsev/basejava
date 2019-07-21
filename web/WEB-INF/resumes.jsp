<%@ page import="com.mysite.webapp.model.Resume" %><%--
  Created by IntelliJ IDEA.
  User: Konstantin
  Date: 21.07.2019
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>All resumes list</title>
</head>
<body>
<table border="2">
    <tr>
        <td>Resume UUID</td>
        <td>Full Name</td>
    </tr>
    <c:forEach items="${storage.getAllSorted()}" var="item">
        <tr>
            <td><c:out value="${item.getFullName()}"/></td>
            <td><a href="?uuid=${item.getUuid()}"><c:out value="${item.getUuid()}"/></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
