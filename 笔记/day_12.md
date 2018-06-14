# day 12  
## JSP三大指令
>  一个jsp页面中，可以有0~N个指令的定义！
## 1. page--> 最复杂：    `{实际配置就用到 language Import pageEncoding和contentType一个}`
<%@page language="java" info="xxx"...%>

  * pageEncoding和contentType：
    > pageEncoding：它指定当前jsp页面的编码，只要实际的页面编码也是这个，就不会有乱码！在服务器要把jsp编译成.java时需要使用pageEncoding!  

    > contentType：它表示添加一个响应头：Content-Type！等同与response.setContentType("text/html;charset=utf-8");  

    > 如果两个属性只提供一个，那么另一个的默认值为设置那一个。 `｛一般就设置一个｝`  

    > 如果两个属性都没有设置，那么默认为iso  `｛不支持中文｝`  

  * import：导包！可以出现多次 
  * errorPage和isErrorPage
    > errorPage：当前页面如果抛出异常，那么要 **转发** 到哪一个页面，由errorPage来指定  

    > isErrorPage：它指定当前页面是否为处理错误的页面！当该属性为true时，`这个页面会设置状态码为500！而且这个页面可以使用9大内置对象中的exception!` 
  *   在web.xml配置错误页面   

```java
  <error-page>
  	<error-code>404</error-code>
  	<location>/error/errorPage.jsp</location>
      </error-page>
      <error-page>
        <error-code>500</error-code>
        <location>/error/errorPage.jsp</location>
      </error-page>  
      <error-page>
        <exception-type>java.lang.RuntimeException</exception-type>
        <location>/index.jsp</location>
      </error-page>  
```

   * autoFlush和buffer  `{了解一下}`
     > autoFlush：指定jsp的输出流缓冲区满时，是否自动刷新！默认为true，如果为false，那么在缓冲区满时抛出异常！  

     > buffer：指定缓冲区大小，默认为8kb，通常不需要修改！ 

   * isELIgnored：是否忽略el表达式，默认值为false，不忽略，即支持！  

   * 基本没用：  

     > language：指定当前jsp编译后的语言类型，默认值为java。  

     > info：信息！  

     > isThreadSafe：当前的jsp是否支持并发访问！  

     > session：当前页面是否支持session，如果为false，那么当前页面就没有session这个内置对象！  

     > extends：让jsp生成的servlet去继承该属性指定的类！  

 ##     2. include --> 静态包含  `测试的时候出错`

  * 与RequestDispatcher的include()方法的功能相似！  

  * <%@include%> 它是在jsp编译成java文件时完成的！他们共同生成一个java(就是一个servlet)文件，然后再生成一个class！  
  * RequestDispatcher的include()是一个方法，包含和被包含的是两个servlet，即两个.class！他们只是把响应的内容在运行时合并了！  

  * 作用：把页面分解了，使用包含的方式组合在一起，这样一个页面中不变的部分，就是一个独立jsp，而我们只需要处理变化的页面。  

  `{目的是把多个JSP合并成一个JSP文件！
include指令只有一个属性：file，指定要包含的页面，例如：<%@include file=”b.jsp”%>。}`

## 3. taglib --> 导入标签库
`{在JSP页面中使用第三方的标签库时，需要使用taglib指令来“导包”。例如：
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>}`

  * 两个属性：
    > prefix：指定标签库在本页面中的前缀！由我们自己来起名称！  
    > uri: 指定标签库的位置！  
    > <%@taglib prefix="s" uri="/struts-tags"%> 前缀的用法<s:text>

---

## 九个内置对象

* out --> jsp的输出流，用来向客户端响应  
* page --> 当前jsp对象！　它的引用类型是Object，即真身中有如下代码：Object page = this;  `{没用}`
* config --> 它对应真身中的ServletConfig对象！`{没用 不配置}`
* `pageContext --> 一个顶9个！`
* `request` --> HttpServletEequest
* `response` --> HttpServletResponse
* `exception` --> Throwable
* `session` --> HttpSession  `{ HttpSession类的对象，不是每个JSP页面中都可以使用，如果在某个JSP页面中设置<%@page session=”false”%>，说明这个页面不能使用session。}`
* `application` --> ServletContext类的对象  与Tomcat同生共死

