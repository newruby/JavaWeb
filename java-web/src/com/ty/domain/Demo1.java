package com.ty.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.ty.utils.CommonUtils;

public class Demo1 {
	@Test
	public void fun1() throws Exception {
		String className = "com.ty.domain.Person";
		Class cl = Class.forName(className);
		Object bean = cl.newInstance();
		
		BeanUtils.setProperty(bean, "name", "TY");
		BeanUtils.setProperty(bean, "age", "23");
		BeanUtils.setProperty(bean, "gender", "male");
		
		
		System.out.println(BeanUtils.getProperty(bean, "name"));
		System.out.println(bean);
	}
	/*
	 * 把map中的属性直接封装到一个bean中　
	 * 
	 * Map: {"username":"zhangSan", "password","123"}
	 * 我们要把map的数据封装到一个javabean中！要求map的key与bean的属性名相同！
	 */
	@Test
	public void fun2() throws Exception {
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", "zhangSan");
		map.put("password", "123");
		
		
		User user = new User();
		BeanUtils.populate(user, map);
		
		System.out.println(user);
	}
	
	@Test
	public void fun3() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("username", "zhangSan");
		map.put("password", "123");
		
		/*
		 * request.getParameterMap();
		 */
		
		User user = CommonUtils.toBean(map, User.class);
		System.out.println(user);
	}

}


/*TY
Person [name=TY, age=23, gender=male]
User [username=zhangSan, password=123]
User [username=zhangSan, password=123]*/