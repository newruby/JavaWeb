# day_23

## 1. ajax
  * asynchronous javascript and xml：异步的js和xml  

  * 它能使用js访问服务器，而且是异步访问！

  * 服务器给客户端的响应一般是整个页面，一个html完整页面！但在ajax中因为是*局部刷新*，那么服务器就不用再响应整个页面！而只是数据！
    > text：纯文本  
    > xml：  
    > json：它是js提供的数据交互格式，它在ajax中最受欢迎！

## 2. 异步交互和同步交互
  * 同步：

    > 发一个请求，就要等待服务器的响应结束，然后才能发第二个请求！中间这段时间就是一个字“卡”  
    > 刷新的是整个页面！
    
  * 异步：

    > 发一个请求后，无需等待服务器的响应，然后就可以发第二个请求！  
    > 可以使用js接收服务器的响应，然后使用js来局部刷新！

"![ex](/image/异步交互和同步交互.bmp "异步交互和同步交互")" 

## 3. ajax应用场景  

  * 搜索引擎搜索框

  * 用户注册时（校验用户名是否被注册过）

## 4. ajax的优缺点
  优点：  

  * 异步交互：*增强了用户的体验*！  
  * 性能：因为服务器无需再响应整个页面，只需要*响应部份内容*，所以服务器的*压力减轻了*！

  缺点：  

  * ajax不能应用在所有场景！

  * ajax无端的增多了对服务器的*访问*次数，给服务器带来了*压力*！

---


## ajax发送异步请求（四步操作）!!!
```javascript
window.onload = function() {
};
```



1. 第一步（**得到XMLHttpRequest**）
  * ajax其实只需要学习一个对象：XMLHttpRequest，如果掌握了它，就掌握了ajax！！！
  * 得到XMLHttpRequest
    > *大多数浏览器*都支持：var xmlHttp = new XMLHttpRequest();  
    > IE6.0：var xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
    > IE5.5以更早版本的IE：var xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");

  * 编写创建XMLHttpRequest对象的函数      

*`通过嵌try-catch达到判断、if效果`*

  ```javascript
  function createXMLHttpRequest() {
      try {
          return new XMLHttpRequest();
      } catch(e) {
          try {
	      return new ActiveXObject("Msxml2.XMLHTTP");
	  } catch(e) {
	      try {
	          return new ActiveXObject("Microsoft.XMLHTTP");
	      } catch(e) {
	          alert("你用的是什么浏览器啊？");
	          throw e;
	      }
	  }
      }
  }
  ```

2. 第二步（**打开与服务器的连接**）

  * xmlHttp.open()：用来打开与服务器的连接，它需要*三个参数*：

    > 请求方式：可以是GET或POST  
    > 请求的URL：指定服务器端资源，例如；/day23_1/AServlet    
    > 请求是否为异步：如果为true表示发送异步请求，否则同步请求！  

  ```
   xmlHttp.open("GET", "/day23_1/AServlet", true);
  
  ```

3. 第三步（**发送请求**）

  * xmlHttp.send(null)：如果不给可能会造成部份浏览器无法发送！

    > *参数：就是请求体内容！如果是GET请求(无请求体)，必须给出null。*

4. 第四步（**接收服务器响应**）

  * 在xmlHttp对象的一个事件上注册监听器：onreadystatechange  

  * xmlHttp对象一共有5个状态：

    > 0状态：刚创建，还没有调用open()方法;   
    > 1状态：请求开始：调用了open()方法，但还没有调用send()方法  
    > 2状态：调用完了send()方法了；  
    > 3状态：服务器已经开始响应，但不表示响应结束了！  
    > 4状态：服务器响应结束！（通常我们`只关心这个状态`！！！）   

  * 得到xmlHttp对象的状态：

```
    var state = xmlHttp.readyState;//可能是0、1、2、3、4
```
  * 得到服务器响应的状态码

```
 var status = xmlHttp.status;//例如为200、404、500
```
  * 得到服务器响应的内容
```
    var content = xmlHttp.responseText;//得到服务器的响应的文本格式的内容 ，返回字符串
     
    var content = xmlHttp.responseXML;//得到服务器的响应的xml响应的内容，它是Document对象了！
```
*responseText这个既可以得到text也可以得到xml(自己解析)*

