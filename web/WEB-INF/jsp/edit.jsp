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

    function insertCell(num, row, name) {
        var cell = row.insertCell(num);
        var element = document.createElement("input");
        element.type = "text";
        element.name=name;
        element.size = 30;
        cell.appendChild(element);
    }
    function insertCellText(num, row, value) {
        row.insertCell(num).outerHTML = "<th>"+value+"</th>";
    }

    function handleClickTable(clickedId, type, loopIndex)
    {
        if(clickedId == "addPosition") {
            var table = document.getElementById(type+loopIndex+"_table");

            var rowCount = table.rows.length;
            var row = table.insertRow(rowCount);

            insertCell(0, row, type+loopIndex+"_"+(rowCount-1)+"_start");
            insertCell(1, row, type+loopIndex+"_"+(rowCount-1)+"_end");
            insertCell(2, row, type+loopIndex+"_"+(rowCount-1)+"_title");
            insertCell(3, row, type+loopIndex+"_"+(rowCount-1)+"_description");

            var posCount = document.getElementById(type+loopIndex+"_posCount");
            posCount.value = parseInt(posCount.value)+1;
        }
        else if(clickedId == "remPosition") {
            try {
                var table = document.getElementById(type+loopIndex+"_table");
                var rowCount = table.rows.length;
                table.deleteRow(rowCount-1);
                var posCount = document.getElementById(type+loopIndex+"_posCount");
                posCount.value = parseInt(posCount.value)-1;
            }catch(e) {
                alert(e);
            }
        }
    }

    function handleClicklist (clickedId, type) {
        if(clickedId == "addOrganization") {
            var list = document.getElementById("list_" + type);
            var node = document.createElement("LI");

            var listLength = list.getElementsByTagName("li").length;

            var hidden = document.createElement("input");
            hidden.type = "hidden";
            hidden.id = type + listLength + "_posCount";
            hidden.name = type + listLength + "_posCount";
            hidden.value = listLength;
            node.appendChild(hidden);

            var inputLink = document.createElement("input");
            inputLink.type = "text";
            inputLink.name="link"+type+listLength;
            inputLink.size = 30;
            node.appendChild(inputLink);

            var inputLinkUrl = document.createElement("input");
            inputLinkUrl.type = "text";
            inputLinkUrl.name="linkUrl"+type+listLength;
            inputLinkUrl.size = 30;
            node.appendChild(inputLinkUrl);

            var table =  document.createElement("table");
            table.id = type + listLength + "_table";
            table.setAttribute("border", "1");
            table.setAttribute("cellpadding", "8");
            table.setAttribute("cellspacing", "0");

            var row = table.insertRow(0);
            insertCellText(0, row, "Дата начала");
            insertCellText(1, row, "Дата окончания");
            insertCellText(2, row, "Должность");
            insertCellText(3, row, "Описание");

            row = table.insertRow(1);
            insertCell(0, row, type+listLength+"_"+(0)+"_start");
            insertCell(1, row, type+listLength+"_"+(0)+"_end");
            insertCell(2, row, type+listLength+"_"+(0)+"_title");
            insertCell(3, row, type+listLength+"_"+(0)+"_description");
            node.appendChild(table);

            var buttonAddPos = document.createElement("input");
            buttonAddPos.type = "button";
            buttonAddPos.value = "Добавить позицию";
            buttonAddPos.id = "addPosition";
            buttonAddPos.addEventListener('click', handleClickTable(this.id, type, listLength), false);
            node.appendChild(buttonAddPos);

            var buttonRemPos = document.createElement("input");
            buttonRemPos.type = "button";
            buttonRemPos.value = "Удалить последнюю позицию";
            buttonRemPos.id = "remPosition";
            buttonRemPos.addEventListener('click', handleClickTable(this.id, type, listLength), false);
            node.appendChild(buttonRemPos);

            list.appendChild(node);

            var orgCount = document.getElementById("organizationCount"+type);
            orgCount.value = parseInt(orgCount.value)+1;
        }
        else if(clickedId == "remOrganization"){
            var list = document.getElementById("list_" + type);
            var listLength = list.getElementsByTagName("li").length;
            list.removeChild(list.childNodes[listLength]);

            var orgCount = document.getElementById("organizationCount"+type);
            orgCount.value = parseInt(orgCount.value)-1;
        }
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
                <input type="hidden" id="organizationCount${type}" name="organizationCount${type}" value="${organizationCount}">
                <input type="button" value="Добавить организацию" onclick="handleClicklist(this.id, '${type}');" id="addOrganization" /><input type="button" value="Удалить последнюю организацию" onclick="handleClicklist(this.id, '${type}');" id="remOrganization" />
                <ol id="list_${type}">
                 <c:forEach var="organization" items="${organizationList}" varStatus="loop">
                    <jsp:useBean id="organization" type="com.mysite.webapp.model.Organization"/>
                     <li>
                     <%
                         Link link = organization.getLinkOrganization();
                         pageContext.setAttribute("link", link);
                         List<Organization.Position> positionList = organization.getPositionList();
                         pageContext.setAttribute("positionList", positionList);
                         pageContext.setAttribute("posCount", positionList.size());
                     %>
                     <input type="hidden" id="${type}${loop.index}_posCount" name="${type}${loop.index}_posCount" value="${posCount}">
                     <dd>​<input type="text" name="link${type}${loop.index}" size=30 value="${link.title}"><input type="text" name="linkUrl${type}${loop.index}" size=30 value="${link.URL}"></dd><br/>
                     <table id="${type}${loop.index}_table" border="1" cellpadding="8" cellspacing="0">
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
                         <input type="button" value="Добавить позицию" onclick="handleClickTable(this.id, '${type}', '${loop.index}');" id="addPosition" /><input type="button" value="Удалить последнюю позицию" onclick="handleClickTable(this.id, '${type}', '${loop.index}');" id="remPosition" />
                     </li>
                 </c:forEach>
                 </ol>
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
