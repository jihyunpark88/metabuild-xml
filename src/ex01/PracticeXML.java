package mesin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PracticeXML {
	
	//�ش� ������ ������ �Ľ��Ͽ� document�� return
	public Document fileParsing(String fileName) {
		//�ش� ������ ������
		File file = new File("C:\\Users\\meta\\Desktop\\1.XML ���� �м�/"+fileName);		
			//xmlFile�� �Ľ����ִ� ��ü�� ����
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			Document document = null;			
			try {
				documentBuilder = factory.newDocumentBuilder();
				//file parsing
				document = documentBuilder.parse(file);
				document.getDocumentElement().normalize();				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return document;
	}

	//�ش� document�� tagNames�� �´� nodeList�� return(XPath ���)
	public Map<String, NodeList> getNodeLists(Document document, String... tagNames){
		Map<String, NodeList> nodeListsMap = new HashMap<String, NodeList>();
		
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
        	for(int temp=0; temp<tagNames.length; temp++) {
        		NodeList nodeList = (NodeList) xpath.evaluate("TABLE/ROWS/ROW/"+tagNames[temp], document, XPathConstants.NODESET);
        		nodeListsMap.put(tagNames[temp], nodeList);
        	}
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return nodeListsMap;
	}
	
	/*//�ش� document�� tagNames�� �´� nodeList�� return(XPath �̻��)
	public Map<String, NodeList> getNodeLists(Document document, List<String> tagNames){		
		Map<String, NodeList> nodeListsMap = new HashMap<String, NodeList>();
		Element elemnet = document.getDocumentElement();
		for(int temp=0; temp<tagNames.size(); temp++) {
			NodeList nodeList = elemnet.getElementsByTagName(tagNames.get(temp));
			nodeListsMap.put(tagNames.get(temp), nodeList);
		}
		return nodeListsMap;
	}*/
	
	//�ش� targetNodeList���� searchkey�� ������ nodeValue�� ���� node�� �ٸ� tag(returnNodeList)�� node�� return  
	public Node searchNodeList(String searchKey, NodeList targetNodeList, NodeList returnNodeList) {
		Node node = null;		
		for(int temp=0; temp<targetNodeList.getLength(); temp++) {
			if(searchKey.equals(targetNodeList.item(temp).getTextContent())) {
				node = returnNodeList.item(temp);
			}
		}		
		return node;
	}
	
	//�ش� document�� XMLfile�� ���� 
	public void saveXMLFile(Document document, File file) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");			
			transformer.transform(new DOMSource(document), new StreamResult(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
