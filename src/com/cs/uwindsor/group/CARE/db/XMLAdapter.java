/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cs.uwindsor.group.CARE.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This was originally made for a different project that used an actual XML database
 * so there is a lot of unused methods that can be removed the only ones used in this
 * project are the constructors and the getAllRecords() method
 * @author Matt
 *
 */
public class XMLAdapter implements Parcelable, DBAdapter<Element> {

    private String resourceName;
    private String entityElementsName;
    private Document dom;

    /**
     * Construct an XMLAdapter for the xml file referred by resorceName.
     * The entityElement is the tag name for each entity in the XML file
     * @param resourceName
     * @param primaryKeyName
     */
    public XMLAdapter(String resourceName, String entityElementsName) throws IOException, ParserConfigurationException, SAXException {
        this.init(resourceName, entityElementsName);
    }
    
    public XMLAdapter(Document resourceName, String entityElementsName) throws IOException, ParserConfigurationException, SAXException {
        this.initd(resourceName, entityElementsName);
    }
    
    private void initd(Document resourceName, String entityElementsName) throws IOException, ParserConfigurationException, SAXException {
        this.entityElementsName = entityElementsName;
        this.dom = resourceName;
    }

    private void init(String resourceName, String entityElementsName) throws IOException, ParserConfigurationException, SAXException {
        URL url = XMLAdapter.class.getResource(resourceName);
        if (url == null) {
            throw new FileNotFoundException("Resource: " + resourceName + " Not Found");
        }
        this.resourceName = url.getFile().toString();
        this.entityElementsName = entityElementsName;
        this.dom = XMLHelper.getXMLDoc(this.resourceName);
    }

    public List<String> getPropertyNames() {
        ArrayList<String> headers = new ArrayList<String>();
        NodeList records = dom.getElementsByTagName(entityElementsName);
        /* A silly assumption here is that the XML Doc always contains some data */
        if (records.getLength() > 0) {
            Element entity = (Element) records.item(0);
            if (entity.getAttributeNode("id") != null) {
                headers.add("id");
            }
            NodeList childs = entity.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++) {
                if (childs.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    headers.add(childs.item(i).getNodeName());
                }
            }
        }
        return headers ;
    }

    /**
     * Check to see if a given primary key exists in the database
     * @param keyValue
     * @return true if primary key exists false otherwise
     *
     */
    private boolean primaryKeyExists(String keyValue) {
        List<Element> records = this.getAllRecords();
        for (Element element : records) {
            if (element.getAttribute("id").equals(keyValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create new Element in the XML database
     * Created based on given primary key as well as values
     * for the other parts of it
     *
     * @param key
     * @param data ex. For creating a Student data would contain fname & lname.
     * @return Created Element
     * @throws Exception if data given is not a string or primary key already exists
     *
     */
    public Element create(Object key, Object... data) throws Exception {
        if (key instanceof String) {
            if (primaryKeyExists((String) key)) {
                throw new Exception("Primary key:" + (String) key + " exists");
            }
        } else {
            throw new Exception("Only String data type supported for XML Adpaters");
        }
        Element element = dom.createElement(entityElementsName);
        List<String> headers = this.getPropertyNames();
        for (Object val : data) {
            if (!(val instanceof String)) {
   
                throw new Exception("Only String data type supported for XML Adpaters");
            }
        }
        element.setAttribute(headers.get(0), (String) key);
        Node temp[] = new Node[headers.size()];
        for(int i=0; i < headers.size()-1; i++){
            temp[i] = dom.createElement(headers.get(i+1));
            temp[i].appendChild(dom.createTextNode((String) data[i]));
            element.appendChild(temp[i]);
        }
        dom.getDocumentElement().appendChild(element);
        return element;
    }

    public Element read(Object key) throws Exception {
        if (!(key instanceof String)) {
            throw new Exception("Only String data type supported for XML Adpaters");
        }
        List<Element> nodes = this.getAllRecords();
        for (Element element : nodes) {
            String id = element.getAttribute("id");
            if (id != null && id.equals(key)) {
                return element;
            }
        }
        return null;
    }

    /**
     *
     *
     * @param primaryKey
     * used to identify the element being updated
     * @param keyToChange
     * used to identify which parameter is going to be changed
     * @param newValue
     * the new value to update to
     * @return
     * a boolean value to check if the update was successful
     * @throws Exception
     * if the data provided is not a string or if the primary key is invalid
     *
     *
     * Effectively iterates through the children of the primary key, if
     * the parameter is equivalent to the keyToChange then replace the current
     * node with the newValue object
     *
     */
    public boolean update(Object primaryKey, Object keyToChange, Object newValue) throws Exception {
        if (!(primaryKey instanceof String)) {
            throw new Exception("Only String data type supported for XML Adpaters");
        } else if (!(keyToChange instanceof String)) {
            throw new Exception("Only String data type supported for XML Adpaters");
        } else if (!(newValue instanceof String)) {
            throw new Exception("Only String data type supported for XML Adpaters");
        } else if (this.getPropertyNames().indexOf(keyToChange) < 0) {
            throw new Exception("Invalid key name:" + keyToChange);
        }

        List<Element> retElements = this.getAllRecords();

        for (Element element : retElements){
            String id = element.getAttribute("id");
            if (id.equals(primaryKey)){
                if(keyToChange.equals("id")){
                    element.getAttributeNode("id").setTextContent((String)newValue);
                    return true;
                }
                else{
                    NodeList nodes = element.getChildNodes();
                    for(int i=0; i<=nodes.getLength(); i++){
                        System.out.print(nodes.item(i).getNodeName());
                        if(nodes.item(i).getNodeName().equals(keyToChange)){
                            Node newChild = dom.createElement((String)keyToChange);
                            newChild.appendChild(dom.createTextNode((String)newValue));
                            element.replaceChild(newChild, nodes.item(i));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * Deletes an element from the XML database
     * Requires the primary key in order to find the object
     * that is to be removed.
     *
     * @param key
     * @return a boolean value to make sure the delete was successful
     * @throws Exception
     *
     */
    public boolean delete(Object key) throws Exception {
        if (key instanceof String) {
            if (! primaryKeyExists((String) key)) {
                throw new Exception("Primary key:" + (String) key + "dosen't exist");
            }
        }
        else {
            throw new Exception("Only String data type supported for XML Adpaters");
        }
        List<Element> retElements = this.getAllRecords();

        for (Element element : retElements){
            String id = element.getAttribute("id");
            if (id.equals(key)){
                dom.getDocumentElement().removeChild(element);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Commit changes to the physical database stored at 'resourceName'
     *
     * @throws Exception when no file is found at 'resourceName'
     *
     */
    public void commit() throws Exception {
        PrintWriter pw = new PrintWriter(new FileOutputStream(resourceName), true);
        {
            pw.println(XMLHelper.xmlDocToString(dom));
            pw.close();
        }
    }

    /**
     * Count the number of elements in the XML database
     *
     * @return the number of elements
     *
     */
    public int size() {
        int size = 0;
        Element docEl = dom.getDocumentElement();
        NodeList nodes = docEl.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++){
            if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
                size++;
        }
        return size;
    }

    /**
     * Find all the Elements directly under the Document Element
     *
     * @return a list of all those elements found
     *
     */
    public List<Element> getAllRecords() {
        ArrayList<Element> retNodes = new ArrayList<Element>();
        Element docEl = dom.getDocumentElement();
        NodeList nodes = docEl.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++){
                if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)
                    retNodes.add((Element) nodes.item(i));
            }
        return retNodes;
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
