# day17

## JDBC
JDBC 一个规范 接口  
驱动 其他厂商提供访问自己数据库的API  ->
JDBC驱动 接口的实现  

## JDBC核心类（接口）
核心类有：DriverManager、Connection、Statement，和ResultSet  
DriverManger（驱动管理器）的作用有两个：
* 注册驱动：这可以让JDBC知道要使用的是哪个驱动；
* 获取Connection：如果可以获取到Connection，那么说明已经与数据库连接上了。

Connection对象表示连接，与数据库的通讯都是通过这个对象展开的：
* Connection最为重要的一个方法就是用来获取Statement对象；

Statement是用来向数据库发送SQL语句的，这样数据库就会执行发送过来的SQL语句：
* void executeUpdate(String sql)：执行更新操作（insert、update、delete等）；
* ResultSet executeQuery(String sql)：执行查询操作，数据库在执行查询后会把查询结果，查询结果就是ResultSet；

ResultSet对象表示查询结果集，只有在执行查询操作后才会有结果集的产生。结果集是一个二维的表格，有行有列。操作结果集要学习移动ResultSet内部的“行光标”，以及获取当前行上的每一列上的数据：
* boolean next()：使“行光标”移动到下一行，并返回移动后的行是否存在；
* XXX getXXX(int col)：获取当前行指定列上的值，参数就是列数，列数从1开始，而不是0。


## JDBC程序  
1. jar包 mysql-connector-java-5.1.13-bin.jar  
2. 获取连接  
>DriverManager来注册驱动  
>DriveDriverManager来获取Connection对象rManager来获取Connection对象