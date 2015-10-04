package print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import database.DataBase;

public class PrintOrder implements Printable {

	DataBase db;
	int pages;
	int[] pageBreaks;
	String date = "";
	String store = "";
	File storeAndDateFile;
	int index;
	ArrayList<String[]> details;
	int lines;
	String total;

	public PrintOrder(String s, String d) {
		store = s;
		date = d;
		db = DataBase.getInstance();
		details = db.getOrderDetailsForPrinting(store, date, "pid");
		total = db.getOrderTotal(store, date);
	}

	public void setNumberOfPages(PageFormat pf) {
		
		int usable = (int) (pf.getImageableHeight() - 180 - 40);
		int fontSize = 15;
		lines = usable / fontSize;
		int fullPages = details.size() / lines;
		int halfPages = details.size() % lines;
		
		System.out.println("usable: " + usable + " lines: " + lines + " fullPages: " + fullPages + " halfPages: " + halfPages);
		
		if (halfPages != 0) {
			fullPages++;
		}
		pages = fullPages - 1;
		
		
	}

	private void printGrid(Graphics g, PageFormat pf) {
		for (int x = 0; x < pf.getImageableWidth(); x += 10) {
			if (x % 100 == 0) {
				g.drawLine(x, 0, x, 767);
			}
			for (int y = 0; y < pf.getImageableHeight(); y += 10) {
				if (y % 100 == 0) {
					g.drawLine(0, y, 587, y);
				}
				g.drawOval(x, y, 1, 1);
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		
		setNumberOfPages(pf);

		// We have only one page, and 'page' is zero-based
		if (page > pages) {
			return NO_SUCH_PAGE;
		}

		// User (0,0) is typically outside the imageable area, so we must
		// translate
		// by the X and Y values in the PageFormat to avoid clipping.
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		System.out.println("X: " + pf.getImageableX() + " Y: "
				+ pf.getImageableY() + " Width: " + pf.getImageableWidth()
				+ " Height: " + pf.getImageableHeight());

		// printGrid(g, pf);

		printLogo(g);
		printOrderNumberAndDate(g);
		printClientInfo(g);
		printContentBar(g);
		printPageNumber(g, page);

		printOrderDetails(g, pf, page);

		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;

	}

	private void printTotal(Graphics g, int y) {
		g.drawLine(80, y, 500, y);
		y += 20;
		g.drawString("Total:", 390, y);
		g.drawString(total, 440, y);
	}

	private void printLogo(Graphics g) {
		int x = 40;
		int y = 40;
		g.drawString("Patisserie Rosemont", x, y);
		g.drawString("2894 Boul. Rosemont", x, y += 15);
		g.drawString("Montreal, Qc, H1Y 1L7", x, y += 15);
		g.drawString("Tel: (514) 728-7711", x, y += 15);
	}

	private void printClientInfo(Graphics g) {
		ResultSet customer = db.getCustomerDetails(store);

		int x = 350;
		int y = 55;

		g.drawString("Client:", x, y);
		x += 50;
		try {
			g.drawString(customer.getString("full_name"), x, y);
			g.drawString(customer.getString("address"), x, y += 15);
			g.drawString(customer.getString("postal_code"), x, y += 15);
			g.drawString(customer.getString("phone_num"), x, y += 15);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void printOrderNumberAndDate(Graphics g) {
		String oid = db.getOrderID(store, date);
		int x = 350;
		int y = 20;
		g.drawString("Order Number: " + oid, x, y);
		g.drawString("Order Date: " + date, x, y += 15);
	}

	private void printContentBar(Graphics g) {
		int x = 80;
		int y = 150;
		g.drawString("Quantity", x, y);
		g.drawString("Item", x += 70, y);
		g.drawString("Price", x += 240, y);
		g.drawString("Total", x += 70, y);
		y += 10;
		g.drawLine(80, y, 500, y);
	}

	private void printPageNumber(Graphics g, int page) {
		g.drawString("Page " + (page + 1) + " of " + (pages + 1), 510, 760);
	}

	private void printOrderDetails(Graphics g, PageFormat pf, int page) {
		int y = 180;
		int index = page * lines;
		int lastLine = index + lines;
		Iterator<String[]> it = null;
		if (index < details.size()) {
			it = details.listIterator(index);
		}
		
		System.out.println(page + " : " + lastLine);
		
		while (it != null && it.hasNext() && index < lastLine) {
			String[] product = it.next();
			System.out.println(index + " : " + y + " : " + product[1]);
			int x = 80;
			g.drawString(product[0], x, y);
			g.drawString(product[1], x += 70, y);
			g.drawString(product[2], x += 240, y);
			g.drawString(product[3], x += 70, y);
			y += 15;
			index++;
		}
		
		if (page == pages) {
			printTotal(g, y);
		}
		
	}

}
