/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cs.uwindsor.group.CARE.db;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLHelper {
    
	/**
     * Given a XML String it returns a XML Document
     *
     * @param string
     * @return doc
     * @throws Exception
     */
	public static Document xmlStringToDocument(String xmlString) throws IOException,ParserConfigurationException,SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlString));
        Document dom = builder.parse(is);
        return dom;
    }


    /**
     * Given a XML Document it returns a String containing the Document
     *
     * @param doc
     * @return
     * @throws Exception
     */
    public static String xmlDocToString(Document doc) throws IllegalArgumentException,TransformerException,TransformerFactoryConfigurationError {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        //initialize StreamResult with File object to save to file
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        String xmlString = result.getWriter().toString();
        return xmlString;
    }

    public static Document getXMLDoc(String fileName) throws IOException, ParserConfigurationException,SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return  db.parse(fileName);
    }

    /**
     * Creates and returns a fresh Document object that can be used to build XML Documents
     * @return
     * @throws ParserConfigurationException
     */
    public static Document createNewDocument() throws ParserConfigurationException{
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.newDocument();
        return doc;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(xmlDocToString(getXMLDoc("/home/shamual/Desktop/abc.xml")));
    }


}
