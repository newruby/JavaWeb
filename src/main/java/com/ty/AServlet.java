package com.ty;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * created by TY on 2018/6/10.
 */

/**
 * 统计访问量
 * @param
 * @return
 */

public class AServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1.获取ServletContext对象
         * 2.从ServletContext对象中获取名为count的属性
         * 3.如果存在 访问量加一
         * 4.不存在说明是第一次访问，ServletContext中保存名为count的属性值为一
         * @param [request, response]
         * @return void
         */
        ServletContext app = this.getServletContext();
        Integer count = (Integer) app.getAttribute("count");
        if(count == null) {
            app.setAttribute("count", 1);
        }else {
            app.setAttribute("count", count+1);
        }

        // 更新count

        count = (Integer)app.getAttribute("count");
        /**
         *
         向浏览器输出使用Response
         */
        PrintWriter pw = response.getWriter();
        pw.print("<h1>" + count + "</h1>");

    }
}
