package it.gb.salestaxes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;
import org.junit.jupiter.api.Test;

import it.gb.salestaxes.bean.ProductsBean;
import it.gb.salestaxes.util.UtilConstants;
import it.gb.salestaxes.util.UtilMethods;

import static org.mockito.Mockito.*;

public class UtilMethodsTest {
	
	List<ProductsBean> mockList = new ArrayList<>();
	Object[][] data = new Object[mockList.size()][UtilConstants.columnTypes.size() + UtilConstants.OTHER_COLUMNS];
	List<String> mockColumns  = mock(List.class);
	
	HashMap<Integer, Class> columnTypes = UtilConstants.columnTypes;
	
	int[] editableColumns = { 5 };
	int disabledColumn = 0;
	

	@Test
	public void canDefineEnabledColumn() {
		DefaultTableModel tableModel =UtilMethods.generateTypeTableModel(data, mockColumns, columnTypes, editableColumns);
		assertTrue(tableModel.isCellEditable(0, editableColumns[0]));
		assertFalse(tableModel.isCellEditable(0, disabledColumn));
		System.out.println("@Test that checks considered column is enabled ora disabled");
	}
	
	@Test
	public void canGetColumnType() {
		DefaultTableModel tableModel =UtilMethods.generateTypeTableModel(data, mockColumns, columnTypes, editableColumns);
		assertEquals(tableModel.getColumnClass(0), UtilConstants.columnTypes.get(0));
		assertEquals(tableModel.getColumnClass(0),String.class);
		assertNotEquals(tableModel.getColumnClass(0), Integer.class);
		System.out.println("@Test that check the column type");
	}
	

}
