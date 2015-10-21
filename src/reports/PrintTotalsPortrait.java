package reports;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import database.DataBase;

public class PrintTotalsPortrait {

	String date = "";

	PrinterJob job;

	public PrintTotalsPortrait(String d) {
		date = d;

		System.out.println("PRITING TOTALS");

		job = PrinterJob.getPrinterJob();

		PageFormat pf = job.defaultPage();
		Paper paper = pf.getPaper();
		paper.setSize(8.5 * 72, 11 * 72);
		paper.setImageableArea(12.0, 12.0, 588.0, 768.0);
		pf.setPaper(paper);

		Book bk = new Book();
		bk.append(new PrintTotalsPortraitContent(date), pf);
		job.setPageable(bk);

		boolean doPrint = job.printDialog();
		if (doPrint) {
			try {
				job.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class PrintTotalsPortraitContent implements Printable {

	DataBase db;
	int pages;
	int[] pageBreaks;
	String date = "";
	File storeAndDateFile;
	int index;
	ArrayList<String[]> details;
	int lines;
	String bread = "'bread', 'pastry'";
	String cake = "'other', 'cake'";
	PrinterJob job;

	public PrintTotalsPortraitContent(String d) {
		date = d;
		db = DataBase.getInstance();
		ArrayList<String[]> detailsAll = db.getTotals(date,
				bread + ", " + cake, "'Montreal', 'Ottawa'");
		ArrayList<String[]> detailsMtl = db.getTotals(date,
				bread + ", " + cake, "'Montreal'");
		ArrayList<String[]> detailsOtt = db.getTotals(date,
				bread + ", " + cake, "'Ottawa'");
		
		HashMap<String, String[]> mtlDeets = new HashMap<String, String[]>();
		for (String[] mtl : detailsMtl) {
			mtlDeets.put(mtl[0], mtl);
		}
		HashMap<String, String[]> ottDeets = new HashMap<String, String[]>();
		for (String[] ott : detailsOtt) {
			ottDeets.put(ott[0], ott);
		}

		details = new ArrayList<String[]>();
		for (String[] detail : detailsAll) {
			String[] mtl = mtlDeets.get(detail[0]);
			String[] ott = ottDeets.get(detail[0]);
			String m = "";
			String o = "";
			if (mtl != null && mtl.length >= 2) {
				m = mtl[1];
			}
			if (ott != null && ott.length >= 2) {
				o = ott[1];
			}
			String[] arr = { detail[0], detail[1], o, m};
			details.add(arr);
		}

	}

	private void printGrid(Graphics g, PageFormat pf) {
		for (int x = 0; x < pf.getImageableWidth(); x += 10) {
			if (x % 100 == 0) {
				g.drawLine(x, 0, x, (int) pf.getImageableHeight());
			}
			for (int y = 0; y < pf.getImageableHeight(); y += 10) {
				if (y % 100 == 0) {
					g.drawLine(0, y, (int) pf.getImageableWidth(), y);
				}
				g.drawOval(x, y, 1, 1);
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex)
			throws PrinterException {
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		System.out.println("X: " + pf.getImageableX() + " Y: "
				+ pf.getImageableY() + " Width: " + pf.getImageableWidth()
				+ " Height: " + pf.getImageableHeight());

		//printGrid(g, pf);

		double w = pf.getImageableWidth();
		double h = pf.getImageableHeight();

		int xo = (int) pf.getImageableX();
		int yo = (int) pf.getImageableY();

		Rectangle2D r = new Rectangle2D.Double(0, 0, w, h);
		//g2d.draw(r);

		setNumberOfLines(pf);
		printContentBar(g, pf);
		printOrderDetails(g, pf);

		return PAGE_EXISTS;
	}

	public void setNumberOfLines(PageFormat pf) {

		int usable = (int) (pf.getImageableHeight());
		int fontSize = 15;
		lines = usable / fontSize;

		System.out.println("usable: " + usable + ", Total Lines: "
				+ details.size() + ", lines on page: " + lines);
	}

	private void printContentBar(Graphics g, PageFormat pf) {
		int x = 5;
		int y = 12;

		for (int i = 0; i < 2; i++) {
			g.drawString("Item", x, y);
			g.drawString("Total", x += 160, y);
			g.drawString("OTT", x += 50, y);
			g.drawString("MTL", x += 45, y);
			x = 300;
		}

		g.drawLine(0, 15, (int) pf.getImageableWidth(), 15);
	}

	private void printOrderDetails(Graphics g, PageFormat pf) {
		int y = 30;
		int index = 0;

		Iterator<String[]> it = null;
		if (index < details.size()) {
			it = details.listIterator(index);
		}
		int x = 6;
		int column = 0;
		while (it != null && it.hasNext()) {
			String[] product = it.next();
			System.out.println(index + " : " + y + " : " + product[1] + " : "
					+ product[0]);
			x = 6 + 300 * column;
			g.drawString(product[0], x, y);
			g.drawString(product[1], x += 160, y);
			g.drawString(product[2], x += 50, y);
			g.drawString(product[3], x += 50, y);
			g.drawLine(300*column - 3, y+3, 300*column + 300 - 3, y+3);
			y += 15;
			index++;
			if (y > pf.getImageableHeight()) {
				y = 30;
				column++;
				g.drawLine(300*column - 3, 0, 300*column - 3, (int) pf.getImageableHeight());
			}
		}
	}

}