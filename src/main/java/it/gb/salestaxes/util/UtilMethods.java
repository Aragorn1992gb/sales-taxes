package it.gb.salestaxes.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import it.gb.salestaxes.GUI.SecondWindow;

public class UtilMethods {
	static UtilConstants utilC = new UtilConstants();
	static GlobalStorage gs = GlobalStorage.getInstance();

	// Method used in order to redirect window to another window
	public void redirect(ActionEvent event, Class windowClass, String MethodName, JFrame frameToClose)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		frameToClose.setVisible(false);
		Method m = windowClass.getMethod(MethodName);
		Object ret = m.invoke(windowClass, new Object[] {});
		SecondWindow sw = new SecondWindow();
	}

	// uses this method in order to define a DefaultDataModel with column types and
	// editable cells customized
	public static DefaultTableModel generateTypeTableModel(Object[][] data, List<String> columns,
			HashMap<Integer, Class> columnTypes, int[] editableColumns) {

		DefaultTableModel tableModel = new DefaultTableModel(data, columns.toArray()) {
			@Override
			public Class<?> getColumnClass(int column) {
				return columnTypes.get(column);
			}

			// disable all columns except just the ones you want (stored into
			// editableColumns Array)
			@Override
			public boolean isCellEditable(int row, int column) {
				if (editableColumns != null && IntStream.of(editableColumns).anyMatch(x -> x == column)) {
					return true;
				} else {
					return false;
				}

			};
		};
		return tableModel;
	}
	
	public static Action addToCartClick = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int modelRow = Integer.valueOf(e.getActionCommand());

			int idProd = gs.getRowmapidprod().get(modelRow);
			int counter = gs.getProdidmapcounter().get(idProd) == null ? 1 : gs.getProdidmapcounter().get(idProd)+1;
			
			int units = gs.getProductslist().get(idProd-1).getUnits();
			if(counter<=units) {
				gs.addToProdIdMapCounter(idProd, counter);
			} else {
				JOptionPane.showMessageDialog(null, "No other available units for chosen item");
			}

		} 
	};
	
	
	public static Action deleteItemClick = new AbstractAction() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JTable table = (JTable) e.getSource();
			TableModel tableModel = table.getModel();
	
	//		tableModel.
			int modelRow = Integer.valueOf(e.getActionCommand());
			tableModel.setValueAt(0, modelRow, gs.getColumnElements());
			int idProd = gs.getRowmapidprod().get(modelRow);
			gs.removeFromProdIdMapCounter(idProd);
		//	gs.addToProdIdMapCounter(idProd, 0);
		}
	};

}
