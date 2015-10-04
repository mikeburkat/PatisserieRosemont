package database;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

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
					System.out.println("Store Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					
					Customer c = new Customer(eElement);
					db.addCustomer(c);
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

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					System.out.println("Product Name : "
							+ eElement.getElementsByTagName("name").item(0)
									.getTextContent());
					
					Product p = new Product(eElement);
					db.addProduct(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
