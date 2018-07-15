# day_20   
JavaWeb监听器    


三大组件：
* Servlet
* Listener
* Filter  
```java 
btn.addActionListener*(new ActionListener(){
    public void actionPerformed(ActionEvent evt) {
        sout....
    }
 });

```  
监听器：是一个接口，内容由我们来实现
监听器中的方法，会在特殊事件发生时被调用  
观察者：
事件源；
>小偷
事件；
>偷东西
监听器；
>警察
>监听器中的方法：抓捕  

## JavaWeb中的监听器`(一共八个 三大域中各两个)`
* 事件源：三大域！

* ServletContext  
>生命周期监听：ServletContextListener，它有两个方法，一个在出生时调用，一个在死亡时调用；  
>>void contextInitialized(ServletContextEvent sce)：创建SErvletcontext时`(出生后)`  
>>void contextDestroyed(ServletContextEvent sce)：销毁Servletcontext时  

>属性监听：ServletContextAttributeListener，它有三个方法，一个在`添加`属性时调用，一个在`替换`属性时调用，最后一个是在`移除`属性时调用。  
>>void attributeAdded(ServletContextAttributeEvent event)：添加属性时；  
>>void attributeReplaced(ServletContextAttributeEvent event)：替换属性时；  
>>void attributeRemoved(ServletContextAttributeEvent event)：移除属性时；  

* HttpSession
>生命周期监听：HttpSessionListener，它有两个方法，一个在出生时调用，一个在死亡时调用；  
>>void sessionCreated(HttpSessionEvent se)：创建session时
>>void sessionDestroyed(HttpSessionEvent se)：销毁session时

>属性监听：HttpSessioniAttributeListener，它有三个方法，一个在添加属性时调用，一个在替换属性时调用，最后一个是在移除属性时调用。  
>>void attributeAdded(HttpSessionBindingEvent event)：添加属性时；  
>>void attributeReplaced(HttpSessionBindingEvent event)：替换属性时  
>>void attributeRemoved(HttpSessionBindingEvent event)：移除属性时  

* ServletRequest
>生命周期监听：ServletRequestListener，它有两个方法，一个在出生时调用，一个在死亡时调用；  
>>void requestInitialized(ServletRequestEvent sre)：创建request时  
>>void requestDestroyed(ServletRequestEvent sre)：销毁request时  

>属性监听：ServletRequestAttributeListener，它有三个方法，一个在添加属性时调用，一个在替换属性时调用，最后一个是在移除属性时调用。  
>>void attributeAdded(ServletRequestAttributeEvent srae)：添加属性时  
>>void attributeReplaced(ServletRequestAttributeEvent srae)：替换属性时  
>>void attributeRemoved(ServletRequestAttributeEvent srae)：移除属性时   

* javaWeb中完成编写监听器：  
>写一个监听器类：要求必须去实现某个监听器接口；  
>注册，是在web.xml中配置来完成注册！ ```（create一个listener自动添加）  ```
```xml
<listener>
<listener-class>xxx</listener-class>
</listener>
```
## 事件对象：    
获取
* ServletContextEvent：  
```
ServletContext getServletContext()
```
* HttpSessionEvent：   
```
HttpSession getSession()
```
* ServletRequestEvent：
```
ServletContext getServletContext()；  
ServletReques getServletRequest()；
```  
事件对象
* ServletContextAttributeEvent：  
ServletContext getServletContext()；   
>String getName()：获取属性名   
>Object getValue()：获取属性值   
* HttpSessionBindingEvent：略
* ServletRequestAttributeEvent ：略

感知监听（都与HttpSession相关）`第7、8个` 
>它用来添加到`JavaBean`上，而不是添加到三大域上！  
>这两个监听器都`不需要在web.xml中注册`！  
### （在work文件夹下保存SESSIONS.ser）
*  HttpSessionBindingListener  `(第7个)`：添加到javabean上，javabean就知道自己是否添加到session中了。
 
示例步骤：
>编写Person类，让其实现HttpSessionBindingListener监听器接口；
>编写Servlet类，一个方法向session中添加Person对象，另一个从session中移除Person对象；
>在index.jsp中给出两个超链接，分别访问Servlet中的两个方法。  
```
Person类实现了HttpSessionBindingListener监听器接口，当把Person类对象添加到session中，或者把Person对象从session中移除时会调用Person类的valueBound()和valueUnbound()方法
```
### 钝化和活化 （注意实现Serializable接口  String已经自己实现  一个session一个文件）
* HttpSessionActivationListener`(第8个)`：   
Tomcat会在session从时间不被使用时钝化session对象，所谓钝化session，就是把session通过序列化的方式保存到硬盘文件中。当用户再使用session时，Tomcat还会把钝化的对象再活化session，所谓活化就是把硬盘文件中的session在反序列化回内存。当session被Tomcat钝化时，session中存储的对象也被纯化，当session被活化时，也会把session中存储的对象活化。如果某个类实现了HttpSessionActiveationListener接口后，当对象随着session被钝化和活化时，下面两个方法就会被调用：
>public void sessionWillPassivate(HttpSessionEvent se)：当对象感知被活化时调用本方法；
>public void sessionDidActivate(HttpSessionEvent se)：当对象感知被钝化时调用本方法； 


因为钝化和活化session，其实就是使用序列化和反序列化技术把session从内存保存到硬盘，和把session从硬盘加载到内存。这说明**如果Person类没有实现Serializable接口**，那么当session钝化时就**不会钝化Person，而是把Person从session中移除再钝化！这也说明session活化后，session中就不在有Person对象了。**
 

* 示例步骤：  
配置Tomcat钝化session的参数，把配置文件放到tomcat\conf\catalina\localhost目录下 文件名称为项目名称。
```
<Context>
<Manager className="org.apache.catalina.session.PersistentManager" maxIdleSwap="1">
[如果session在1分钟内没有使用，那么Tomcat就会钝化它]
<Store className="org.apache.catalina.session.FileStore" directory="mysession"/>
[把session序列化到Tomcat\work\Catalina\localhost\listener\mysession目录下。]
</Manager>
</Context>
```
## 国际化
国际化就是可以把页面中的中文变成英文  

......   
### 小结
主要是了解监听器的功能  
前七个看一下怎么实现的最终是要理解在框架占有欲在框架中的应用国际化就是实践性code
