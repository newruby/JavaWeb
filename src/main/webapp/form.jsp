<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/6/11
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--以“/”开头：相对当前主机,自己手动添加项目名  注意这里只有JavaWeb项目--%>
<form action="/JavaWeb/CServlet" method="post">
    整数1：<input type="text" name="num1"/><br/>
    整数2：<input type="text" name="num2"/><br/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
