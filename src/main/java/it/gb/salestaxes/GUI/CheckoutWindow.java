package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.ListenerProxies;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

public class CheckoutWindow {
	static GlobalStorage gs = GlobalStorage.getInstance();
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();
	static ListenerProxies listenerP = new ListenerProxies();
	static UtilMethods utilM = new UtilMethods();
	static UtilConstants utilC = new UtilConstants();
	static Object objMainWindow = new MainWindow();
	static Object objPayWindow = new MainWindow();
	
	static ClassLoader classL = new CartWindow().getClass().getClassLoader();
	
	static DefaultTableModel tableModel = null;
	
	public static void checkoutWindow() {
		int[] editableColumns = { };

		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Checkout"), BorderLayout.NORTH);
		
		ArrayList<ProductsBean> a = gs.getProductslist();
		List<String> columns = settingHeaders();
		Object[][] data = settingData();
		
		tableModel = utilM.generateTypeTableModel(data, columns, utilC.columnTypesCart,
				editableColumns);
		
		JTable table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
		table.setRowHeight(30);
		
		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);
		
		JLabel totalLabel = new JLabel();
		

		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

		JFrame frame = new JFrame("Checkout");

		JButton bPay = new JButton("Pay");
		
		bPay.addActionListener(
				listenerP.actionListener(utilM, "redirect", objPayWindow.getClass(), "checkoutWindow", frame));
		JPanel bPayPanel = new JPanel();
		panel.add(bPay, BorderLayout.WEST);
		
		JButton bBack = new JButton("BACK");
		
		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", objMainWindow.getClass(), "mainWindow", frame));
		JPanel bPanel = new JPanel();
		bPanel.add(bBack);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);
		containerPane.add(tablePanel, BorderLayout.CENTER);
		containerPane.add(bPanel, BorderLayout.SOUTH);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		// frame.setPreferredSize(new Dimension(400, 300));
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private static void setColumnModel(TableColumnModel columnModel) {

	}

	
	private static List<String> settingHeaders() {
		CSVRecord productHeaders = productsDAOImpl.findProductsHeaders();

		List<String> columns = new ArrayList<String>();

		columns.add(productHeaders.get(1));
		columns.add(productHeaders.get(2));
		columns.add(productHeaders.get(3));
		columns.add(productHeaders.get(11));
		columns.add("Elements");

		return columns;
	}
	
	private static Object[][] settingData() {
		Object[][] data = new Object[gs.getProdidmapcounter().size()][utilC.columnTypesCart.size()];

		int i = 0, j = 0;

		for (ProductsBean productRow : gs.getProductslist()) {

			if (gs.getProdidmapcounter().containsKey(productRow.getIdProd())
					&& gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i)) != 0) {
				data[j][0] = productRow.getName();
				data[j][1] = productRow.getPrice();
				data[j][2] = productRow.getCurrency();
				data[j][3] = productRow.getCountryProd();
				data[j][4] = "x "+gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i));

				gs.addToRowMapIdProd(j, productRow.getIdProd());
				j++;

			}
			i++;

		}
		return data;
	}
	
}
