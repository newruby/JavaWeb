#day_20  
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