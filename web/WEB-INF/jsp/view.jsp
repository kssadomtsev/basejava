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
        <c:choose>
        <c:when test="${sectionEntry.getKey()=='OBJECTIVE' || sectionEntry.getKey()=='PERSONAL' || sectionEntry.getKey()=='ACHIEVEMENT' || sectionEntry.getKey()=='QUALIFICATIONS'}">
                <h3><%=sectionEntry.getKey().getTitle()%></h3><%=sectionEntry.getValue().toHtml()%>
        </c:when>
        <c:when test="${(sectionEntry.getKey()=='EXPERIENCE' || sectionEntry.getKey()=='EDUCATION') && sectionEntry.getValue().getOrganizationList().size()!=0}">
            <h3><%=sectionEntry.getKey().getTitle()%></h3>
            <c:set var="organizationSection" value="${sectionEntry.getValue()}"/>
            <jsp:useBean id="organizationSection" type="com.mysite.webapp.model.OrganizationSection"/>
            <c:set var="organizationList" value="${organizationSection.getOrganizationList()}"/>
            <jsp:useBean id="organizationList" type="java.util.List<com.mysite.webapp.model.Organization>"/>
            <c:forEach var="organization" items="${organizationList}">
                <jsp:useBean id="organization" type="com.mysite.webapp.model.Organization"/>
                <c:set var="link" value="${organization.getLinkOrganization()}"/>
                <jsp:useBean id="link" type="com.mysite.webapp.model.Link"/>
                <c:set var="positionList" value="${organization.getPositionList()}"/>
                <jsp:useBean id="positionList" type="java.util.List<com.mysite.webapp.model.Organization.Position>"/>
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
        </c:when>
        </c:choose>
        </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
