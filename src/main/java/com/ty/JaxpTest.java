package com.ty;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * created by TY on 2018/6/5.
 */

/**
 * day6
 * @param
 * @return
 */
public class JaxpTest {
    public static void main(String[] args) throws Exception {
//        search();
        selectSin();
        addNode();

    }
    private static Document returnDocument() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("src/main/resources/person.xml");
        return document;
    }
    private static void reWrite(Document document) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult("src/main/resources/person.xml"));
    }

    public static void search() throws Exception{
        Document document = returnDocument();

        NodeList nodeList = document.getElementsByTagName("name");
        for(int i=0;i<nodeList.getLength();i++) {
            Node name1 = nodeList.item(i);
            System.out.println(name1.getTextContent());
        }
    }

    public static void selectSin() throws Exception {
        Document document = returnDocument();

        NodeList nodeList = document.getElementsByTagName("name");
        Node name1 = nodeList.item(0);
        System.out.println(name1.getTextContent());
    }

    public static void addNode() throws Exception {
        Document document = returnDocument();

        NodeList nodeList = document.getElementsByTagName("p1");
        Node p1 = nodeList.item(0);

        Element sex = document.createElement("sex");
        Text text = document.createTextNode("male");
        sex.appendChild(text);

        p1.appendChild(sex);

        reWrite(document);

    }

}
