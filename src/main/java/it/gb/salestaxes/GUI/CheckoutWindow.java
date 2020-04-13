package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	static Object objPayWindow = new ReceiptWindow();

	static ClassLoader classL = new CartWindow().getClass().getClassLoader();

	static DefaultTableModel tableModel = null;

	public static void checkoutWindow() {
		int[] editableColumns = {};
		gs.generateProductsCheckoutist();
		ArrayList<ProductsBean> productsList = gs.getProductslist();

		for (ProductsBean checkoutProd : gs.getProductscheckoutlist()) {
			int idProd = checkoutProd.getIdProd();
			Integer counterItems = gs.getProdidmapcounter().get(idProd);
			int i = 0;
			for (ProductsBean prod : productsList) {
				if (prod.getIdProd() == idProd) {
					gs.updateUnits(i, prod.getUnits() - counterItems);
					// productsList.get(i).setUnits(prod.getUnits() - counterItems);
					break;
				}
				i++;
			}
		}

		// System.out.println("ciao");

//		ArrayList<ProductsBean> sadsad = gs.getProductscheckoutlist();

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Checkout"), BorderLayout.NORTH);

//		ArrayList<ProductsBean> a = gs.getProductslist();
		List<String> columns = settingHeaders();
		Object[][] data = settingData();

		tableModel = utilM.generateTypeTableModel(data, columns, utilC.columnTypesCart, editableColumns);

		JTable table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
		table.setRowHeight(30);

		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);

		gs.addLine("#### Purchase");
		gs.addLine("#### Basket");
		gs.addLine("'''");

		Iterator itProductsNewList = gs.getProductscheckoutlist().iterator();
		while (itProductsNewList.hasNext()) {
			ProductsBean pb = (ProductsBean) itProductsNewList.next();
			gs.addLine(gs.getProdidmapcounter().get(pb.getIdProd()) + " " + pb.getName() + " at " + pb.getPrice() + " "
					+ pb.getCurrency() + " each");
		}
		gs.addLine("'''");
		gs.addLine("##### Receipt");
		gs.addLine("");

		double salesTaxes = 0;
		Iterator itProductsNewList2 = gs.getProductscheckoutlist().iterator();
		while (itProductsNewList2.hasNext()) {
			ProductsBean pb = (ProductsBean) itProductsNewList2.next();
			double taxedPrice = productsDAOImpl.getTaxedPrice(pb);
			salesTaxes = salesTaxes + (taxedPrice - pb.getPrice());

			gs.addLine(gs.getProdidmapcounter().get(pb.getIdProd()) + " " + pb.getName() + " at " + taxedPrice + " "
					+ pb.getCurrency() + " each");
		}
		gs.addLine("Sales Taxes: " + salesTaxes);
		gs.addLine("Total: " + String.valueOf(productsDAOImpl.getTotalCalculation(gs.getProductscheckoutlist())));
		gs.addLine("'''");

		JLabel totalLabel = new JLabel();
//		
		String totalLabelText = "TOTAL: " + productsDAOImpl.getTotalCalculation(gs.getProductscheckoutlist());
		totalLabel.setText(totalLabelText);

		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

		JFrame frame = new JFrame("Checkout");

		JButton bPay = new JButton("Pay");

		bPay.addActionListener(
				listenerP.actionListener(utilM, "redirect", objPayWindow.getClass(), "receiptWindow", frame));
		JPanel lTotalPanel = new JPanel();
		panel.add(bPay, BorderLayout.WEST);
		lTotalPanel.add(totalLabel, BorderLayout.SOUTH);

		JButton bBack = new JButton("BACK");

		bBack.addActionListener(
				listenerP.actionListener(utilM, "redirect", objMainWindow.getClass(), "mainWindow", frame));
		JPanel bPanel = new JPanel();
		bPanel.add(bBack);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);
		containerPane.add(tablePanel, BorderLayout.CENTER);
		containerPane.add(bPanel, BorderLayout.SOUTH);
		containerPane.add(lTotalPanel, BorderLayout.WEST);

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

		for (ProductsBean productRow : gs.getProductscheckoutlist()) {

			if (gs.getProdidmapcounter().containsKey(productRow.getIdProd())
					&& gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i)) != 0) {
				data[j][0] = productRow.getName();
				data[j][1] = productsDAOImpl.getTaxedPrice(productRow);
				data[j][2] = productRow.getCurrency();
				data[j][3] = productRow.getCountryProd();
				data[j][4] = "x " + gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i));

				gs.addToRowMapIdProd(j, productRow.getIdProd());
				j++;

			}
			i++;

		}
		return data;
	}

}
