<%--
  Created by IntelliJ IDEA.
  User: Konstantin
  Date: 21.07.2019
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <td>Phone</td>
        <td>Email</td>
    </tr>
        <tr>
            <td>"${resume.getFullName()}"</td>
            <td>"${resume.getUuid()}"</td>
            <td>"${resume.getContact("PHONE")}"</td>
            <td>"${resume.getContact("EMAIL")}"</td>
        </tr>
</table>
</body>
</html>
