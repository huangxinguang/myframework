<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
<h1>客户列表</h1>
<table>
    <tr>
        <td>姓名</td>
        <td>联系人</td>
        <td>电话</td>
        <td>邮箱</td>
        <td>操作</td>
    </tr>
    <c:forEach var="customer" items="${customerList}">
        <tr>
            <td>${customer.name}</td>
            <td>${customer.contact}</td>
            <td>${customer.telephone}</td>
            <td>${customer.email}</td>
            <td>
                <a href="${basePath}/customer_edit?id=${customer.id}"></a>编辑
                <a href="${basePath}/customer_delete?id=${customer.id}"></a>删除
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
