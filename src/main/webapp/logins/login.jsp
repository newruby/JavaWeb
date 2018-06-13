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
    <title>login</title>
</head>
<body>
<h1>登录</h1>
<%
    /*
    读取名为uname的Cookie!
    如果为空显示：""
    如果不为空显示：Cookie的值
    */
    String uname = "";
    Cookie[] cs = request.getCookies();
    if(cs != null) {

        for(Cookie c:cs) {//""
            if("uname".equals(c.getName())) {
                uname = c.getValue();
            }
        }

%>

<%
    /*错误消息为空就不要显示页面上*/
    String message = "";
    String msg = (String)request.getAttribute("msg");
    if(msg != null) {
        message = msg;
    }

%>
<%-- 本页面提供登录表单，还要显示错误信息 --%>

<font color="red"><b><%=message%></b></font>
<form action="${pageContext.request.contextPath}//LoginServlet" method="post">
    %-- 把cookie中的用户名显示到用户名文本框中 --%>
    用户名：<input type="text" name="username" value="<%=uname%>"/><br/>
    密码<input type="password" name="password" /></br>
    <input type="button" value="登录"/>
</form>
</body>
</html>
