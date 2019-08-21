<%@ page import="com.mysite.webapp.model.Link" %>
<%@ page import="com.mysite.webapp.model.Organization" %>
<%@ page import="com.mysite.webapp.model.OrganizationSection" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Konstantin
  Date: 21.07.2019
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" width="30"
                                                                                      height="30"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.mysite.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.mysite.webapp.model.SectionType, com.mysite.webapp.model.AbstractSection>"/>
                <%
                switch (sectionEntry.getKey()){
                        case OBJECTIVE:
                        case PERSONAL:
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            out.println(sectionEntry.getKey().toHtml(sectionEntry.getValue().toHtml()));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            out.println(sectionEntry.getKey().getTitle() + ":");
                            OrganizationSection organizationSection = (OrganizationSection) sectionEntry.getValue();
                            List<Organization> organizationList = organizationSection.getOrganizationList();
                            pageContext.setAttribute("organizationList", organizationList);
                            %><br/>
        <c:forEach var="organization" items="${organizationList}">
            <jsp:useBean id="organization" type="com.mysite.webapp.model.Organization"/>
                <%
                                        Link link = organization.getLinkOrganization();
                                        pageContext.setAttribute("link", link);
                                        List<Organization.Position> positionList = organization.getPositionList();
                                        pageContext.setAttribute("positionList", positionList);
                                  %>
        <a href="${link.URL}">${link.title}</a> :<br/>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата начала</th>
            <th>Дата окончания</th>
            <th>Должность</th>
            <th>Описание</th>
        </tr>
        <c:forEach items="${positionList}" var="position">
            <jsp:useBean id="position" type="com.mysite.webapp.model.Organization.Position"/>
            <tr>
                <td>${position.getStartDate().toString()}</td>
                <td>${position.getEndDate().toString()}</td>
                <td>${position.getTitle()}</td>
                <td>${position.getDescription()}</td>
            </tr>
        </c:forEach>
    </table>
    </c:forEach>
    <% break;
    }
    %><br/>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
