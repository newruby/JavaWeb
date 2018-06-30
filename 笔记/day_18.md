## 事务   
`一般都用转账举例` 
1. 事务的四大特性：ACID  （面试）  
* 原子性（Atomicity）  ： 不可再分割。
* 一致性（Consistency）  ：[其他特性都是为了这一特性服务的。]  事务执行前后，业务数据保持一致。比如转账，转账前后两个账户余额和不变。
* 隔离性（Isolation）：多个事务并发操作时要隔离开来。
* 持久性（Durability）：一旦事务提交成功，事务中所有的数据操作必须被持久化到数据库中，即使提交事务后数据库马上崩溃也能保证恢复。  

2. mysql中操作事务    
默认，Mysql执行一条sql语句是一个单独的事务。一个事务包含多条语句： 
开启事务：start transaction；
结束事务：commit成功  
rollback失败  撤销之前的操作

3. jdbc中操作事务 **同一个事务，用同一个Connection对象**  
都是通过connection完成 ，三个方法：  
* 1.setAutoCommit(boolean)  
默认是true每条语句一个单独的事务   con.setAutoCommit(false)表示开启事务
* 2.con.commit()表示提交事务
* 3.con.rollback();表示回滚事务  
格式:  
```java 

try {
  con.setAutoCommit(false);//开启事务…
  ….
  …
  con.commit();//try的最后提交事务
} catch() {
  con.rollback();//回滚事务
}
```

4. 事务隔离级别  

* 并发读问题 ：
> 脏读（dirty read）：`主要问题`  读到另一个事务的*未提交*数据，即读取到了脏数据；  
> 不可重复读（unrepeatable read）：对同一记录的两次读取不一致，因为另一事务对该记录做了*修改*；  
> 幻读（虚读）（phantom read）：对同一张表的两次查询不一致，因为另一事务*插入*了一条记录；  

* 隔离级别：   

1　SERIALIZABLE（串行化）[三种读问题都能处理]
> 不会出现任何并发问题，因为它是对同一数据的访问是串行的，非并发访问的；
> 性能最差；  

2　REPEATABLE READ[脏读、不可重复读，不能处理幻读]（可重复读）（MySQL默认）
> 防止脏读和不可重复读，不能处理幻读问题；
> 性能比SERIALIZABLE好
  
3　READ COMMITTED[只能处理脏读，不能处理不可重复读和幻读。]（读已提交数据）（Oracle默认）
> 防止脏读，没有处理不可重复读，也没有处理幻读；
> 性能比REPEATABLE READ好    

4　READ UNCOMMITTED[啥也不处理！]（读未提交数据）
> 可能出现任何事务并发问题
> 性能最好  

5 MySQL隔离级别
MySQL的默认隔离级别为Repeatable read，可以通过下面语句查看：
select @@tx_isolation

