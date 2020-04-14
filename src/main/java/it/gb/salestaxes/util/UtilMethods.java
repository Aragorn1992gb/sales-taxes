package it.gb.salestaxes.util;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import it.gb.salestaxes.GUI.SecondWindow;

public class UtilMethods {
	static GlobalStorage gs = GlobalStorage.getInstance();
	static ClassLoader classL = new UtilMethods().getClass().getClassLoader();

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

}
