package com.ty.domain;


/*
 * ����ҪΪ��Ա�ṩget/set����������ֻ�ṩһ��Ҳ�ǿ��Եģ���
 * ����Ҫ��Ĭ�Ϲ���������û�εģ���
 * һ����ھ���get/set�����ĳ�Ա������֮Ϊ����
 * 
 * ��ʵ����һ������û�ж�Ӧ�ĳ�Ա������ֻ��get/set����Ҳ�ǿ��Եģ�
 *   ���Ե����ƾ���get/set����ȥ��get/set���ٰ�����ĸСд�ˣ�
 */
public class Person {
	private String name;
	private int age;
	private String gender;
	private boolean bool;
	
	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public String getId() {
		return "fdsafdafdas";
	}

	public String getName() {
		return name;
	}

	public void setName(String username) {
		this.name = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender
				+ "]";
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String name, int age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

}

