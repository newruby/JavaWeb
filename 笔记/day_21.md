## day_21
---

### JavaWeb三大组件

1. 都需要在web.xml中进行配置
Servlet
Listener(2个感知监听器不需要配置)
Filter

2. 过滤器
  它会在`一组资源`（jsp、servlet、.css、.html等等）的前面执行！
  它可以让请求得到目标资源，也可以不让请求达到！
  * 过滤器有拦截请求的能力！

登录：
允许它访问AServlet、BServlet、CServlet

-------------------------------

### 过滤器如何编写

1. 写一个类实现Filter接口
2. 在web.xml中进行配置

### Filter接口

>void init(FilterConfig)
  * 创建之后，马上执行；Filter会在服务器启动时就创建！
>void destory()
  * 销毁之前执行！在服务器关闭时销毁
>void doFilter(ServletRequest,ServletResponse,FilterChain)
  * 每次过滤时都会执行

Filter是单例的！

web.xml  
```
<filter>
  <filter-name>xxx</filter-name>
  <filter-class>类名.AFitler</fitler-class>
</servlet>
<fitler-mapping>
  <filter-name>xxx</filter-name>
  <url-pattern>/*</url-pattern>
  </filter-mapping>
```
-------------------------------

>FilterConfig-->与ServletConfig相似
  * 获取初始化参数：getInitParameter()
  * 获取过滤器名称：getFilterName()
  * 获取appliction：getServletContext()

>FilterChain  `区分Filter的doFilter`
  * doFilter(ServletRequest, ServletResponse)：放行！
  放行，就相当于调用了目标Servlet的service()方法！  

  执行FilterChai下一个过滤器，或是目标资源

-------------------------------



### 多过滤器

FilterChain#doFilter()方法：
  执行目标资源，或是执行下一个过滤器！如果没有下一个过滤器那么执行的是目标资源，如果有，那么就执行下一个过滤器！  
`执行完所有的过滤器才执行目标资源`


-------------------------------

### 过滤器的四种拦截方式

  请求转发包含错误

```
  <dispatcher>REQUEST</dispatcher>默认的！
  <dispatcher>FORWARD</dispatcher>
  <dispatcher>INCLUDE</dispatcher>
  <dispatcher>ERROR</dispatcher>  (error-page)
```
在`<filter-mapping>`中进行配置!


-------------------------------

### 多个过滤器的执行顺序

`<filter-mapping>`的配置顺序决定了过滤器的执行顺序！  

----  
### 过滤器的应用场景  

* 执行目标资源之前做预处理工作，例如设置编码，这种试通常都会放行，只是在目标资源执行之前做一些准备工作；  
[几乎是的Sevlet中都需要写request.setCharacterEndoing() 可以把它入到一个Filter中,这种不会拦截]  

* 通过条件判断是否放行，例如校验当前用户是否已经登录，或者`用户IP`是否已经被禁用；  

* 在目标资源执行后，做一些后续的特殊处理工作，例如把目标资源输出的数据进行处理  
[回程拦截！后续处理]； 

---
不指定`<url-pattern>`，指定`<servlet-name>`！注意，它与某个Servlet的配置名称相同！


---


























