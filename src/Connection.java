

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Connection {

	public static void weather(float lat,float lon){
	// Declaring  globals	
		String hostname = "graphical.weather.gov";	
		URL obj = null;
		HttpURLConnection con = null;

		try {
			
			String text = new String(Files.readAllBytes(Paths.get("NDFDgen.xml")), StandardCharsets.UTF_8);
			
			text = text.replaceFirst("latitudeplaceholder", lat + "");
			text = text.replaceFirst("longitudeplaceholder", lon + "");
			
			obj = new URL("https://" + hostname + "/xml/SOAP_server/ndfdXMLserver.php");	//opening URL with to connect to weather server
			con = (HttpURLConnection) obj.openConnection();		//	establishing connecton 
			con.setRequestMethod("POST");	// Sending xml request with POST method
			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
			con.setRequestProperty("SOAPAction", "https://graphical.weather.gov/xml/DWMLgen/wsdl/ndfdXML.wsdl#NDFDgen");	
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setDoInput(true);	
			con.setDoOutput(true);
			OutputStream reqStream = new BufferedOutputStream(con.getOutputStream());
			reqStream.write(text.getBytes());
			reqStream.flush();
			reqStream.close();
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + hostname);
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // in case of success, writing xml response to NewFile.xml
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				// making xml file readable
				while ((inputLine = in.readLine()) != null) {
					inputLine = inputLine.replaceAll("&lt;", "<");		
					inputLine = inputLine.replaceAll("&gt;", ">");
					inputLine = inputLine.replaceAll("&quot;", "\"");
					inputLine = inputLine.replaceAll("&amp;amp;", "&");
					response.append(inputLine);
					response.append('\n');
				}
				response.replace(437, 458, "");
				response.replace(1690, 1765, "");
				FileWriter fw = new FileWriter("NewFile.xml", false);
				fw.write(response.toString());
				fw.close();
				in.close();
			}
			
			File fXmlFile = new File("NewFile.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fXmlFile);		// parsing the xml file
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());	
			NodeList nList = doc.getElementsByTagName("temperature");	
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
			    Node nNode = nList.item(temp);
			    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			    	Element eElement = (Element) nNode;			    
			    //Getting weather values from stored file and displaying them on text area	
			    	
			    	WeatherGUI.TA.append(eElement.getElementsByTagName("name").item(0).getTextContent() + ": " + eElement.getElementsByTagName("value").item(0).getTextContent());
			    	WeatherGUI.TA.append("\n");
			    }
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
