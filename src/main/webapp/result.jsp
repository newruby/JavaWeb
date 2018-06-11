<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/6/11
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Integer result = (Integer)request.getAttribute("result");
%>
<%=result %>
</body>
</html>
