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

![ex](/image/异步交互和同步交互.bmp "异步交互和同步交互")


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












