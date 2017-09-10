package com.wechat.test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamTest {
	public static void main(String[] args) {
		Person person = new Person();
		person.name = "安迪";
		person.sex = "女";
		person.address = "上海";
		System.out.println(javaObject2Xml(person));
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<person>"
				+ "<name character=\"乖乖女\">关关</name>"
				+ "<sex>女</sex>"
				+ "<address>无锡市</address>"
				+ "</person>";
		Person person2 = (Person) xml2JavaObject(xml);
		System.out.println(person2.toString());
	}
	
	public static String javaObject2Xml(Person person){
		XStream xs = new XStream(new DomDriver());
		xs.alias("person", person.getClass());
		return xs.toXML(person);
	}
	
	public static Object xml2JavaObject(String xml){
		XStream xs = new XStream(new DomDriver());
		xs.alias("person", Person.class);
		Person person = (Person) xs.fromXML(xml);
		return person;
	}
}

class Person{
	String name;
	String sex;
	String address;
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + ", address=" + address + "]";
	}
}
