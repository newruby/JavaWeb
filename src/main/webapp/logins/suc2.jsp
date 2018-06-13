<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/6/13
  Time: 7:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>suc1</title>
</head>
<body>
<h1>succ2</h1>
<%
    String username = (String)request.getAttribute("username");
    if(username == null) {
        request.setAttribute("msg", "please login in");
        request.getRequestDispatcher("login.jsp").forward(request,response);
        return;
    }
%>

<%=username%>欢迎回来
<%-- 可以用个几秒钟自动跳转-->
</body>
</html>
