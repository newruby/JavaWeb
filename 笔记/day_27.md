# day_27


## 泛型
1. 泛型类：具有一个或多个类型变量的类，称之为泛型类！
```
class A<T> {
}
```
2. 在创建泛型类实例时，需要为其类型变量赋值
```
A<String> a = new A<String>();
```
  * 如果创建实例时，不给类型变量赋值，那么会有一个警告！

3. 泛型方法：具有一个或多个类型变量的方法，称之为泛型方法！
```
class A<T> {
  public T fun(T t1) {}
}
```
fun()方法不是泛型方法！它是泛型类中的一个方法！
```
public <T> T fun(T t1) {} --> 它是泛型方法
```
* 泛型方法与泛型类没什么关系，泛型方法不一定非要在泛型类中！

4. 泛型在类中或方法中的使用
  * 泛型类中使用泛型：
    > 成员类型
    > 返回值和参数类型
    > 局部变量的引用上
```
class A<T> {
  private T bean;//泛型可在成员变量上使用
  public T fun(T t) {}//泛型可以在类中的方法上（返回值和参数类型）使用！

  public void fun2() {//泛型还可以在局部变量的引用类型上使用
    T b = ...
    new T();//不行的！
  }
}
```
---

5. 泛型的继承和实现
```
class A<T> {}

class AA extends A<String> {} //不是泛型类，只是它父类是泛型类！
```
5.1 继承泛型类

  * 子类不是泛型类：需要给父类传递类型常量
    > 当给父类传递的类型常量为String时，那么在父类中所有T都会被String替换！
  * 子类是泛型类：可以给父类传递类型常量，也可以传递类型变量
```
class AA1 extends A<Integer> {}

class AA3<E> extends A<E> {}
```



---

## 泛型的通配符

1. 通配符使用的场景  
方法的形参！

2. 通配符的优点  
  使方法更加通用！    

*通配符只能出现在左边！即不能在new时使用通配符！！！*

3. 通配符分类  
  无界通配：?  
  子类限定：? extends Object  
  父类限定：? super Integer  

4. 通配符缺点  
  使变量使用上不再方便  
  无界：参数和返回值为泛型的方法，不能使用！  
  子类：参数为泛型的方法(ex:add)不能使用  
  父类：返回值为泛型的方法(ex:get)不能使用  

```java

	@Test

	
	/*
	 * 其中的?就是通配符
	 * 通配符只能出现在左边！即不能在new时使用通配符！！！
	 * List<?> list = new ArrayList<String>();
	 */
	/*
	 * ?它表示一个不确定的类型，它的值会在调用时确定下来
	 */
	public void print(List<?> list) {
		/*
		 * 当使用通配符时，对泛型类中的参数为泛型的方法起到了副作用，不能再使用！
		 */
//		list.add("hello");
		/*
		 * 当使用通配符时，泛型类中返回值为泛型的方法，也作废了！
		 */
		Object s = list.get(0);
		/*
		 * 通配符好处：可以使泛型类型更加通用！尤其是在方法调用时形参使用通配符！
		 */
	}
	
	public void fun3() {
		List<Integer> intList = new ArrayList<Integer>();
		print1(intList);
		
		List<Long> longList = new ArrayList<Long>();
		print1(longList);
	}
	
	/*
	 * 给通配符添加了限定：
	 *   只能传递Number或其子类型
	 *   子类通配符对通用性产生了影响，但使用形参更加灵活
	 */
	public void print1(List<? extends Number> list) {
		/*
		 * 参数为泛型的方法还是不能使用
		 */
//		list.add(new Integer(100));
		/*
		 * 返回值为泛型的方法可用了！
		 */
		Number number = list.get(0);
	}
	
	public void fun4() {
		List<Integer> intList = new ArrayList<Integer>();
		print2(intList);
		
		List<Number> numberList = new ArrayList<Number>();
		print2(numberList);
		
		List<Object> objList = new ArrayList<Object>();
		print2(objList);
	}
	
	/*
	 * 给通配符添加了限定
	 *   只能传递Integer类型，或其父类型
	 */
	public void print2(List<? super Integer> list) {
		/*
		 * 参数为泛型的方法可以使用了
		 */
		list.add(new Integer(100));
		/*
		 * 返回值为泛型的方法，还是不能使用
		 */
		Object obj =  list.get(0);
	}
}

```

5. 比较通配符  
比如方法
boolean addAll(Collection<E> c)
```
List<Number> numList = new ArrayList<Number>();
List<Integer> intList = new ArrayList<Integer>();
numList.addAll(intList);//addAll(Collection<Number> c), 传递的是List<Integer>


boolean addAll(Collection<? extends E> c)

List<Number> numList = new ArrayList<Number>();
List<Integer> intList = new ArrayList<Integer>();
numList.addAll(intList);//addAll(Collection<? extends Number> c), 传递的是List<Integer>
```



小结  
* 编译器认为  
```
List<String> strList = new ArrayList<String>();
List<Integer> sintList = new ArrayList<Integer>();
都是List list = new ArrayList();
```
```java

	public void fun1() {
	
		
		Object[] objArray = new String[10];
		objArray[0] = new Integer(100);//ArrayStoreException

		List<Object> objList = new ArrayList<String>();
		objList.add(new Integer(100));//编译错
		/*
		 * 泛型引用和创建两端，给出的泛型变量必须相同！
		 */
	}
	
	public void fun2() {
		List<Integer> integerList = new ArrayList<Integer>();
		print(integerList);
		
		List<String> stringList = new ArrayList<String>();
		print(stringList);
	}


```









































