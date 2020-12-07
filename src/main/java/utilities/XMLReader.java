package utilities;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;

public class XMLReader {

	String path = "";

	public XMLReader(String xmlPath) {
		path = xmlPath;
	}

	public XMLReader() {
		path = "configFile.xml";
	}

	/** Read Tag Value according to the given Tag Name */

	public String readTagVal(String tagName) {
		String tagVal = null;
		File file = new File(path);

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			tagVal = doc.getElementsByTagName(tagName).item(0).getTextContent();

		} catch (Exception e) {
			System.out.println("Could not get data from config.XML file for the tag " + tagName);
			e.printStackTrace();
		}
     
		return tagVal;
	}

	public String[] readTagValues(String tagName) {
		String tagVal[] = null;
		File file = new File(path);
		int tagSize = 0;

		try {

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			tagSize = doc.getElementsByTagName(tagName).getLength();
			tagVal = new String[tagSize];

			for (int item = 0; item < tagSize; item++) {
				tagVal[item] = doc.getElementsByTagName(tagName).item(item).getTextContent();
			}

		} catch (Exception e) {
			System.out.println("Could not get data from config.XML file for the tag " + tagName);
			e.printStackTrace();
		}

		return tagVal;
	}

}
