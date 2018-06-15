package com.ty.utils;


import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

public class CommonUtils {
	/**
	 * ���ɲ��ظ���32λ���Ĵ�д�ַ���
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * ��mapת����ָ�����͵�javaBean����
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			/*
			 * 1. ����ָ�����͵�javabean����
			 */
			T bean = clazz.newInstance();
			/*
			 * 2. �����ݷ�װ��javabean��
			 */
			BeanUtils.populate(bean, map);
			/*
			 * ����javabean����
			 */
			return bean;
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}




