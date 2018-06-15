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
		//1.��ȡ������
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2.У��
		if(!"it".equalsIgnoreCase(username)) {
			Cookie cookie = new Cookie("uname", username); //����
			cookie.setMaxAge(60*60*24);//����һ��
			response.addCookie(cookie);////����cookie
			
			
			//3. ����ɹ�
			/*    >  �����û���Ϣ��session��
			 *   >  �ض���succ1.jsp*/
			
			HttpSession session = request.getSession(); //session���б����û���
			session.setAttribute("username", username);  //session�򷽷�
			response.sendRedirect("/java-web/loginTest/suc1.jsp"); //�ض���    ������response
		}else {
			//4.��¼ʧ��
			/*
			 *  ���ʧ��
			 *   > ���������Ϣ��request����
			 *   > ת����login.jsp
			 */
			request.setAttribute("msg", "please login");
			//����ת��
			request.getRequestDispatcher("/loginTest/login.jsp").forward(request, response);
		}
		
	}

}