### 1. pageContext
  * 一个顶9个！
  * Servlet中有三大域`{request、session、application}`，而JSP中有四大域`{pageContext、request、session、application}`，它就是最后一个域对象！
    > ServletContext：整个应用程序  

    > session：整个会话(一个会话中只有一个用户)  

    > request：一个请求链！  

    > pageContext：一个jsp页面！这个域是在当前jsp页面和当前jsp页面中使用的标签之间共享数据！ pageContext 对象是PageContext类型，它的主要功能有： 

      >> 域对象   
`{ pageContext也是域对象，它的范围是当前页面。它的范围也是四个域对象中最小的！}`
    ```java
    void setAttribute(String name, Object value)；
    Object getAttrbiute(String name, Object value)；
    void removeAttribute(String name, Object value)；
    ```


      >> 代理其他域：`{ 可以使用pageContext向request、session、application对象中存取数据，例如：} `  pageContext.setAttribute("xxx", "XXX", PageContext.SESSION_SCOPE);    
      ```java
      void setAttribute(String name, Object value, int scope)：在指定范围中添加数据；  
      Object getAttribute(String name, int scope)：获取指定范围的数据；
      void removeAttribute(String name, int scope)：移除指定范围的数据；
      ```

      >> 全域查找：pageContext.findAttribute("xxx");从小到大，依赖查找！  

      `{Object findAttribute(String name)：依次在page、request、session、application范围查找名称为name的数据，如果找到就停止查找。这说明在这个范围内有相同名称的数据，那么page范围的优先级最高！}`

      >> 获取其他8个内置对象：  
      ```java
    JspWriter getOut()：获取out内置对象；
    ServletConfig getServletConfig()：获取config内置对象；
    Object getPage()：获取page内置对象；
    ServletRequest getRequest()：获取request内置对象；
    ServletResponse getResponse()：获取response内置对象；
    HttpSession getSession()：获取session内置对象；
    ServletContext getServletContext()：获取application内置对象；
    Exception getException()：获取exception内置对象；
      ```

---


## JSP动作标签  

　　这些jsp的动作标签，与html提供的标签有本质的区别。  
*  动作标签是由tomcat(服务器)来解释执行！它与java代码一样，都是在`服务器端`执行的！

* `html由浏览器`来执行！  
* JSP动作标签的格式：<jsp:标签名 …>

* <jsp:forward>：转发！它与RequestDispatcher的forward方法是一样的，一个是在Servlet中使用，一个是在jsp中使用！`{“别在显示我，去显示它吧！”。}`

* <jsp:include>：包含：它与RequestDispatcher的include方法是一样的，一个是在Servlet中使用，一个是在jsp中使用！  

    > <%@include>和<jsp:include>有什么不同！  
 虽然它们都是用来包含其它JSP页面的，但它们的实现的级别是不同的    
include指令是在`编译级别`完成的包含，即把当前JSP和被包含的JSP合并成一个JSP，然后再编译成一个Servlet。  
include动作标签是在`运行级别`完成的包含，即当前JSP和被包含的JSP都会各自生成Servlet，然后在执行当前JSP的Servlet时完成包含另一个JSP的Servlet。

* <jsp:param>：它用来作为forward和include的子标签！用来给转发或包含的页面传递参数！

---


## JavaBean  
`是一种规范，也就是对类的要求。`

### javaBean的规范：
  1. 必须要有一个默认构造器  

  2. 提供get/set方法，如果`只有get方法`，那么这个属性是`只读属性`！类似的只写  

  3. 属性：有get/set方法的成员，还可以没有成员，只有get/set方法。`属性名称由get/set方法来决定！而不是成员名称！  ` 比如getUsername 属性名称username  `属性的名称就是get/set方法去除get/set后，再把首字母小写了！`

  4. 方法名称满足一定的规范，那么它就是属性！`boolean类型的属性`，它的读方法可以是`is开头，也可以是get开头！`  

