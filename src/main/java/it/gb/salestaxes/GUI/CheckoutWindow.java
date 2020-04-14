package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Iterator;
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
	static Object objHome = new MainWindow();
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
					break;
				}
				i++;
			}
		}

		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();

		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel labelCheckout = new JLabel("Checkout");
		labelCheckout.setFont(UtilConstants.FONT_TITLE);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 0;
		panel.add(labelCheckout, gbc);

		JLabel labelSpace = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 100;
		panel.add(labelSpace, gbc);

		List<String> columns = settingHeaders();
		Object[][] data = settingData();

		tableModel = utilM.generateTypeTableModel(data, columns, UtilConstants.columnTypesCart, editableColumns);

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

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 0;
		gbc.weightx = 100;
		panel.add(new JScrollPane(table), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady = 0;
		gbc.weightx = 100;
		panel.add(table.getTableHeader(), gbc);

		JFrame frame = new JFrame("Checkout");

		JButton bPay = new JButton("Pay");
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.ipady = 20;
		gbc.weightx = 100;
		panel.add(bPay, gbc);

		bPay.addActionListener(
				listenerP.actionListener(utilM, "redirect", objPayWindow.getClass(), "receiptWindow", frame));
		
		JLabel labelSpace2 = new JLabel("");
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.ipady = 20;
		panel.add(labelSpace2, gbc);

		JButton bBack = new JButton("CANCEL");
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.ipady = 0;
		gbc.weightx = 100;
		panel.add(bBack, gbc);

		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", objHome.getClass(), "mainWindow", frame));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

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
		Object[][] data = new Object[gs.getProdidmapcounter().size()][UtilConstants.columnTypesCart.size()];

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
