package com.ty.day_11;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by TY on 2018/6/11.
 */
public class CServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        String s1 = request.getParameter("num1");
        String s2 = request.getParameter("num2");
        // 转换成int类型
        int num1 = Integer.parseInt(s1);
        int num2 = Integer.parseInt(s2);

        // 运算
        int sum = num1 + num2;

        // 把结果保存到request域中
        request.setAttribute("result", sum);

        // 转换到result.jsp   相对当前项目目录，即当然index.jsp所在目录
        request.getRequestDispatcher("/result.jsp").forward(request, response);

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getContextPath());
    }

}
