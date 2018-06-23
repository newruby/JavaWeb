package com.ty.day_17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.Class.forName;

/**
 * created by TY on 2018/6/22.
 */
public class MysqlTest {
    public static void main(String[] args) throws Exception{
        //注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //获取连接
       Connection con = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/webtest?useUnicode=true&characterEncoding=UTF8",
               "root","1234");
        // 获取Statement
        Statement stmt = con.createStatement();

        String sql = "select * from userinfo";
        ResultSet rs = stmt.executeQuery(sql );
        //光标移动到第一行
        rs.next();
        Object o = rs.getObject(1);
        System.out.println(o.toString());

    }
}
