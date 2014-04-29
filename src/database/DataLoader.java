package database;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Connection;

public class DataLoader {
	private DataBase db;
	
	public DataLoader(DataBase database) {
		db = database;
	}

	public void importStores() {

		try {

			String rosemontDir = System.getProperty("user.home")
					+ "/Patisserie Rosemont/";
			File fXmlFile = new File(rosemontDir + "stores.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("store");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					System.out.println("First Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					db.addCustomer(eElement.getElementsByTagName("name").item(0).getTextContent(),
							eElement.getElementsByTagName("city").item(0).getTextContent(),
							eElement.getElementsByTagName("address").item(0).getTextContent(),
							"",
							eElement.getElementsByTagName("phone").item(0).getTextContent()
							);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void importProducts() {

		try {
			String rosemontDir = System.getProperty("user.home")
					+ "/Patisserie Rosemont/";
			File fXmlFile = new File(rosemontDir + "products.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("product");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					System.out.println("First Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					
					db.addProduct("Misc", eElement.getElementsByTagName("name").item(0).getTextContent(), 
							Double.parseDouble( eElement.getElementsByTagName("montrealPrice").item(0).getTextContent() )
							, "2014-04-28");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
