/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author dvice
 */
public class XmlParser {
    private ConnectionData connectionData;
    private Document document;
    
    private static final Logger logger = Logger.getLogger(XmlParser.class.getName());
    
    /**
     * @return the connectionData
     */
    public ConnectionData getConnectionData() {
        return connectionData;
    }
    
    /**
     *
     * @param file
     */
    public XmlParser(String file)
    {
        parseXmlFile(file);
    }
    
    private void parseXmlFile(String fileName)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            
            document = db.parse(ClassLoader.getSystemResourceAsStream(fileName));
            
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            
            for(int x = 0; x < nodeList.getLength(); x++)
            {
                Node node = nodeList.item(x);
                
                if(node instanceof Element)
                {
                    String type = node.getAttributes().getNamedItem("type").getNodeValue();
                    
                    connectionData = new ConnectionData();
                    connectionData.setType(type);
                    
                    NodeList childNodes = node.getChildNodes();
                    
                    for(int y = 0; y < childNodes.getLength(); y++)
                    {
                        Node kidNode = childNodes.item(y);
                        
                        if(kidNode instanceof Element)
                        {
                            String nodeData = kidNode.getLastChild().getTextContent().trim();
                            switch(kidNode.getNodeName())
                            {
                                case "url":
                                    connectionData.setUrl(nodeData);
                                    break;
                                case "ipaddress":
                                    connectionData.setIpaddress(nodeData);
                                    break;
                                case "port":
                                    connectionData.setPort(nodeData);
                                    break;
                                case "database":
                                    connectionData.setDatabase(nodeData);
                                    break;
                                case "login":
                                    connectionData.setLogin(nodeData);
                                    break;
                                case "password":
                                    connectionData.setPassword(nodeData);
                                    break;
                            }
                        }
                    }
                }
            }
        }
        catch(ParserConfigurationException | SAXException | IOException excep)
        {
            logger.log(Level.SEVERE, null, excep);
        }
        logger.log(Level.INFO, "File Parsed");
    }
}
