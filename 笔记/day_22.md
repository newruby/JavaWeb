# day_22


上传(上传不能使用BaseServlet)

## 1. 上传对表单限制
  * method="post"
  * `enctype="multipart/form-data"`
  * 表单中需要添加文件表单项：`<input type="file" name="xxx" />`


示例代码
```javascript
<form action="xxx" method="post" enctype="multipart/form-data">
  用户名；<input type="text" name="username"/><br/>
  照　片：<input type="file" name="zhaoPian"/><br/>
  <input type="submit" value="上传"/>
</form>
```

效果
<form action="xxx" method="post" enctype="multipart/form-data">
  用户名；<input type="text" name="username"/><br/>
  照　片：<input type="file" name="zhaoPian"/><br/>
  <input type="submit" value="上传"/>
</form>

## 2. 上传对Servlet限制

  * 表单为enctype="multipart/form-data"时，方法request.getParametere("xxx");作废。永远都返回null


  * `ServletInputStream` request.getInputStream();包含整个请求的体！

-----------------------------

### 多部件表单的体

1. 每隔出多个部件，即一个表单项一个部件。  

2. 一个部件中自己包含`请求头和空行，以及请求体`。   

3. **普通表单项：(name和value)**
  > 1个头：Content-Disposition：包含name="xxxx"，即表单项名称。  
  > 体就是表单项的值
4. **文件表单项：  (name/filename和Content-Type)**

  > 2个头：
    Content-Disposition：包含name="xxxx"，即表单项名称；还有一个filename="xxx"，表示上传文件的名称  
     Content-Type：它是上传文件的MIME类型，例如：image/pjpeg，表示上传的是图片，图上中jpg扩展名的图片。  
  > 体就是上传文件的内容。

-----

###  commons-fileupload
  * commons-fileupload.jar  

  * commons-io.jar

这个组件，它会帮我们解析request中的上传数据，解析后的结果是一个表单项数据封装到一个FileItem对象中。我们只需要调用FileItem的方法即可！

-----

1. 上传三步
  相关类：
  * 工厂：DiskFileItemFactory
  * 解析器：ServletFileUpload
  * 表单项：FileItem

  1). 创建工厂：`DiskFileItemFactory factory = new DiskFileItemFactory();  `

  2). 创建解析器：`ServletFileUpload sfu = new ServletFileUpload(factory);  `

  3). 使用解析器来解析request，得到FileItem集合：`List<FileItem> fileItemList = sfu.parseRequest(request);`

2. FileItem  `*`  

`(普通表单项)`
  * boolean isFormField()：是否为普通表单项！返回true为普通表单项，如果为false即文件表单项！
  * String getFieldName()：返回当前表单项的名称；
  * String getString(String charset)：返回表单项的值；  

---
---
`(文件表单项)`

  * String getName()：返回上传的文件名称
  * long getSize()：返回上传文件的字节数
  * InputStream getInputStream()：返回上传文件对应的输入流
  * void write(File destFile)：把上传的文件内容保存到指定的文件中。
  * String getContentType();

---

### 小结  
高亮的部分  
示例代码和一些方法文件表单项和普通表单项  怎么说背过
？？？


























































