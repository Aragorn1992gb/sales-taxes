package it.gb.salestaxes.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
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
	static Object objBack = new SecondWindow();
	static Object objCheckoutWindow = new CheckoutWindow();

	static ClassLoader classL = new CartWindow().getClass().getClassLoader();
	static ImageIcon iconDelete = new ImageIcon(classL.getResource(UtilConstants.REMOVE_ICO));
	static ImageIcon iconDescription = new ImageIcon(classL.getResource(UtilConstants.DESCRIPTION_ICO));

	static JLabel descriptionProdLabel = new JLabel();
	static DefaultTableModel tableModel = null;
	static JButton bCheckout = new JButton("Checkout");

	public static void cartWindow() throws Exception {

		bCheckout.setEnabled(true);

		gs.setColumnElements(5);
		int cartColumn = 8;
		int descriptionColumn = 9;

		int[] editableColumns = { gs.getColumnElements(), cartColumn, descriptionColumn };

		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();

		panel.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel labelCart = new JLabel("Your cart");
		labelCart.setFont(UtilConstants.FONT_TITLE);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 0;
		panel.add(labelCart, gbc);

		JLabel labelSpace = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.ipady = 100;
		panel.add(labelSpace, gbc);

		List<String> columns = settingHeaders();

		Object[][] data = settingData();

		tableModel = UtilMethods.generateTypeTableModel(data, columns, UtilConstants.columnTypesCart, editableColumns);

		JTable table = new JTable(tableModel);

		table.setPreferredScrollableViewportSize(new Dimension(500, 500));

		table.setRowHeight(30);

		TableColumnModel columnModel = table.getColumnModel();
		setColumnModel(columnModel);

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

		descriptionProdLabel.setPreferredSize(new Dimension(300, 500));
		descriptionProdLabel.setMaximumSize(new Dimension(300, 500));
		descriptionProdLabel.setFont(UtilConstants.FONT_DESCRIPTION);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weighty = 5;
		gbc.weightx = 100;
		gbc.ipady = 0;
		panel.add(descriptionProdLabel, gbc);

		JFrame frame = new JFrame("Your cart");

		JLabel labelSpace2 = new JLabel("");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.ipady = 100;
		panel.add(labelSpace2, gbc);

		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.ipady = 0;
		gbc.weightx = 100;
		panel.add(bCheckout, gbc);

		bCheckout.addActionListener(listenerP.actionListener(utilM, "redirect", objCheckoutWindow.getClass(), "checkoutWindow", frame));

		JButton bBack = new JButton("BACK");
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.ipady = 0;
		gbc.weightx = 100;
		panel.add(bBack, gbc);

		bBack.addActionListener(listenerP.actionListener(utilM, "redirect", objBack.getClass(), "productsWindow", frame));

		table.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				try {
					int row = e.getFirstRow();
					int idProd = gs.getRowmapidprod().get(row);
					if (gs.containsKeyProdIdMapCounter(idProd)) {
						int units = gs.getProductslist().get(idProd - 1).getUnits();
						int oldValue = gs.getProdidmapcounter().get(idProd);
						String currentValue = table.getValueAt(row, gs.getColumnElements()).toString();
						int intValue = Integer.valueOf(currentValue);

						if (intValue <= units) {
							gs.addToProdIdMapCounter(idProd, intValue);
						} else {
							tableModel.setValueAt(oldValue, row, e.getColumn());
							JOptionPane.showMessageDialog(null, "Units chosen are unavailable for this item");
						}

					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		ButtonColumn buttonColumnDescription = new ButtonColumn(table, showDescription, descriptionColumn);

		ButtonColumn buttonColumn = new ButtonColumn(table, deleteItemClick, cartColumn);

		HashMap<Integer, Integer> asda = gs.getRowmapidprod();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = frame.getContentPane();
		containerPane.add(panel, BorderLayout.NORTH);

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
		Object[][] data = new Object[gs.getProdidmapcounter().size()][UtilConstants.columnTypesCart.size()
				+ UtilConstants.OTHER_COLUMNS_CART];

		int i = 0, j = 0;

		for (ProductsBean productRow : gs.getProductslist()) {

			if (gs.getProdidmapcounter().containsKey(productRow.getIdProd())
					&& gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i)) != 0) {
				data[j][0] = productRow.getName();
				data[j][1] = productRow.getBrands();
				data[j][2] = productRow.getIngredients();
				data[j][3] = productRow.getGrams();
				data[j][4] = productRow.getDescription();
				data[j][5] = gs.getProdidmapcounter().get(gs.getRowmapidprod().get(i));
				data[j][6] = productsDAOImpl.getTaxedPrice(productRow);
				data[j][7] = productRow.getCurrency();
				data[j][8] = iconDelete;
				data[j][9] = iconDescription;

				gs.addToRowMapIdProd(j, productRow.getIdProd());
				j++;

			}
			i++;

		}
		for (; j < gs.getProductslist().size(); j++) {
			gs.removeFromRowMapIdprod(j);
		}

		return data;
	}

	private static List<String> settingHeaders() {
		CSVRecord productHeaders = productsDAOImpl.findProductsHeaders();

		List<String> columns = new ArrayList<String>();

		columns.add(productHeaders.get(1));
		columns.add(productHeaders.get(8));
		columns.add(productHeaders.get(7));
		columns.add(productHeaders.get(10));
		columns.add(productHeaders.get(6));
		columns.add("Elements");
		columns.add(productHeaders.get(2));
		columns.add(productHeaders.get(3));
		columns.add("Remove all");
		columns.add("Description");

		return columns;
	}

	private static Action showDescription = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTable table = (JTable) e.getSource();

			int modelRow = Integer.valueOf(e.getActionCommand());

			int idProd = gs.getRowmapidprod().get(modelRow);
			ProductsBean product = gs.getProductById(idProd);

			String colorRed = "<font color='red'>";
			String colorClosed = "</font>";
			String ingredients = (product.getIngredients() == null || product.getIngredients().equals("")) ? ""
					: "<br/>" + colorRed + "Ingredients: " + colorClosed + product.getIngredients();
			String grams = (product.getGrams() == null || product.getGrams().equals("")) ? ""
					: "<br/>" + colorRed + "Grams: " + colorClosed + product.getGrams();
			String labelText = "<html>" + colorRed + "Product Name: " + colorClosed + product.getName().toString()
					+ "<br/>" + colorRed + "Brand: " + colorClosed + product.getBrands() + "<br/>" + colorRed
					+ "Description: " + colorClosed + product.getDescription() + "<br/>" + colorRed + "Category: "
					+ colorClosed + product.getCategory() + "<br/>" + colorRed + "Gender: " + colorClosed
					+ product.getGender() + ingredients + grams + "<br/>" + colorRed + "Price: " + colorClosed
					+ product.getPrice() + " " + product.getCurrency() + "</html>";

			descriptionProdLabel.setText(labelText);
		}
	};

	private static Action deleteItemClick = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTable table = (JTable) e.getSource();

			int modelRow = Integer.valueOf(e.getActionCommand());
			tableModel.setValueAt(0, modelRow, gs.getColumnElements());
			int idProd = gs.getRowmapidprod().get(modelRow);

			gs.removeFromProdIdMapCounter(idProd);
			gs.shiftRow(modelRow);
			tableModel.removeRow(modelRow);

			if (gs.getProdidmapcounter().isEmpty()) {
				bCheckout.setEnabled(false);
			}
		}
	};

}
