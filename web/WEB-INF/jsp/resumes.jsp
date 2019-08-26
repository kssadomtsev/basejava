<%--
  Created by IntelliJ IDEA.
  User: Konstantin
  Date: 21.07.2019
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.mysite.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${storage}" var="resume">
            <jsp:useBean id="resume" type="com.mysite.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" width="30" height="30"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" width="30" height="30"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<div>
    <a href="resume?action=add">Добавить новое резюме</a>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
