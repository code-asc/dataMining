package tools;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import beans.JiraData;


public class JiraCall {
	
	private static JiraCall jira = null;
	
	private JiraCall() {}
	
	public static JiraCall getInstance() {
		if(jira == null)
			jira = new JiraCall();
		
		return jira;
	}
	/**
	 * @param xmlData This is XML data in the form of String.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public JiraData readWebXML(String link) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
	
		Document doc = parser.parse(link);
		
		doc.getDocumentElement().normalize();
		NodeList nodeListAll = doc.getDocumentElement().getChildNodes();
		
		NodeList nodeListInner = nodeListAll.item(1).getChildNodes();
		NodeList nodeListItem = null;
		
		for(int i = 0; i < nodeListInner.getLength(); i++) {
			if(nodeListInner.item(i).getNodeName().
					toString().equals("item")) {
				nodeListItem = (NodeList) nodeListInner.item(i).getChildNodes();
				break;
			}
		}
//		System.out.println(nodeListItem.item(0).getChildNodes().item(0) == null);
//		System.out.println(nodeListItem.item(1).getChildNodes().item(0).getNodeValue());
//		System.out.println(nodeListItem.item(0).getNodeName());
//		System.out.println(nodeListItem.item(1).getNodeName());
//		
		if(nodeListItem != null) {
			JiraData data = new JiraData();
			
			for(int i =0; i < nodeListItem.getLength(); i++) {
				
					if(nodeListItem.item(i).getNodeName().
							toString().equals("title"))
						data.setTitle(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("link"))
						data.setLink(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("project"))
						data.setProjectID(nodeListItem.item(i).
								getAttributes().
								getNamedItem("id").getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("key"))
						data.setTicketNumber(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("resolution"))
						data.setResolution(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("created"))
						data.setCreatedDate(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
				
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("resolved"))
						data.setResolvedDate(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
				
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("version"))
						data.setVersion(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
				
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("fixVersion"))
						data.setFixVersion(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("assignee"))
						data.setAssignee(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("reporter"))
						data.setReporter(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("type"))
						data.setType(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
					else if(nodeListItem.item(i).getNodeName().
							toString().equals("priority"))
						data.setPriority(nodeListItem.item(i).
								getChildNodes().item(0).
								getNodeValue());
					
				
			}
			
			return data;
		}
		
		return null;
		
	}
	
	/**
	 * This method makes a web call to the specified JIRA URL and 
	 * return the XML data.
	 * @param link This is the URL of the XML
	 * @return String
	 * @throws IOException
	 */
	public String getRestData(String link) throws IOException {
		URL url = new URL(link);
		URLConnection con = url.openConnection();
		InputStream stream = con.getInputStream();
		StringWriter writer = new StringWriter();
		
		IOUtils.copy(stream, writer, "UTF-8");
		
		return ignoreComments(writer.toString());
	}
	
	
	/**
	 * This method is used to remove the comments at the beginning
	 * of the XML file.
	 * @param xmlData The XML that needs to be parsed
	 * @return String with removed comments at the beginning of it.
	 */
	private static String ignoreComments(String xmlData) {
		String data = xmlData;
		data = data.replaceAll("(?s)<!--.*?-->", "");
		return data;
	}
	
//	public static void main(String[] args) throws MalformedURLException, ParserConfigurationException, SAXException {
//		try {
//			String link = "https://issues.apache.org/jira/si/jira.issueviews:issue-xml/HADOOP-15576/HADOOP-15576.xml";
//			
//			JiraCall call = new JiraCall();
//			//System.out.println(call.getRestData(link));
//			JiraData data = call.readWebXML(link);
//			System.out.println(data.getProjectID());
//			System.out.println(data.getAssignee());
//			System.out.println(data.getReporter());
//			System.out.println(data.getCreatedDate());
//			System.out.println(data.getLink());
//			System.out.println(data.getResolvedDate());
//			System.out.println(data.getResolution());
//			System.out.println(data.getFixVersion());
//			System.out.println(data.getTicketNumber());
//			System.out.println(data.getTitle());
//			System.out.println(data.getVersion());
//			System.out.println(data.getType());
//			System.out.println(data.getPriority());
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//	}
}