也可以通过下面语句来设置当前连接的隔离级别：
set transaction isolationlevel [4MySQL隔离级别
MySQL的默认隔离级别为Repeatable read，可以通过下面语句查看：
select @@tx_isolation

也可以通过下面语句来设置当前连接的隔离级别：
set transaction isolationlevel [4选1]



6　JDBC设置隔离级别
con. setTransactionIsolation(int level)
参数可选值如下：
```Connection.TRANSACTION_READ_UNCOMMITTED；
Connection.TRANSACTION_READ_COMMITTED；
Connection.TRANSACTION_REPEATABLE_READ；
Connection.TRANSACTION_SERIALIZABLE。
```
总结：
注意区分可重复读和不可重复读两种类型不同
事务的隔离级别： READ_UNCOMMITTED、READ_COMMITTED、REPEATABLE_READ、SERIALIZABLE。多个事务并发执行时才需要考虑并发事务。


## 数据库连接池  
`为了可重用`  
* 池参数（都有默认值）
* 管理连接的生命周期，使用**四大连接参数**来完成创建连接对象
* 引入jar包 common-dbcp-xx.jar  common-pool-xx.jar
* 必须实现java`x`.sql.DataSource接口    
* 连接池返回的Connection对象，调用它的close()不是关闭，而是把连接归还给池！
* 实现
```java
1.创建连接池对象
    BasicDataSource dataSource = new BasicDataSource();

2.配置四大参数
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/mydb3");
    dataSource.setUserName("root");
    dataSource.setPassword("1234");

3.配置池参数  详细dbpc配置文件
    dataSource.setMaxActive(20);
    dataSource.setMinIdle(3);
    dataSource.setMaxWait(1000);

4.得到连接对象
    Connection con = dataSource.getConnection();
System.out.println(con.getClass().getName());//装饰者模式
/**
*连接池内部使用四大参数创建连接对象即mysql驱动提供的connection
连接池使用Mysql的连接对象进行装饰，只对close方法进行增强
装饰之后的Connection的close()方法，用来把当前连接归还给池   

*/

con.close();

```
![xxx](连接池返回的连接结构.bmp "连接池返回的连接结构")

`一个项目一个连接池`  

## 视频装饰者模式 + DBCP （skip zs）

## C3P0
* 池类是：ComboPooledDataSource  / jar包 /底层动态代理


```java
public void fun1() throws PropertyVetoException, SQLException {
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb1");
        ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setUser("root");
		ds.setPassword("123");
		
[基本配置]		
		ds.setAcquireIncrement(5)[每次的增量为5];
		ds.setInitialPoolSize(20)[初始化连接数];
		ds.setMinPoolSize(2)[最少连接数];
		ds.setMaxPoolSize(50)[最多连接数];
		
		Connection con = ds.getConnection();
		System.out.println(con);
		con.close();
	}


```
* 配置文件要求
```
文件名称：必须叫c3p0-config.xml
文件位置：必须在src下
```


```java
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
	<default-config>[默认配置]
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
		<property name="driverClass">com.mysql.jdbc.Driver</property>
		<property name="user">root</property>
		<property name="password">123</property>
        <!-- 池参数配置 -->
		<property name="acquireIncrement">3</property>
		<property name="initialPoolSize">10</property>
		<property name="minPoolSize">2</property>
		<property name="maxPoolSize">10</property>
	</default-config>
    </default-config>
	<named-config name="oracle-config">[命名配置]
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
		<property name="driverClass">com.mysql.jdbc.Driver</property>
		<property name="user">root</property>
		<property name="password">123</property>
		<property name="acquireIncrement">3</property>
		<property name="initialPoolSize">10</property>
		<property name="minPoolSize">2</property>
		<property name="maxPoolSize">10</property>
	</named-config>
</c3p0-config>


代码中直接
ComboPooledDataSource ds = new ComboPooledDataSource();
[不用定配置文件名称，因为配置文件名必须是c3p0-config.xml，这里使用的是默认配置。]
		Connection con = ds.getConnection();
		System.out.println(con); 

///////////////////

ComboPooledDataSource ds = new ComboPooledDataSource("orcale-config")[使用名为oracle-config的配置。];


```

总结：代码配置、配置文件中的默认配置、配置文件中的命名配置   

---   

## Tomcat配置连接池  

JNDI（Java Naming and Directory Interface），Java命名和目录接口。JNDI的作用就是：在服务器上配置资源，然后通过统一的方式来获取配置的资源。 
在apache-tomcat-8.5.23\conf\Catalina\localhost 下新建 项目名.xml

```xml

<Context>  
<!--name:指定资源的名称
factory：资源由谁来负责创建
type:资源的类型
这三个必须的其他的都是资源的参数  编码UTF-8
--->
  <Resource name="jdbc/dataSource" 
            factory="org.apache.naming.factory.BeanFactory"
			type="com.mchange.v2.c3p0.ComboPooledDataSource"	
			user="root" 
			password="123" 
			driverClass="com.mysql.jdbc.Driver"    
			jdbcUrl="jdbc:mysql://127.0.0.1/mydb1"
			acquireIncrement="1"
			maxPoolSize="5"
			initialPoolSize="3"/>
</Context>
```

```java
//获取JNDI的资源
//获取JNDI的上下文对象
Context cxt = new InitialContext();
//查询出入口
Context envContext = (Context) cxt.lookup("java:comp/env");
//查找到资源 resource name
DataSource dataSource = (DataSource) envContext.lookup("jdbc/dataSource");
//DataSource dataSource = (DataSource) cxt.lookup("java:comp/env/jdbc/dataSource");

Connection con = dataSource.getConnection();


```
## ThreadLocal  
Thread ->人类
Runnable ->任务类  
* TreadLocal(泛型类)内部是个Map，以**当前线程**作为键   
map.put(Thread.currentThread(), value)
* 三个方法
```
ThreadLocal<String> t1 = new ThreadLocal<String> ();
t1.set("xxx");//存
String s = t1.get();//取
t1.remove();//
```  

通常用在一个类的成员上，多个线程访问它时 ，每个线程都有自己的副本互不干扰  
private TreadLocal<> xxx = new TreadLocal<>();
spring中把connection放到ThreadLocal   

## dbutils   

