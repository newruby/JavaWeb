package com.ty;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * created by TY on 2018/6/5.
 */
public class JaxpTest {
    public static void main(String[] args) throws Exception {
        search();

    }

    public static void search() throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("src/main/resources/person.xml");

        NodeList nodeList = document.getElementsByTagName("name");
        for(int i=0;i<nodeList.getLength();i++) {
            Node name1 = nodeList.item(i);
            System.out.println(name1.getTextContent());
        }
    }

}
