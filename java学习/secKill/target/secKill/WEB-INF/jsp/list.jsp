<%--
  Created by IntelliJ IDEA.
  User: hanhongxuan
  Date: 2018/5/12
  Time: 下午2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>list</title>
    <meta content="charset=utf-8" />
</head>
<body>
    <table>
        <tr>
            <td>商品id</td>
            <td>商品名</td>
            <td>库存</td>
            <td>活动开始时间</td>
            <td>活动结束时间</td>
            <td>创建时间</td>
            <td>详情页</td>
        </tr>
        <c:forEach var="sk" items="${list}">
            <tr>
                <td>${sk.seckillId}</td>
                <td>${sk.name}</td>
                <td>${sk.number}</td>
                <td>
                    <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>
                    <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>
                    <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>
                    <a href="/seckill/${sk.seckillId}/detail">link</a>
                </td>
            </tr>
        </c:forEach>
    </table>


<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</body>
</html>
