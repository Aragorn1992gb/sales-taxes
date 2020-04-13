package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

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
	static UtilConstants utilC = new UtilConstants();
	static Object obj = new MainWindow();
	static Object objCartWindow = new CartWindow();
	//static ArrayList<Integer> toCart = new ArrayList<>();
	//static ArrayList<ProductsBean> productsList = new ArrayList();
	static ClassLoader classL = new SecondWindow().getClass().getClassLoader();
	static ImageIcon icon = new ImageIcon(classL.getResource(utilC.CART_ICO));
	
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();

	
	//static GlobalStorage gs = new GlobalStorage();
	
	static GlobalStorage gs = GlobalStorage.getInstance();


	// Window that shows list of products
	public static void productsWindow() throws Exception {
	//	GlobalStorage gs = new GlobalStorage();
	//	gs = new GlobalStorage();
		gs.initializeProductList();
		
		int[] editableColumns = { 5 };
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Products catalog"), BorderLayout.NORTH);

		// ProductsDAOImpl contains method that acts on product data stored on a csv
		// file
		// ProductBean contains the characteristics of each rows in the csv file that
		// can be set and get
		// ArrayList<ProductsBean> productsList = new ArrayList();

		// findProductsData() is the method that read data from products file and return
		// a data list

		List<String> columns = settingHeaders();

		Object[][] data = settingData();

		DefaultTableModel tableModel = utilM.generateTypeTableModel(data, columns, utilC.columnTypes, editableColumns);

		JTable table = new JTable(tableModel);

		table.setPreferredScrollableViewportSize(table.getPreferredSize());

		table.setRowHeight(30);
		
		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);

		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

		panel.add(new JLabel(
				"Select quantity and click to add to cart on the items that you want. Click on the item to see the details"),
				BorderLayout.SOUTH);
		JFrame frame = new JFrame("Product Catalog");
		
		JButton bCart = new JButton("Go to your cart");

		bCart.addActionListener(listenerP.actionListener(utilM, "redirect", objCartWindow.getClass(), "cartWindow", frame));
		JPanel bCartPanel = new JPanel();
		bCartPanel.add(bCart);

		JButton bBack = new JButton("BACK");

		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", obj.getClass(), "mainWindow", frame));
		JPanel bPanel = new JPanel();
		bPanel.add(bBack);
		



		ButtonColumn buttonColumn = new ButtonColumn(table, utilM.addToCartClick, 5);
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);
		containerPane.add(tablePanel, BorderLayout.CENTER);
		containerPane.add(bCartPanel, BorderLayout.WEST);
		containerPane.add(bPanel, BorderLayout.SOUTH);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		// frame.setPreferredSize(new Dimension(400, 300));
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
		Object[][] data = new Object[gs.getProductslist().size()][utilC.columnTypes.size() + utilC.OTHER_COLUMNS];

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

//	private static Action addToCartClick = new AbstractAction() {
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			int modelRow = Integer.valueOf(e.getActionCommand());
//
//			HashMap<Integer,Integer> hm = new HashMap<Integer, Integer>();
//			int idProd = gs.getRowmapidprod().get(modelRow);
//			int counter = gs.getProdidmapcounter().get(idProd) == null ? 1 : gs.getProdidmapcounter().get(idProd)+1;
//			gs.addToProdIdMapCounter(idProd, counter);
//
//		}
//	};
}
