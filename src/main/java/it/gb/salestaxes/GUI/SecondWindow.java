package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.apache.commons.csv.CSVRecord;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.daoImpl.ProductsDAOImpl;
import it.gb.salestaxes.util.ButtonColumn;
import it.gb.salestaxes.util.GlobalStorage;
import it.gb.salestaxes.util.ListenerProxies;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

public class SecondWindow extends JFrame {
	static ListenerProxies listenerP = new ListenerProxies();
	static UtilMethods utilM = new UtilMethods();
	static Object obj = new MainWindow();
	static Object objCartWindow = new CartWindow();

	static ClassLoader classL = new SecondWindow().getClass().getClassLoader();
	static ImageIcon icon = new ImageIcon(classL.getResource(UtilConstants.CART_ICO));

	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();

	static GlobalStorage gs = GlobalStorage.getInstance();

	static JButton bCart = new JButton("Go to your cart");

	// Window that shows list of products
	public static void productsWindow() throws Exception {
		gs.initializeProductList();

		int[] editableColumns = { 5 };

		bCart.setEnabled(false);

		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();

		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel labelCatalog = new JLabel("Products catalog");
		labelCatalog.setFont(UtilConstants.FONT_TITLE);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 0;
		panel.add(labelCatalog, gbc);

		JLabel labelSpace = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 100;
		panel.add(labelSpace, gbc);

		JLabel labelDescription = new JLabel("Click the relative button to add to cart on the items that you want");
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 3;
		panel.add(labelDescription, gbc);

		List<String> columns = settingHeaders();

		Object[][] data = settingData();

		DefaultTableModel tableModel = UtilMethods.generateTypeTableModel(data, columns, UtilConstants.columnTypes,
				editableColumns);

		JTable table = new JTable(tableModel);

		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		table.setRowHeight(30);

		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady = 0;
		panel.add(new JScrollPane(table), gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipady = 0;
		panel.add(table.getTableHeader(), gbc);

		JLabel labelSpace2 = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.ipady = 100;
		panel.add(labelSpace2, gbc);

		JFrame frame = new JFrame("Product Catalog");

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.ipady = 0;
		panel.add(bCart, gbc);

		bCart.addActionListener(listenerP.actionListener(utilM, "redirect", objCartWindow.getClass(), "cartWindow", frame));

		JButton bBack = new JButton("BACK");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.ipady = 0;
		panel.add(bBack, gbc);

		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", obj.getClass(), "mainWindow", frame));

		ButtonColumn buttonColumn = new ButtonColumn(table, addToCartClick, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		frame.pack();
		frame.setVisible(true);

	}

	private static void setColumnModel(TableColumnModel columnModel) {
		columnModel.getColumn(0).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(50);
		columnModel.getColumn(4).setPreferredWidth(70);
		columnModel.getColumn(5).setPreferredWidth(100);
	}

	private static Object[][] settingData() {
		Object[][] data = new Object[gs.getProductslist().size()][UtilConstants.columnTypes.size()
				+ UtilConstants.OTHER_COLUMNS];

		int i = 0;

		for (ProductsBean productRow : gs.getProductslist()) {

			data[i][0] = productRow.getName();
			data[i][1] = productRow.getBrands();
			data[i][2] = productRow.getGrams();
			data[i][3] = productsDAOImpl.getTaxedPrice(productRow);
			data[i][4] = productRow.getCurrency();
			data[i][5] = icon;

			gs.addToRowMapIdProd(i, productRow.getIdProd());

			i++;
		}
		return data;
	}

	private static List<String> settingHeaders() {
		CSVRecord productHeaders = productsDAOImpl.findProductsHeaders();

		List<String> columns = new ArrayList<String>();

		columns.add(productHeaders.get(1));
		columns.add(productHeaders.get(8));
		columns.add(productHeaders.get(10));
		columns.add(productHeaders.get(2));
		columns.add(productHeaders.get(3));
		columns.add("Add To Cart");

		return columns;
	}

	public static Action addToCartClick = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int modelRow = Integer.valueOf(e.getActionCommand());
			bCart.setEnabled(true);
			int idProd = gs.getRowmapidprod().get(modelRow);
			int counter = gs.getProdidmapcounter().get(idProd) == null ? 1 : gs.getProdidmapcounter().get(idProd) + 1;

			int units = gs.getProductslist().get(idProd - 1).getUnits();
			if (counter <= units) {
				gs.addToProdIdMapCounter(idProd, counter);
			} else {
				JOptionPane.showMessageDialog(null, "No other available units for chosen item");
			}

		}
	};

}
