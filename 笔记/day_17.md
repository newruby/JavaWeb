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

2. **获取连接**  
>DriverManager来注册驱动    
```Class.forName(“com.mysql.jdbc.Driver”) ```  
>DriveDriverManager来获取Connection对象rManager来获取Connection对象   
```DriverManager.getConnection(url,username,password)```  
url:> ```jdbc:mysql://localhost:3306/mydb1```  
在url中提供参数：
jdbc:mysql://localhost:3306/mydb1```?useUnicode=true&characterEncoding=UTF8```  指定Unicode字节集和字节集编码

3. **获取Statement**   
Statement是用来向数据库发送要执行的SQL语句   
```Statement stmt = con.createStatement();```  

4. **发送SQL增、删、改语句** 
``` java
String sql = “insert into user value(’zhangSan’,  ’123’)”;    
int m = stmt.executeUpdate(sql);
```
其中int类型的返回值表示执行这条SQL语句所影响的行数,执行失败，那么executeUpdate()会抛出一个SQLException 

5. **发送SQL查询语句**  
```java 
String sql = “select * from userinfo”;
ResultSet rs = stmt.executeQuery(sql);
```
返回结果集ResultSet  

6. **读取结果集中的数据**  
ResultSet是一张二维的表格，行光标默认的位置在“第一行上方”，所以  
``` rs.next();//光标移动到第一行 ```  
```getXXX(int col)方法来获取指定列的数据,常用的方法有：
Object getObject(int col) //不确定数据类型 使用
String getString(int col)
int getInt(int col)
double getDouble(int col)  
```
  
 ```  rs.getInt(1);//获取第一行第一列的数据 ```    

 7. **关闭**  
 关闭的顺序是先得到的后关闭，后得到的先关闭。
```
rs.close();
stmt.close();
con.close();  
```
## 规范化代码    

无论是否出现异常，都要关闭ResultSet、Statement，以及Connection  

```java  
public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/mydb1";
		return DriverManager.getConnection(url, "root", "123");
	}
@Test
	public void query() {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
[在try外给出引用的定义]		try {
			con = getConnection();[在try内为对象实例化]
			stmt = con.createStatement();
			String sql = "select * from user";
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				System.out.println(username + ", " + password);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
[在finally中进行关闭]			} catch(SQLException e) {}
		}
	}
```  
## JDBC对象介绍  
1. DriverManager两种异常
2. Statement stmt = con.createStatement(int,int);   ```[这两个参数是用来确定创建的Statement能生成什么样的结果集]```  
3. Statement中的方法     

   int executeUpdate(String sql)    
``` [create、alter、drop、insert、update、delete] ```   

   ResultSet executeQuery(String sql)： ```[select]```  
   boolean execute()  

    该方法返回的是boolean类型，表示SQL语句是否有结果集！。```[了解！可以执行executeUpdate()和executeQuery()两个方法能执行的sql语句]```
4. con.createSttement()：生成的结果集：不滚动、不敏感、不可更新！  
5. 获取一列的数据，有两种方式，getXxx(int columIndex)，还有一种：getXxx(String columnName)