package ru.spbu.math;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public enum SimpleIndexer {
    INSTANCE;

    private HashMap<String, HashSet<Integer>> index;
    private List<TestDocument> testDocuments;

    SimpleIndexer() {
        index = new HashMap<String, HashSet<Integer>>();

        try {
            testDocuments = parseDocument(new File("testDocument.sgm"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (TestDocument testDocument : testDocuments) {

        }

    }

    public static SimpleIndexer getInstance() {
        return INSTANCE;
    }

    private List<TestDocument> parseDocument(File file)
        throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        NodeList reuters = document.getElementsByTagName("REUTERS");

        List<TestDocument> testDocuments = new ArrayList<TestDocument>();
        for (int i = 0; i < reuters.getLength(); ++i) {
            Node currentNode = reuters.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) currentNode;
                int id = Integer.parseInt(element.getAttribute("NEWID"));
                String title = null;
                String text = null;

                Node textNode = element.getElementsByTagName("TEXT").item(0);
                if (textNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element textElement = (Element) textNode;

                    NodeList titleNodeList = textElement.getElementsByTagName("TITLE");
                    if (titleNodeList.getLength() == 1) {
                        title = titleNodeList.item(0).getTextContent();
                    }

                    NodeList bodyNodeList = textElement.getElementsByTagName("BODY");
                    if (bodyNodeList.getLength() == 1) {
                        text = bodyNodeList.item(0).getTextContent();
                    }
                }

                if (title != null && text != null) {
                    testDocuments.add(new TestDocument(id, title, text));
                }

            }
        }

        return testDocuments;
    }
}
