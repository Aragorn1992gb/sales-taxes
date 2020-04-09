package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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

public class CartWindow {
	static GlobalStorage gs = GlobalStorage.getInstance();
	static ProductsDAOImpl productsDAOImpl = new ProductsDAOImpl();
	static ListenerProxies listenerP = new ListenerProxies();
	static UtilMethods utilM = new UtilMethods();
	static UtilConstants utilC = new UtilConstants();
	static Object obj = new MainWindow();
	static Object objPayWindow = new PayWindow();

	static ClassLoader classL = new CartWindow().getClass().getClassLoader();
	static ImageIcon icon = new ImageIcon(classL.getResource(utilC.REMOVE_ICO));

	public static void cartWindow() throws Exception {
		
		
		gs.setColumnElements(7);
		int cartColumn = 8;

		int[] editableColumns = { gs.getColumnElements(), cartColumn };
		

		for (Integer prodId : gs.getTocart())
			System.out.println(prodId);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel("Your cart"), BorderLayout.NORTH);

		List<String> columns = settingHeaders();

		Object[][] data = settingData();

		DefaultTableModel tableModel = utilM.generateTypeTableModel(data, columns, utilC.columnTypesCart,
				editableColumns);

		JTable table = new JTable(tableModel);
		
		//table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setPreferredScrollableViewportSize(new Dimension(1000, 1000));
	//	table.setSize(100, 100);
		
		table.setRowHeight(30);

		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);

		JPanel tablePanel = new JPanel();
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);

		JFrame frame = new JFrame("Your cart");

		JButton bCheckout = new JButton("Checkout");

		bCheckout.addActionListener(
				listenerP.actionListener(utilM, "redirect", objPayWindow.getClass(), "payWindow", frame));
		JPanel bCheckoutPanel = new JPanel();
		bCheckoutPanel.add(bCheckout);

		JButton bBack = new JButton("BACK");

		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", obj.getClass(), "mainWindow", frame));
		JPanel bPanel = new JPanel();
		bPanel.add(bBack);

		Action addToCartClick = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int modelRow = Integer.valueOf(e.getActionCommand());
				gs.addToCart(modelRow);
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, utilM.deleteItemClick, cartColumn);
		
		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int idProd = gs.getRowmapidprod().get(row);
				int units = gs.getProductslist().get(idProd-1).getUnits();
				int oldValue = gs.getProdidmapcounter().get(idProd);
				String currentValue = table.getValueAt(row, gs.getColumnElements()).toString();
				int intValue = Integer.valueOf(currentValue);
				
				if(intValue<=units) {
					gs.addToProdIdMapCounter(idProd, intValue);
				} else {
					tableModel.setValueAt(oldValue, row, e.getColumn());
					JOptionPane.showMessageDialog(null, "Units chosen are unavailable for this item");
				}
				
			}
		    });
		
		buttonColumn.setMnemonic(KeyEvent.VK_D);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);
		containerPane.add(tablePanel, BorderLayout.CENTER);
		containerPane.add(bCheckoutPanel, BorderLayout.WEST);
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
		Object[][] data = new Object[gs.getProdidmapcounter().size()][utilC.columnTypesCart.size() + utilC.OTHER_COLUMNS_CART];
		//HashMap<Integer, Integer> procutsMap = groupProducts();
		
	//	int a = gs.getProdidmapcounter().get(1);

		int i = 0, j=0;

		for (ProductsBean productRow : gs.getProductslist()) {
			
			if (gs.getProdidmapcounter().containsKey(productRow.getIdProd()) && gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i)) != 0) {
				data[j][0] = productRow.getName();
				data[j][1] = productRow.getBrands();
				data[j][2] = productRow.getGrams();
				data[j][3] = productRow.getPrice();
				data[j][4] = productRow.getCurrency();
				data[j][5] = productRow.getDescription();
				data[j][6] = productRow.getIngredients();
				data[j][7] = gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i));
				data[j][8] = icon;

				gs.addToRowMapIdProd(j, productRow.getIdProd());
				j++;
				
			}
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
		columns.add(productHeaders.get(6));
		columns.add(productHeaders.get(7));
		columns.add("Elements");
		columns.add("Remove all");

		return columns;
	}

//	private static HashMap<Integer, Integer> groupProducts() {
//		HashMap<Integer, Integer> counterProdMap = new HashMap<Integer, Integer>();
//
//		for (Integer i : gs.getTocart()) {
//			Integer j = counterProdMap.get(i);
//			counterProdMap.put(i, (j == null) ? 1 : j + 1);
//		}
//
//		return counterProdMap;
//	}
}
