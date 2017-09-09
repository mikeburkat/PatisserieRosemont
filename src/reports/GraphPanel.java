package reports;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.util.Insets2D;
import de.erichseifert.gral.util.Location;

public class GraphPanel extends JPanel {

	String date;
	Color COLOR1 = Color.BLUE;

	public GraphPanel() {
		setLayout(new BorderLayout());
		// Create example data
		DataTable data = new DataTable(Integer.class, Integer.class,
				String.class);
		data.add(1, 1000, "January");
		data.add(2, 1500, "February");
		data.add(3, 1600, "March");
		data.add(4, 1300, "April");
		data.add(5, 1300, "May");
		data.add(6, 1200, "June");
		data.add(7, 1000, "July");
		data.add(8, 700, "August");

		// Create new bar plot
		BarPlot plot = new BarPlot(data);

		// Format plot
		plot.setInsets(new Insets2D.Double(40.0, 40.0, 40.0, 40.0));
		plot.setBarWidth(0.8);

		// Format bars
		BarRenderer pointRenderer = (BarRenderer) plot.getPointRenderer(data);
		pointRenderer.setColor(new LinearGradientPaint(0f, 0f, 0f, 1f,
				new float[] { 0.0f, 1.0f }, new Color[] { COLOR1,
						GraphicsUtils.deriveBrighter(COLOR1) }));
		pointRenderer.setBorderStroke(new BasicStroke(3f));
		pointRenderer.setBorderColor(new LinearGradientPaint(0f, 0f, 0f, 1f,
				new float[] { 0.0f, 1.0f }, new Color[] {
						GraphicsUtils.deriveBrighter(COLOR1), COLOR1 }));
		pointRenderer.setValueVisible(true);
		pointRenderer.setValueColumn(2);
		pointRenderer.setValueLocation(Location.CENTER);
		pointRenderer.setValueColor(GraphicsUtils.deriveDarker(COLOR1));
		pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));

		add(new InteractivePanel(plot), BorderLayout.CENTER);

	}

	public void setDate(String newDate) {
		date = newDate;
	}

}