`属性名要求：前两个字母要么都大写，要么都小写`

内省：`了解`  

  内省类 --> Bean信息 --> 属性描述符 --> 属性的get/set对应的Method！ --- > 可以反射了！

  `{通过getUsername和setUsername来访问JavaBean属性。后面的word没有看}`

-----------------------

### commons-beanutils，它是依赖内省完成！
  * 导包：
    > commons-beanutils.jar  
    > commons-logging.jar

```java
BeanUtils.getProperty(Object bean, String propertyName)   

BeanUtils.setProperty(Object bean, String propertyName, String propertyValue)   

BeanUtils.populate(Map map, Object bean) //把map中的数据封装到user对象中

CommontUtils.toBean(Map map, Class class)
```
-----------------------

## jsp中与javaBean相关的标签！

* <jsp:useBean> --> `创建或查询`bean  
<jsp:useBean>标签`默认`是把JavaBean对象保存到`page域`，还可以通过scope标签属性来指定保存的范围：
  * <jsp:useBean id="user1" class="cn.itcast.domain.User" scope="session"/>   
  在session域中查找名为user1的bean，如果不存在，创建之  

* <jsp:setProperty>  

  * <jsp:setProperty property="username" name="user1" value="admin"/>   
  设置名为user1的这个javabean的username属性值为admin  

* <jsp:getProperty>  

  * <jsp:getProperty property="username" name="user1"/>   
  获取名为user1的javabean的名为username属性值

---


## EL表达式

### 1. EL是JSP内置的表达式语言！  

  * jsp2.0开始，不让再使用java脚本，而是`使用el表达式和动态标签来替代java脚本！`

  * EL替代的是<%= ... %>，也就是说，EL只能做`输出`！  

  * EL的格式 ${…}    例如：${1 + 2}  

  * 关闭EL  
  
>如果希望整个JSP`忽略EL表达式`，需要在page指令中指定isELIgnored=”true”。  
>如果希望`忽略某个EL表达式`，可以在EL表达式之前添加“\”，例如：\${1 + 2}。  

* 运算符  
比较特殊 ：empty  是否为空  

>${empty “”}，可以判断字符串、数据、集合的`长度是否为0，为0返回true`。empty还可以与not或!一起使用。${not empty “”}

* 表达式格式  
>操作List和数组：${list[0]}、${arr[0]}；  
操作bean的属性：${person.name}、${person[‘name’]}，对应person.getName()方法；  
操作Map的值：${map.key}、${map[‘key’]}，对应map.get(key)。  

* 11个`内置对象`，无需创建即可以使用。这11个内置对象中有10个是Map类型的，最后一个是pageContext对象。  
>pageScope  
requestScope  
sessionScope  
applicationScope  
param  
paramValues  
header  
headerValues  
initParam  
cookie  
pageContext  

* 域相关内置对象`（重点）`  

域内置对象一共有`四个`：  
>pageScope：${pageScope.name}等同与pageContext.getAttribute(“name”)； 

>requestScope：${requestScope.name}等同与request.getAttribute(“name”)；

>sessionScoep： ${sessionScope.name}等同与session.getAttribute(“name”)；  

>applicationScope：${applicationScope.name}等同与application.getAttribute(“name”)；  


### 2. EL表达式来读取四大域
  * ${xxx}，全域查找(依次在pageScope、requesScopet、sessionScope、appliationScope四个域中查找)名为xxx的属性，如果`不存在，输出空字符串，而不是null`。 

  * ${pageScope.xxx}、${requestScope.xxx}、${sessionScope.xxx}、${applicationScope.xxx}，指定域获取属性！  


### 3. javaBean导航
  <%  

	Address address = new Address();
	address.setCity("北京");
	address.setStreet("西三旗");
	
	Employee emp = new Employee();
	emp.setName("李小四");
	emp.setSalary(123456);
	emp.setAddress(address);
	
	request.setAttribute("emp", emp);
  %>  

