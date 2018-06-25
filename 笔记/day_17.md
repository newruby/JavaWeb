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
4.ResultSet之滚动结果集


方法分为两类，一类用来判断游标位置的，另一类是用来移动游标的
*  con.createStatement()：生成的结果集：不滚动、不敏感、不可更新！  
* 获取一列的数据，有两种方式，getXxx(int columIndex)，还有一种：getXxx(String columnName)  
 
## PreparedStatement  
1. 用法  
   * 给出SQL模板！
   * 调用Connection的PreparedStatement prepareStatement(String sql模板)；
   * 调用pstmt的setXxx()系列方法sql模板中的?赋值！
   * 调用pstmt的executeUpdate()或executeQuery()，但它的方法都没有参数。   
2. 好处 
   * 防止SQL攻击；
   * 提高代码的可读性，以可维护性；
   * 提高效率  

补充：**什么是SQL攻击**  
在需要用户输入的地方，用户输入的是SQL语句的片段，最终用户输入的SQL片段与我们DAO中写的SQL语句合成一个完整的SQL语句！例如用户在登录时输入的用户名和密码都是为SQL语句的片段！  
     **防止SQL攻击 的方法**
过滤用户输入的数据中是否包含非法字符；  
分步交验！先使用用户名来查询用户，如果查找到了，再比较密码；  
使用PreparedStatement。  
3. 使用    
   * 使用Connection的prepareStatement(String sql)：即创建它时就让它*与一条SQL模板绑定；  
   * 调用PreparedStatement的setXXX()系列方法为问号设置值  
   * 调用executeUpdate()或executeQuery()方法，但要注意，调用没有参数的方法   
```java
String sql = “select * from tab_student where s_number=?”;
PreparedStatement pstmt = con.prepareStatement(sql);
pstmt.setString(1, “S_1001”);
ResultSet rs = pstmt.executeQuery();
rs.close();
pstmt.clearParameters();[再次使用时需要把原来的设置清空。]
pstmt.setString(1, “S_1002”);
rs = pstmt.executeQuery();
```   
## JdbcUtils工具类   
从配置文件中读取配置参数：驱动类、url、用户名，以及密码，然后创建连接对象   
## UserDao

## 时间类型   
java中三种时间类型  
 java.sql包下给出三个与数据库相关的日期时间类型，分别是：
* Date：表示日期，只有年月日，没有时分秒。会丢失时间；
* Time：表示时间，只有时分秒，没有年月日。会丢失日期；
* Timestamp：表示时间戳，有年月日时分秒，以及毫秒。

这三个类都是java.util.Date的子类。

所以数据库时间类型转java.util.Date，把子类对象给父类的引用[
多态 ？？？]，不需要转换。```java.sql.Date date = …
java.util.Date d = date;```   
java.util.Date转换成数据库的三种时间类型    

```java.utl.Date d = new java.util.Date();
java.sql.Date date = new java.sql.Date(d.getTime());//会丢失时分秒
```  



## 大数据[看视频]
目标：把mp3保存到数据库中！   

## 批处理   
批处理只针对更新（增、删、改）语句,
addBatch(String sql) 添加一条语句到“批”中；
executeBatch()：执行“批”中所有语句。返回值表示每条语句所影响的行数据；
clearBatch()：清空“批”中的所有语句。   
执行了“批”之后，“批”中的SQL语句就会被清空  

```java
for(int i = 0; i < 10; i++) {
				String number = "S_10" + i;
				String name = "stu" + i;
				int age = 20 + i;
				String gender = i % 2 == 0 ? "male" : "female";
				String sql = "insert into stu values('" + number + "', '" + name + "', " + age + ", '" + gender + "')";
				stmt[内部有个集合，用来装载sql语句].addBatch(sql);
			}
			stmt.executeBatch[执行批，即一次把批中的所有sql语句发送给服务器]();
```   
### PreparedStatement批处理   
```java
con = JdbcUtils.getConnection();
			String sql = "insert into stu values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			for(int i = 0; i < 10; i++) {
				pstmt.setString(1, "S_10" + i);
				pstmt.setString(2, "stu" + i);
				pstmt.setInt(3, 20 + i);
				pstmt.setString(4, i % 2 == 0 ? "male" : "female");
				pstmt.addBatch()[PreparedStatement的addBatch()方法没有参数！];
			}
			pstmt.executeBatch[执行批]();
```