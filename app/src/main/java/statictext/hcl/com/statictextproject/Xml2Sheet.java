package statictext.hcl.com.statictextproject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Xml2Sheet {

    public static void main(String[] args) {
        File file = new File("C:\\Users\\jagannadhamr\\Desktop\\ShoppixDependencies.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element root = document.getDocumentElement();
            System.out.println(root.getNodeName());
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node node = childNodes.item(i);

//                System.out.println(node.getChildNodes().getLength());

                if (node.getChildNodes().getLength() != 0) {
//                    System.out.println("block number"+i);
                    Element element = (Element) node;
//                    element.getAttribute("path");
                    System.out.println("Header: " + element.getAttribute("path"));

                    NodeList childNodes1 = element.getChildNodes();
                    for (int j = 0; j < childNodes1.getLength(); j++) {
                        System.out.println("DEP: "+root.getChildNodes().item(3).getAttributes().item(0).getNodeValue());
//                        Element item = childNodes1.item(j).getNodeName();
//                        System.out.println(item.getAttribute("path"));
                    }


                 /*   for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                        System.out.println(node.getChildNodes().item(j).getAttributes());
//                        Element eElement = (Element) node.getChildNodes().item(j);
//                        System.out.println("Dependies: "+eElement.getAttribute("path"));

                    }*/
                }
            }

            //NodeList childNodes = document.getElementsByTagName("file");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Extract all text children of an element
     */
    public static String extractTextChildren(Element parentNode) {
        NodeList childNodes = parentNode.getChildNodes();
        String result = new String();
        for (int i = 0; i < childNodes.getLength(); i++) {
            NodeList childList = childNodes.item(i).getChildNodes();
            for (int j = 0; j < childList.getLength(); j++) {
                //result += childList.item(j).get;
            }
            //if (node.getNodeType() == Node.TEXT_NODE) {

            //}
        }
        return result;
    }
}
