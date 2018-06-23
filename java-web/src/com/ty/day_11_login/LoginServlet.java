package com.ty.day_11_login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2.校验
		if(!"it".equalsIgnoreCase(username)) {
			Cookie cookie = new Cookie("uname", username); //创建
			cookie.setMaxAge(60*60*24);//保存一天
			response.addCookie(cookie);////保存cookie
			
			
			//3. 如果成功
			/*    >  保存用户信息到session中
			 *   >  重定向到succ1.jsp*/
			
			HttpSession session = request.getSession(); //session域中保存用户名
			session.setAttribute("username", username);  //session域方法
			response.sendRedirect("/java-web/loginTest/suc1.jsp"); //重定向    对象是response
		}else {
			//4.登录失败
			/*
			 *  如果失败
			 *   > 保存错误信息到request域中
			 *   > 转发到login.jsp
			 */
			request.setAttribute("msg", "please login");
			//请求转发
			request.getRequestDispatcher("/loginTest/login.jsp").forward(request, response);
		}
		
	}

}
