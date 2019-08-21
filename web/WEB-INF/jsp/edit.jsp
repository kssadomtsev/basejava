<%--
  Created by IntelliJ IDEA.
  User: Konstantin
  Date: 21.07.2019
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.mysite.webapp.model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    function handleClick(clickedId, el)
    {
        if(clickedId == "addOrganization")
            alert(el);
            var element = document.createElement("input");
            element.setAttribute("type", "text");
            element.setAttribute("name", "mytext");
            var spanvar = document.getElementById(el);
            spanvar.appendChild(element);
    }
</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.mysite.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <jsp:useBean id="type" type="com.mysite.webapp.model.SectionType"/>
            <dl>
                <dt>${type.title}</dt>
                <% switch (type){
                    case OBJECTIVE:
                    case PERSONAL:
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:%>
                <dd>​<textarea name="${type.name()}" rows="5" cols="40">${resume.getSection(type).toString()}</textarea></dd>
                <%  break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = (OrganizationSection) resume.getSection(type);
                        List<Organization> organizationList = organizationSection.getOrganizationList();
                        pageContext.setAttribute("organizationList", organizationList);
                        pageContext.setAttribute("organizationCount", organizationList.size());%>
                <input type="hidden" name="${type.name()}" value="${type.name()}">
                <input type="hidden" name="organizationCount${type}" value="${organizationCount}">
                <span id="${organizationList.hashCode().toString()}">
                 <c:forEach var="organization" items="${organizationList}" varStatus="loop">
                    <jsp:useBean id="organization" type="com.mysite.webapp.model.Organization"/>
                     <%
                         Link link = organization.getLinkOrganization();
                         pageContext.setAttribute("link", link);
                         List<Organization.Position> positionList = organization.getPositionList();
                         pageContext.setAttribute("positionList", positionList);
                         pageContext.setAttribute("posCount", positionList.size());
                     %>
                     <input type="hidden" name="${type}${loop.index}_posCount" value="${posCount}">
                     <dd>​<input type="text" name="link${type}${loop.index}" size=30 value="${link.title}"><input type="text" name="linkUrl${type}${loop.index}" size=30 value="${link.URL}"></dd><br/>
                     <table border="1" cellpadding="8" cellspacing="0">
                            <tr>
                                <th>Дата начала</th>
                                <th>Дата окончания</th>
                                <th>Должность</th>
                                <th>Описание</th>
                            </tr>
                        <c:forEach items="${positionList}" var="position" varStatus="loopPos">
                        <jsp:useBean id="position" type="com.mysite.webapp.model.Organization.Position"/>
                            <tr>
                                <td>​<input type="text" name="${type}${loop.index}_${loopPos.index}_start" size=30 value="${position.getStartDate().toString()}"></td>
                                <td>​<input type="text" name="${type}${loop.index}_${loopPos.index}_end" size=30 value="${position.getEndDate().toString()}"></td>
                                <td>​<input type="text" name="${type}${loop.index}_${loopPos.index}_title" size=30 value="${position.getTitle().toString()}"></td>
                                <td>​<input type="text" name="${type}${loop.index}_${loopPos.index}_description" size=30 value="${position.getDescription().toString()}"></td>
                            </tr>
                        </c:forEach>
                    </table>
                 </c:forEach>
                 </span>
            <%--
            <input type="button" value="Добавить организацию" onclick="handleClick(this.id, ${organizationList.hashCode().toString()});" id="addOrganization" />
            --%>
            <% } %>
        </dl>
    </c:forEach>
    <hr>
    <button type="submit">Сохранить</button>
    <button type="button" onclick="window.history.back()">Отменить</button>
</form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
