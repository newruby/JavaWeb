<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <h1>login</h1>
  <body>
  
  <%
	  String uname="";
	  Cookie[] cs = request.getCookies();
	  if(cs != null) {
	    for(Cookie c:cs) {
		  	if("uname".equals(c.getName())) {  //有这个名字为uname的cookie
		  		uname = c.getValue();
		  	}
	  	}
	  } 
  
   %>
   
   <%--错误信息 --%>
   <%
   
   	String message = ""; //不出错就不显示
   	String msg = (String) request.getAttribute("msg");
   	if(msg != null) {
   		message = msg;
   	}
      
    %>
    <font color="red"><b><%=message %></b></font>
    <form action="/java-web/LoginServlet" method="post">
    	用户名<input type="text" name="username" value=<%=uname %>/>
    	密码<input type="password" name="password"/> 
    	<input type="submit" value="登录"/> 
    </form>
  </body>
</html>
