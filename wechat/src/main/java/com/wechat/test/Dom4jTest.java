package com.wechat.test;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Dom4jTest {
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<person>"
				+ "<name character=\"乖乖女\">关关</name>"
				+ "<sex>女</sex>"
				+ "<address>无锡市</address>"
				+ "</person>";
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			List<Element> elementList = root.elements();
			for(Element e : elementList){
				Attribute attr = e.attribute("character");
				e.attributeValue("character");
				if(attr != null){
					System.out.println(attr.getName()+ " "+attr.getValue());
				}
				System.out.println(e.getName()+" => "+e.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
}