```java
<h3>使用el获取request域的emp</h3>
${requestScope.emp.address.street }  
<!-- request.getAttribute("emp").getAddress().getStreet() --><br/>

```
### 4. EL可以输出的东西都在11`内置对象`中！11个内置对象，其中10个是Map！pageContext不是map，它就是PageContext类型，1个项9个。
  * 我们已经学习了四个  

  * `param`：对应参数，它是一个Map，其中key参数名，value是参数值，适用于单值的参数。  

  * `paramValues`：对应参数，它是一个Map，其中key参数名，value是多个参数值，适用于多值的参数。  

  * `header`：对应请求头，它是一个Map，其中key表示头名称，value是单个头值，适用于单值请求头  

  * `headerValues`：对应请求头，它是一个Map，其中key表示头名称，value是多个头值，适用于多值请求头  

  * `initParam`：获取```<context-param>```内的参数！
```java
    <context-param>
  	    <param-name>xxx</param-name>
  	    <param-value>XXX</param-value>
    </context-param> 

    <context-param>
  	    <param-name>yyy</param-name>
  	    <param-value>YYY</param-value>
    </context-param>

    ${initParam.xxx}  ` 输出XXX `
```
* `cookie`：Map<String,Cookie>类型，其中key是cookie的name，value是`cookie对象。`${cookie.username.value}  

* `pageContext`：它是PageContext类型！${pageContext.request.contextPath}

---


## EL函数库（由JSTL提供的）  

`对EL的扩展`  

EL函数库就是定义一些有`返回值的静态方法`。然后通过EL语言来调用它们！当然，不只是JSTL可以定义EL函数库，我们也`可以自定义EL函数库`。  

EL函数库中`包含了很多对字符串的操作方法，以及对集合对象的操作。`  
例如：${fn:length(“abc”)}会输出3，即字符串的长度。  

  * 导入标签库：<%@ tablib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
```
String toUpperCase(String input)：把参数转换成大写 

String toLowerCase(String input)：把参数转换成小写  

int indexOf(String input, String substring)：从大串，输出小串的位置！  

boolean contains(String input, String substring)：查看大串中是否包含小串  

boolean containsIgnoreCase(String input, String substring)：忽略大小写的，是否包含  

boolean startsWith(String input, String substring)：是否以小串为前缀  

boolean endsWith(String input, String substring)：是否以小串为后缀  

String substring(String input, int beginIndex, int endIndex)：截取子串  

String substringAfter(String input, String substring)：获取大串中，小串所在位置后面的字符串  

substringBefore(String input, String substring)：获取大串中，小串所在位置前面的字符串  

String escapeXml(String input)：把input中“<”、">"、"&"、"'"、"""，进行转义  

String trim(String input)：去除前后空格 

String replace(String input, String substringBefore, String substringAfter)：替换  

String[] split(String input, String delimiters)：分割字符串，得到字符串数组  

int length(Object obj)：可以获取字符串、数组、各种集合的长度！  

String join(String array[], String separator)：联合字符串数组！
```
```java
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
…
String[] strs = {"a", "b","c"};
List list = new ArrayList();
list.add("a");
pageContext.setAttribute("arr", strs);
pageContext.setAttribute("list", list);
%>

${fn:length(arr) }<br/><!--3-->
${fn:length(list) }<br/><!--1-->
…
```
---


### 自定义函数库
 * 写一个java类，类中可以定义0~N个方法，`static，而且有返回值`的！
 * 在`WEB-INF目录`下创建一个`tld文件`   `url???`
 ```java 
<function>
  <name>fun</name>    
   <function-class> cn.itcast.fn.MyFunction </function-class>
   <function-signature>java.lang.String fun() </function-signature>
</function>
```
 * 在`jsp页面中导入标签库`
   <%@ taglib prefix="it" uri="/WEB-INF/tlds/itcast.tld" %>  

 * 在jsp页面中使用自定义的函数：${it:fun() }


