```javascript
  xmlHttp.onreadystatechange = function() {//xmlHttp的5种状态都会调用本方法
      if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {//双重判断：判断是否为4状态，而且还要判断是否为200
          // 获取服务器的响应内容
	  var text = xmlHttp.responseText;
      }
  };

```

---

## 第二例：发送POST请求(如果发送请求时需要带有参数，一般都用POST请求)


*与GET请求的不同点*

* open：xmlHttp.open("POST" ....);  

* 添加一步：设置Content-Type请求头：

```
xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

```
* send：
```
xmlHttp.send("username=zhangSan&password=123");//发送请求时指定请求体
```


---


## 第三例：注册表单之校验用户是否注册！

1. 编写页面：

  * ajax3.jsp
    > 给出注册表单页面  
    > 给用户名文本框添加onblur事件的监听  
    > 获取文本框的内容，通过ajax4步发送给服务器，得到响应结果  
      * 如果为1：在文本框后显示“用户名已被注册”
      * 如果为0：什么都不做！




2. 编写Servlet

  * ValidateUsernameServlet
    > 获取客户端传递的用户名参数  
    > 判断是否为xxx
      * 是：返回1
      * 否：返回0

---

响应内容为xml数据

  * 服务器端：
    > 设置响应头：ContentType，其值为：text/xml;charset=utf-8

  * 客户端：
    > var doc = xmlHttp.responseXML;//得到的是Document对象！

---
## 第四例：响应内容为xml数据

*window.addEventListener 没有这个是IE尝试家户代码在判断if-else*
```javascript

// 创建异步对象
function createXMLHttpRequest() {

}

window.onload = function() {//文档加载完毕后执行
	var btn = document.getElementById("btn");
	btn.onclick = function() {//给按钮的点击事件注册监听
		/*
		ajax四步操作，得到服务器的响应
		把响应结果显示到h1元素中
		*/
		/*
		1. 得到异步对象 
		*/
		var xmlHttp = createXMLHttpRequest();
		/*
		2. 打开与服务器的连接
		  * 指定请求方式
		  * 指定请求的URL
		  * 指定是否为异步请求
		*/
		xmlHttp.open("GET", "<c:url value='/BServlet'/>", true);
		/*
		3. 发送请求
		*/
		xmlHttp.send(null);//GET请求没有请求体，但也要给出null，不然FireFox可能会不能发送！
		/*
		4. 给异步对象的onreadystatechange事件注册监听器
		*/
		xmlHttp.onreadystatechange = function() {//当xmlHttp的状态发生变化时执行
			// 双重判断：xmlHttp的状态为4（服务器响应结束），以及服务器响应的状态码为200（响应成功）
			if(xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				// 获取服务器的响应结果（xml）
				var doc = xmlHttp.responseXML;
				// 查询文档下名为student的所有元素，得到数组，再取下标0元素
				var ele = doc.getElementsByTagName("student")[0];
				var number = ele.getAttribute("number");//获取元素名为number的属性值
				var name;
				var age;
				var sex;
				
				// 处理浏览器的差异
				if(window.addEventListener) {
					name = ele.getElementsByTagName("name")[0].textContent;//其他浏览器
				} else {
					name = ele.getElementsByTagName("name")[0].text;//IE支持
				}
				if(window.addEventListener) {
					age = ele.getElementsByTagName("age")[0].textContent;//其他浏览器
				} else {
					age = ele.getElementsByTagName("age")[0].text;//IE支持
				}
				if(window.addEventListener) {
					sex = ele.getElementsByTagName("sex")[0].textContent;//其他浏览器
				} else {
					sex = ele.getElementsByTagName("sex")[0].text;//IE支持
				}

				var text = number + ", " + name + ", " + age + ", " + sex;
				document.getElementById("h1").innerHTML = text;
			}
		};
	};
};
```


---
*7.23未看*

## 第五例：省市联动

1. 页面
  <select name="province">
    <option>===请选择省份===</option>
  </select>
  <select name="city">
    <option>===请选择城市===</option>  
  </select>

2. ProvinceServlet
  * ProvinceServlet：当页面加载完毕后马上请求这个Servlet！
    > 它需要加载china.xml文件，把所有的省的名称使用字符串发送给客户端！

3. 页面的工作
  * 获取这个字符串，使用逗号分隔，得到数组
  * 循环遍历每个字符串（省份的名称），使用每个字符串创建一个<option>元素添加到<select name="province">这个元素中

4. CityServlet
  * CityServlet：当页面选择某个省时，发送请求！
  * 得到省份的名称，加载china.xml文件，查询出该省份对应的元素对象！，把这个元素转换成xml字符串，发送给客户端

5. 页面的工作
  * 把<select name="city">中的所有子元素删除，但不要删除<option>===请选择城市===</option>
  * 得到服务器的响应结果：doc！！！
  * 获取所有的<city>子元素，循环遍历，得到<city>的内容
  * 使用每个<city>的内容创建一个<option>元素，添加到
  <selectname="city">

---

## XStream  
1. 作用  
  * 可以把JavaBean转换为（序列化为）xml  


2. XStream的jar包  
  * 核心JAR包：xstream-1.4.7.jar；
  * 必须依赖包：xpp3_min-1.1.4c（XML Pull Parser，一款速度很快的XML解析器）；  
  
3. `使用步骤`
  ```
   XStream xstream = new XStream();
  String xmlStr = xstream.toXML(javabean);
```
4. 使用细节
  * 别名：把类型对应的元素名修改了
```
xstream.alias("china", List.class)：让List类型生成的元素名为china  
xstream.alias("province", Province.class)：让Province类型生成的元素名为province  
```
  * 使用为属性：默认类的成员，生成的是元素的子元素！我们希望让类的成员生成元素的属性
```
xstream.useAttributeFor(Province.class, "name")：把Province类的名为name成员，生成<province>元素的name属性  
```
  * 去除Collection类型的成名：我们只需要Collection的内容，而不希望Collection本身也生成一个元素
```
xstream.addImplicitCollection(Province.class, "cities")：让Province类的名为cities(它是List类型的，它的内容还会生成元素)的成名不生成元素
```
  * 去除类的指定成名，让其不生成xml元素
```
xstream.omitField(City.class, "description")：在生成的xml中不会出现City类的名为description的对应的元素！
```
---

## JSON
1. json是什么
* 它是js提供的一种数据交换格式！  


2. json的语法
  * {}：是对象！
    > 属性名必须使用双引号括起来！单引不行！！！
    > 属性值：
      * null
      * 数值
      * 字符串
      * 数组：使用[]括起来
      * boolean值：true和false

3. 应用json  
  ` var person = {"name":"zhangSan", "age":18, "sex":"male"};`

  ```javascript
  var text = xmlHttp.responseText;//它是一个json串
				// 执行json串
	var person = eval("(" + text + ")");
	var s = person.name + ", " + person.age + ", " + person.sex;
	document.getElementById("h3").innerHTML = s;
  ```

4. json与xml比较
* 可读性：XML胜出
* 解析难度：JSON本身就是JS对象（主场作战），所以简单很多
* 流行度：XML已经流行好多年，但在AJAX领域，JSON更受欢迎。

-----------------------------------

## json-lib

1. 是什么？
  * 它可以把javabean转换成json串  


2. jar包
  * commons-beanutils-1.8.3.jar  
  commons-collections-3.2.1.jar  
  commons-lang-2.5.jar  
  commons-logging-1.1.1.jar  
  ezmorph-1.0.6.jar  
  json-lib-2.4-jdk15.jar  
  xom-1.1.jar

3. 核心类
  * JSONObject --> Map
    > toString();  
    > JSONObject map = JSONObject.fromObject(person)：把对象转换成JSONObject对象
  * JSONArray --> List
    > toString()   
    > JSONArray jsonArray = JSONObject.fromObject(list)：把list转换成JSONArray对象



小结：暂时  
<获取到的标签>.innerHTML = 要打印的东西

servlet中 
response.getWriter().print(str);  
var str = "xxx";

var str = "1 + 2";//String
var sum = eval("(" + str + ")");
alert(sum);

eval函数把str当作js语句执行，前后要有"("和 ")"















